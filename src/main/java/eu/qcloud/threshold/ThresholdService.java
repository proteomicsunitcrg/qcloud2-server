package eu.qcloud.threshold;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.Instrument.InstrumentRepository;
import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.contextSource.ContextSourceRepository;
import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.nonconformity.thresholdnonconformity.ThresholdNonConformity;
import eu.qcloud.nonconformity.thresholdnonconformity.ThresholdNonConformityRepository;
import eu.qcloud.param.Param;
import eu.qcloud.param.ParamRepository;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;
import eu.qcloud.threshold.ThresholdRepository.ThresholdForPlot;
import eu.qcloud.threshold.ThresholdRepository.withParamsWithoutThreshold;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThreshold;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThresholdRepository;
import eu.qcloud.threshold.labsystemstatus.LabSystemStatus;
import eu.qcloud.threshold.params.ThresholdParams;
import eu.qcloud.threshold.params.ThresholdParamsId;
import eu.qcloud.threshold.params.ThresholdParamsRepository;
import eu.qcloud.threshold.params.ThresholdParamsRepository.paramsNoThreshold;
import eu.qcloud.threshold.sigmathreshold.SigmaThreshold;
import eu.qcloud.threshold.sigmathreshold.SigmaThresholdRepository;
import eu.qcloud.utils.ThresholdUtils;
import eu.qcloud.utils.factory.ThresholdForPlotFactory;
import eu.qcloud.utils.factory.ThresholdForPlotImpl;

@Service
public class ThresholdService {

	@Autowired
	private ThresholdRepo thresholdRepository;

	@Autowired
	private SigmaThresholdRepository sigmaThresholdRepository;

	@Autowired
	private HardLimitThresholdRepository hardLimitThresholdRepository;

	@Autowired
	private LabSystemRepository labSystemRepository;

	@Autowired
	private ThresholdParamsRepository thresholdParamsRepository;

	@Autowired
	private ThresholdUtils thresholdUtils;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private SampleTypeRepository sampleTypeRepository;

	@Autowired
	private InstrumentRepository instrumentRepository;

	@Autowired
	private ParamRepository paramRepository;
	
	@Autowired
	private ThresholdNonConformityRepository thresholdNonConformityRepository;
	
	@Autowired
	private ContextSourceRepository contextSourceRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${qcloud.threshold.min-points-auto}")
	private int minPointsAutoThreshold;

	public List<withParamsWithoutThreshold> getAll() {
		List<withParamsWithoutThreshold> thresholds = new ArrayList<>();
		thresholdRepository.findMini().forEach(thresholds::add);
		return thresholds;
	}

	public Threshold saveSigmaThreshold(SigmaThreshold threshold) {
		return sigmaThresholdRepository.save(threshold);
	}

	public Threshold saveHardLimitThreshold(HardLimitThreshold threshold) {
		return hardLimitThresholdRepository.save(threshold);
	}

	/**
	 * Find a threshold in the database
	 * 
	 * @param sampleTypeId
	 * @param paramId
	 * @param cvId
	 * @return
	 */
	public Optional<Threshold> findThresholdBySampleTypeIdAndParamIdAndCvId(Long sampleTypeId, Long paramId,
			Long cvId) {
		return thresholdRepository.findOptionalBySampleTypeIdAndParamIdAndInstrumentIdAndIsEnabledTrue(sampleTypeId,
				paramId, cvId);
	}

	public Optional<Threshold> findThresholdBySampleTypeQCCVAndParamQCCVAndCVQCCV(String sampleTypeQCCV,
			String paramQCCV, String instrumentQCCV) {
		return thresholdRepository
				.findOptionalBySampleTypeQualityControlControlledVocabularyAndParamQccvAndInstrumentQccv(sampleTypeQCCV,
						paramQCCV, instrumentQCCV);
	}

