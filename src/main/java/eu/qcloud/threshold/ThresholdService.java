package eu.qcloud.threshold;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.data.Data;
import eu.qcloud.data.DataRepository;
import eu.qcloud.labsystem.GuideSet;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.threshold.ThresholdRepository.ThresholdForPlot;
import eu.qcloud.threshold.ThresholdRepository.withParamsWithoutThreshold;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThreshold;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThresholdRepository;
import eu.qcloud.threshold.params.ThresholdParams;
import eu.qcloud.threshold.params.ThresholdParamsId;
import eu.qcloud.threshold.params.ThresholdParamsRepository;
import eu.qcloud.threshold.params.ThresholdParamsRepository.paramsNoThreshold;
import eu.qcloud.threshold.processor.ThresholdProcessor;
import eu.qcloud.threshold.sigmathreshold.SigmaThreshold;
import eu.qcloud.threshold.sigmathreshold.SigmaThresholdRepository;
import eu.qcloud.utils.ThresholdUtils;

@Service
public class ThresholdService {

	@Autowired
	private ThresholdRepo thresholdRepository;

	@Autowired
	private SigmaThresholdRepository sigmaThresholdRepository;

	@Autowired
	private HardLimitThresholdRepository hardLimitThresholdRepository;

	@Autowired
	private DataRepository dataRepository;

	@Autowired
	private LabSystemRepository labSystemRepository;

	@Autowired
	private ThresholdParamsRepository thresholdParamsRepository;
	
	@Autowired
	private ThresholdUtils thresholdUtils;

	@PersistenceContext
	private EntityManager entityManager;

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
	 * @param sampleTypeId
	 * @param paramId
	 * @param cvId
	 * @return
	 */
	public Optional<Threshold> findThresholdBySampleTypeIdAndParamIdAndCvId(Long sampleTypeId, Long paramId, Long cvId) {
		return thresholdRepository.findOptionalBySampleTypeIdAndParamIdAndCvId(sampleTypeId, paramId, cvId);
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
				.findOptionalBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(sampleTypeId, paramId, cvId, labSystemId);
		if (t.isPresent()) {
			return t.get();
		} else {
			// create and return
			Optional<LabSystem> ls = labSystemRepository.findById(labSystemId);
			t = thresholdRepository.findOptionalBySampleTypeIdAndParamIdAndCvId(sampleTypeId, paramId, cvId);
			if(t.isPresent()) {
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
	 * Save the params of a new labsystem threshold
	 * It is necessary to detach the entity in order to persist
	 * because it still hold the threshold, and the threshold id 
	 * of the parent (default) threshold.
	 * @param labSystemThreshold
	 */
	private void saveThresholdParams(Threshold labSystemThreshold) {
		for(ThresholdParams p : labSystemThreshold.getThresholdParams()) {
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

	public Threshold processThreshold(Threshold t) {
		ThresholdProcessor thresholdProcessor = t.getProcessor();
		if (thresholdProcessor.isGuideSetRequired()) {
			if (t.getLabSystem() == null) {
				// throw error
			} else {
				GuideSet gs = t.getLabSystem().getGuideSet();				
				if(gs == null) {
					gs = thresholdUtils.getTwoWeeksGuideSet(t.getLabSystem().getId());
				}				
				thresholdProcessor.setGuideSet(gs);
				// get the data for the processor
				for (ThresholdParams p : t.getThresholdParams()) {
					List<Data> dataForProcessor = dataRepository.findParamData(p.getContextSource().getId(),
							t.getParam().getId(), gs.getStartDate(), gs.getEndDate(), t.getLabSystem().getId(),
							t.getSampleType().getId());
					thresholdProcessor.setGuideSetData(dataForProcessor);
					thresholdProcessor.process(p);
					thresholdParamsRepository.save(p);
				}
			}
		} else {
			for (ThresholdParams p : t.getThresholdParams()) {
				p.setThreshold(t);
				thresholdParamsRepository.save(p);
			}
		}

		return t;
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

	public List<paramsNoThreshold> getAllThresholdParams() {
		return thresholdParamsRepository.getAll();
	}

	public List<withParamsWithoutThreshold> getMini() {
		return thresholdRepository.findMini();
	}

	public ThresholdForPlot getThreshold(Long thresholdId) {
		return thresholdRepository.getThresholdForPlot(thresholdId);
	}

}