	public Threshold saveThreshold(ThresholdType type, Threshold threshold) {
		// Get the entities
		Optional<SampleType> sampleType = sampleTypeRepository.findByQualityControlControlledVocabulary(
				threshold.getSampleType().getQualityControlControlledVocabulary());
		Param p = paramRepository.findByQccv(threshold.getParam().getqCCV());
		Optional<Instrument> instrument = instrumentRepository.getByCVId(threshold.getCv().getCVId());

		if (!sampleType.isPresent() || p == null | !instrument.isPresent()) {
			throw new DataRetrievalFailureException("Instrument, parameter or sample type not found");
		}

		/**
		 * I had to this because I could not do a downcast from threshold to a more
		 * specific son.
		 */
		switch (type) {
		case SIGMA:
			SigmaThreshold st = new SigmaThreshold();
			st.setCv(instrument.get());
			st.setSteps(threshold.getSteps());
			st.setParam(p);
			st.setSampleType(sampleType.get());
			st.setEnabled(true);
			st.setMonitored(true);
			st.setIsZeroNoData(threshold.getIsZeroNoData());
			st.setApiKey(UUID.randomUUID());
			return saveSigmaThreshold(st);
		case HARDLIMIT:
			HardLimitThreshold ht = new HardLimitThreshold();
			ht.setCv(instrument.get());
			ht.setSteps(threshold.getSteps());
			ht.setParam(p);
			ht.setSampleType(sampleType.get());
			ht.setEnabled(true);
			ht.setMonitored(true);
			ht.setIsZeroNoData(threshold.getIsZeroNoData());
			ht.setApiKey(UUID.randomUUID());
			return saveHardLimitThreshold(ht);
		default:
			return null;
		}
	}

	/**
	 * Returns a labsystem threshold. In case the labsystem do not have a threshold
	 * yet, it will get the default threshold (the threshold with null value at
	 * labsystem field) and save a new threshold for that lab system and then it
	 * will return it.
	 * 
	 * @param sampleTypeId
	 * @param paramId
	 * @param cvId
	 * @param labSystemId
	 * @return
	 */
	public Threshold findThresholdBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(Long sampleTypeId, Long paramId,
			Long cvId, Long labSystemId) {
		// check if exists for the current labSystem, if not, create
		Optional<Threshold> t = thresholdRepository
				.findOptionalBySampleTypeIdAndParamIdAndInstrumentIdAndLabSystemIdAndIsEnabledTrue(sampleTypeId,
						paramId, cvId, labSystemId);
		if (t.isPresent()) {
			return t.get();
		} else {
			// create and return
			Optional<LabSystem> ls = labSystemRepository.findById(labSystemId);
			t = thresholdRepository
					.findOptionalBySampleTypeIdAndParamIdAndInstrumentIdAndIsEnabledTrueAndLabSystemIdIsNull(
							sampleTypeId, paramId, cvId);
			if (t.isPresent()) {
				t.get().setLabSystem(ls.get());
				// t.get().setGuideSet(ls.get().getGuideSet(sampleTypeId));
				switch (t.get().getThresholdType()) {
				case SIGMA:
					entityManager.detach(t.get());
					t.get().setId(null);
					// save threshold params for new labsystem threshold
					Threshold labSystemSigmaThreshold = saveSigmaThreshold((SigmaThreshold) t.get());
					entityManager.detach(labSystemSigmaThreshold);
					saveThresholdParams(labSystemSigmaThreshold);
					return labSystemSigmaThreshold;
				case HARDLIMIT:
					entityManager.detach(t.get());
					t.get().setId(null);
					Threshold labSystemHardLimitThreshold = saveHardLimitThreshold((HardLimitThreshold) t.get());
					entityManager.detach(labSystemHardLimitThreshold);
					saveThresholdParams(labSystemHardLimitThreshold);
					return labSystemHardLimitThreshold;
				default:
					break;
				}
			}

		}
		return null;
	}

	/**
	 * Find a threshold, if not exists create a new one
	 * 
	 * @param sampleTypeQCCV
	 * @param paramQCCV
	 * @param cvId
	 * @param labSystemApiKey
	 * @return
	 */
	public Threshold findThresholdBySampleTypeQCCVAndParamQCCVAndInstrumentQCCVAndLabSystemApiKey(String sampleTypeQCCV,
			String paramQCCV, String cvId, UUID labSystemApiKey) {
		// check if threshold exists for the current cv
		Optional<Threshold> t = thresholdRepository
				.findOptionalBySampleTypeQualityControlControlledVocabularyAndParamQccvAndInstrumentQccvAndLabSystemApiKeyIsNull(
						sampleTypeQCCV, paramQCCV, cvId);
		if (!t.isPresent()) {
			return null;
		}
		// check if exists for the current labSystem, if not, create
		t = thresholdRepository
				.findOptionalBySampleTypeQualityControlControlledVocabularyAndParamQccvAndInstrumentQccvAndLabSystemApiKeyAndIsEnabledTrue(
						sampleTypeQCCV, paramQCCV, cvId, labSystemApiKey);
		if (t.isPresent()) {
			return t.get();
		} else {
			// create and return
			Optional<LabSystem> ls = labSystemRepository.findByApiKey(labSystemApiKey);
			t = thresholdRepository
					.findOptionalBySampleTypeQualityControlControlledVocabularyAndParamQccvAndInstrumentQccv(
							sampleTypeQCCV, paramQCCV, cvId);
			if (t.isPresent()) {
				t.get().setLabSystem(ls.get());
				switch (t.get().getThresholdType()) {
				case SIGMA:
					entityManager.detach(t.get());
					t.get().setId(null);
					// save threshold params for new labsystem threshold
					Threshold labSystemSigmaThreshold = saveSigmaThreshold((SigmaThreshold) t.get());
					entityManager.detach(labSystemSigmaThreshold);
					saveThresholdParams(labSystemSigmaThreshold);
					return labSystemSigmaThreshold;
				case HARDLIMIT:
					entityManager.detach(t.get());
					t.get().setId(null);
					Threshold labSystemHardLimitThreshold = saveHardLimitThreshold((HardLimitThreshold) t.get());
					entityManager.detach(labSystemHardLimitThreshold);
					saveThresholdParams(labSystemHardLimitThreshold);
					return labSystemHardLimitThreshold;
				default:
					break;
				}
			}

		}
		return null;
	}

	/**
	 * Save the params of a new labsystem threshold It is necessary to detach the
	 * entity in order to persist because it still hold the threshold, and the
	 * threshold id of the parent (default) threshold.
	 * 
	 * @param labSystemThreshold
	 */
	private void saveThresholdParams(Threshold labSystemThreshold) {
		for (ThresholdParams p : labSystemThreshold.getThresholdParams()) {
			entityManager.detach(p);
			p.setThresholdParamsId(null);
			p.setThreshold(labSystemThreshold);
			ThresholdParamsId pid = new ThresholdParamsId();
			pid.setContextSourceId(p.getContextSource().getId());
			pid.setThresholdId(labSystemThreshold.getId());
			p.setThresholdParamsId(pid);
			thresholdParamsRepository.save(p);
		}
	}

	public void saveThresholdParams(List<ThresholdParams> thresholdParams) {
		for (ThresholdParams p : thresholdParams) {
			ThresholdParamsId pId = new ThresholdParamsId();
			pId.setContextSourceId(p.getContextSource().getId());
			pId.setThresholdId(p.getThreshold().getId());
			p.setThresholdParamsId(pId);
			thresholdParamsRepository.save(p);
		}
	}

	/**
	 * Find all threshold params by lab system.
	 * 
	 * @param labSystemApiKey
	 * @return
	 */
	public List<withParamsWithoutThreshold> findAllThresholdsByLabSystemApiKey(UUID labSystemApiKey) {
		Optional<LabSystem> nodeLabSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		if (nodeLabSystem.isPresent()) {
			return thresholdRepository.findLabSystemThresholds(nodeLabSystem.get().getId());
		} else {
			throw new DataRetrievalFailureException("Lab system not found.");
		}
	}

	public void switchThresholdMonitoring(UUID thresholdApiKey) {
		Optional<Threshold> threshold = thresholdRepository.findByApiKey(thresholdApiKey);
		if (threshold.isPresent()) {
			Threshold t = threshold.get();
			// switching
			t.setMonitored(!t.isMonitored());
			// saving
			thresholdRepository.save(t);
		} else {
			throw new DataRetrievalFailureException("Threshold not found");
		}
	}

	public List<paramsNoThreshold> getAllThresholdParams() {
		return thresholdParamsRepository.getAll();
	}

	public List<withParamsWithoutThreshold> getMini() {
		return thresholdRepository.findMini();
	}

	public ThresholdForPlot getThreshold(UUID thresholdApiKey) {
		return thresholdRepository.getThresholdForPlot(thresholdApiKey);
	}

	/**
	 * Update the threshold parameters creating a new one and saving the updated one
	 * and disabling it. This is because we need to keep a trace of the
	 * non-conformities.
	 * 
	 * @param thresholdId
	 * @param thresholdParams
	 *            TODO: check if the threshold belongs to the current user
	 */
	public void updateThresholdParams(UUID thresholdApiKey, List<ThresholdParams> thresholdParams) {
		// get the threshold
		Optional<Threshold> t = thresholdRepository.findByApiKey(thresholdApiKey);
		if (t.isPresent()) {
			Threshold threshold = t.get();
			// compare both thresholdparams
			switch (threshold.getThresholdType()) {
			case HARDLIMIT:
				if (compareParams(threshold.getThresholdParams(), thresholdParams)) {
					// check for constraints
					if (threshold.getManagerThresholdConstraint().isGlobalInitialValue()) {
						// check if all the values are equal
						if (checkGlobalInitialValues(thresholdParams)) {
							if (threshold.getManagerThresholdConstraint().isGlobalStepValue()) {
								if (checkGlobalStepValue(thresholdParams)) {
									// disable current threshold and create a new one
									threshold.setEnabled(false);
									thresholdRepository.save(threshold);
									entityManager.detach(threshold);
									threshold.setEnabled(true);
									threshold.setId(null);
									threshold.setApiKey(UUID.randomUUID());
									threshold = thresholdRepository.save(threshold);
									for (ThresholdParams p : thresholdParams) {
										p.setThreshold(threshold);
										ThresholdParamsId tid = new ThresholdParamsId();
										tid.setContextSourceId(p.getContextSource().getId());
										tid.setThresholdId(threshold.getId());
										p.setThresholdParamsId(tid);
										thresholdParamsRepository.save(p);
									}
								} else {
									throw new DataIntegrityViolationException(
											"Invalid threshold parameters. Constraint validation failed");
								}
							}

						} else {
							throw new DataIntegrityViolationException(
									"Invalid threshold parameters. Constraint validation failed");
						}
					}
				}

				break;
			default:
				throw new InvalidActionException("This threshold parameters can not be updated.");
			}

		} else {
			throw new DataRetrievalFailureException("Threshold not found");
		}
	}

	private boolean checkGlobalStepValue(List<ThresholdParams> thresholdParams) {
		float value = thresholdParams.get(0).getStepValue();
		boolean ok = true;
		for (ThresholdParams t : thresholdParams) {
			if (t.getStepValue() != value) {
				ok = false;
			}
		}
		return ok;
	}

	private boolean checkGlobalInitialValues(List<ThresholdParams> thresholdParams) {
		float value = thresholdParams.get(0).getInitialValue();
		boolean ok = true;
		for (ThresholdParams t : thresholdParams) {
			if (t.getInitialValue() != value) {
				ok = false;
			}
		}
		return ok;
	}

	/**
	 * Compare two threshold params by size and by CV.
	 * 
	 * @param thresholdParams
	 * @param thresholdParams2
	 * @return
	 */
	private boolean compareParams(List<ThresholdParams> thresholdParams, List<ThresholdParams> thresholdParams2) {
		if (thresholdParams.size() == thresholdParams2.size()) {
			int matches = 0;
			for (ThresholdParams p : thresholdParams) {
				for (ThresholdParams p2 : thresholdParams2) {
					if (p.getContextSource().getName().equals(p2.getContextSource().getName())
							&& p.getContextSource().getId() == p2.getContextSource().getId()) {
						matches++;
					}
				}
			}
			if (matches != thresholdParams.size()) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public List<LabSystemStatus> getLabSystemStatus(LabSystem labSystem) {
		List<LabSystemStatus> labSystemStatus = new ArrayList<>();
		List<SampleType> sampleTypes = fileRepository.findDistinctSampleTypeByLabSystemId(labSystem.getId());
		for(SampleType sampleType : sampleTypes) {
			File lastFile = fileRepository.findTop1ByLabSystemIdAndSampleTypeIdOrderByCreationDateDesc(labSystem.getId(), sampleType.getId());
			
			List<ThresholdNonConformity> tncs = thresholdNonConformityRepository.findByFileId(lastFile.getId());
			tncs.forEach(tnc -> {
				labSystemStatus.add(createLabSystemStatusByThresholdNonConformity(tnc));
			});
		}
		return labSystemStatus;
	}
	
	private LabSystemStatus createLabSystemStatusByThresholdNonConformity(ThresholdNonConformity tnc) {
		LabSystemStatus ls = new LabSystemStatus();
		ls.setContextSource(tnc.getContextSource());
		ls.setParam(tnc.getThreshold().getParam());
		ls.setSampleTypeQccv(tnc.getFile().getSampleType().getQualityControlControlledVocabulary());
		ls.setStatus(tnc.getStatus());
		ls.setFileChecksum(tnc.getFile().getChecksum());
		ls.setThresholdApiKey(tnc.getThreshold().getApiKey());
		return ls;
	}
	
	public ThresholdForPlot findThresholdForPlotByParamIdAndSampleTypeIdAndLabSystemApiKey(Long paramId,
			Long sampleTypeId, UUID labSystemApiKey) {
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		if (!labSystem.isPresent()) {
			throw new DataRetrievalFailureException("Labsystem do not exists.");
		}
		return thresholdRepository.findByParamIdAndSampleTypeIdAndLabSystemId(paramId, sampleTypeId,
				labSystem.get().getId());
	}
	
	public ThresholdForPlotImpl calculateThresholdForPlotByParamIdAndSampleTypeIdAndLabSystemApiKey(Long paramId,
			Long sampleTypeId, UUID labSystemApiKey) {
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		if (!labSystem.isPresent()) {
			throw new DataRetrievalFailureException("Labsystem do not exists.");
		}
		
		Threshold threshold = thresholdRepository.findThresholdByParamIdAndSampleTypeIdAndLabSystemId(paramId,
				sampleTypeId, labSystem.get().getId());
		
		return ThresholdForPlotFactory.create(threshold);
	}

	public ThresholdForPlotImpl getNonConformityThresholdWithoutGuideSet(UUID thresholdApiKey, String fileChecksum,
			UUID contextSourceApiKey) {
		// TODO Auto-generated method stub
		Optional<Threshold> threshold = thresholdRepository.findByApiKey(thresholdApiKey);
		File file = fileRepository.findByChecksum(fileChecksum);
		
		
		Optional<ContextSource> cs = contextSourceRepository.findByApiKey(contextSourceApiKey);
		if(!cs.isPresent()) {
			throw new DataRetrievalFailureException("Context source do not exists.");
		}

		GuideSet gs =thresholdUtils.generateGuideSetFromBeforeFile(file,threshold.get().getParam(), cs.get());
		
		thresholdUtils.processThreshold(threshold.get(), gs);

		return ThresholdForPlotFactory.create(threshold.get());
	}

	public Threshold findThresholdByApiKey(UUID thresholdApiKey) {
		Optional<Threshold> threshold = thresholdRepository.findByApiKey(thresholdApiKey);
		if (threshold.isPresent()) {
			return threshold.get();
		}
		throw new DataRetrievalFailureException("Threshold do not exists.");
	}

}
