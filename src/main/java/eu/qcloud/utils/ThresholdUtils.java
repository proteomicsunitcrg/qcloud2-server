package eu.qcloud.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.data.Data;
import eu.qcloud.data.DataRepository;
import eu.qcloud.data.NonConformityStatus;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.guideset.GuideSetRepository;
import eu.qcloud.guideset.automatic.AutomaticGuideSet;
import eu.qcloud.guideset.manual.ManualGuideSet;
import eu.qcloud.guideset.manual.ManualGuideSetRepository;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.param.Param;
import eu.qcloud.threshold.InstrumentStatus;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdRepo;
import eu.qcloud.threshold.ThresholdType;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThreshold;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThresholdRepository;
import eu.qcloud.threshold.labsystemstatus.LabSystemStatus;
import eu.qcloud.threshold.params.ThresholdParams;
import eu.qcloud.threshold.params.ThresholdParamsId;
import eu.qcloud.threshold.params.ThresholdParamsRepository;
import eu.qcloud.threshold.processor.ThresholdProcessor;
import eu.qcloud.threshold.sigma.SigmaThreshold;
import eu.qcloud.threshold.sigma.SigmaThresholdRepository;
import eu.qcloud.threshold.sigmalog2threshold.SigmaLog2Threshold;
import eu.qcloud.threshold.sigmalog2threshold.SigmaLog2ThresholdRepository;
import eu.qcloud.thresholdnonconformity.ThresholdNonConformity;

@Service
public class ThresholdUtils {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private GuideSetRepository guideSetRepository;

	@Autowired
	private ThresholdRepo thresholdRepository;

	@Autowired
	private LabSystemRepository labSystemRepository;

	@Autowired
	private SigmaLog2ThresholdRepository sigmaLog2ThresholdRepository;

	@Autowired
	private SigmaThresholdRepository sigmaThresholdRepository;

	@Autowired
	private HardLimitThresholdRepository hardLimitThresholdRepository;

	@Autowired
	private ThresholdParamsRepository thresholdParamsRepository;

	@Autowired
	private DataRepository dataRepository;

	@Autowired
	private ManualGuideSetRepository manualGuideSetRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private final Log logger = LogFactory.getLog(this.getClass());

	@Value("${qcloud.instrument-status.max-offline-hours}")
	private int maxOfflineHours;

	/**
	 * Generate a guide set with the first and last file with valid values
	 * 
	 * @param file
	 * @param param
	 * @param contextSource
	 * @return
	 */

	public GuideSet generateGuideSetFromWithFile(File file, Param param, ContextSource contextSource) {

		Optional<ManualGuideSet> mg = manualGuideSetRepository.findById(file.getGuideSet().getId());

		if (mg.isPresent()) {
			return mg.get();
		}

		AutomaticGuideSet automaticGuideSet = (AutomaticGuideSet) file.getGuideSet();

		Pageable maxFiles = PageRequest.of(0, automaticGuideSet.getFiles(),
				new Sort(Sort.Direction.DESC, Arrays.asList("creationDate")));
		List<File> files = null;

		if (param.getIsZeroNoData()) {

			files = fileRepository.findFilesExcludingZeroValuesFromDate(file.getLabSystem().getId(),
					file.getSampleType().getId(), file.getCreationDate(), param.getId(), contextSource.getId(),
					maxFiles);
		} else {
			files = fileRepository.findFilesIncludingZeroValuesFromDate(file.getLabSystem().getId(),
					file.getSampleType().getId(), file.getCreationDate(), param.getId(), contextSource.getId(),
					maxFiles);
		}

		try {
			File firstFile = files.get(files.size() - 1);
			File lastFile = files.get(0);
			automaticGuideSet.setStartDate(firstFile.getCreationDate());
			automaticGuideSet.setEndDate(lastFile.getCreationDate());
			automaticGuideSet.setSampleType(file.getSampleType());
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.info("There are no files to build this guide set: " + param.getName() + " "
					+ contextSource.getAbbreviated());
			return null;
		}
		return automaticGuideSet;
	}

	/**
	 * Generate a guideset with the first and the last minus one files with valid
	 * values
	 * 
	 * @param file
	 * @param param
	 * @param contextSource
	 * @return
	 */
	public GuideSet generateGuideSetFromBeforeFile(File file, Param param, ContextSource contextSource) {

		Optional<ManualGuideSet> mg = manualGuideSetRepository.findById(file.getGuideSet().getId());

		if (mg.isPresent()) {
			return mg.get();
		}

		AutomaticGuideSet automaticGuideSet = (AutomaticGuideSet) file.getGuideSet();
		Pageable maxFiles = PageRequest.of(0, automaticGuideSet.getFiles() + 1,
				new Sort(Sort.Direction.DESC, Arrays.asList("creationDate")));
		List<File> files = null;

		if (param.getIsZeroNoData()) {

			files = fileRepository.findFilesExcludingZeroValuesFromDate(file.getLabSystem().getId(),
					file.getSampleType().getId(), file.getCreationDate(), param.getId(), contextSource.getId(),
					maxFiles);
		} else {
			files = fileRepository.findFilesIncludingZeroValuesFromDate(file.getLabSystem().getId(),
					file.getSampleType().getId(), file.getCreationDate(), param.getId(), contextSource.getId(),
					maxFiles);

		}

		File firstFile = files.get(files.size() - 1);
		File lastFile = files.get(1);
		AutomaticGuideSet guideSet = null;

		guideSet = new AutomaticGuideSet(firstFile.getCreationDate(), lastFile.getCreationDate());
		guideSet.setSampleType(file.getSampleType());
		return guideSet;
	}

	public Float processValueWithThresholdProcessor(float value, ThresholdType thresholdType) {
		switch (thresholdType) {
		case HARDLIMIT:
			return value;
		case SIGMA:
			return (value > 0) ? log2(value) : value;

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
	public Threshold findOrCreateLabSystemThresholdBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(Long sampleTypeId,
			Long paramId, Long cvId, Long labSystemId) {
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
				switch (t.get().getThresholdType()) {
				case SIGMALOG2:
					entityManager.detach(t.get());
					t.get().setId(null);
					// save threshold params for new labsystem threshold
					Threshold labSystemSigmaLog2Threshold = saveSigmaLog2Threshold((SigmaLog2Threshold) t.get());
					entityManager.detach(labSystemSigmaLog2Threshold);
					labSystemSigmaLog2Threshold.setApiKey(UUID.randomUUID());
					saveThresholdParams(labSystemSigmaLog2Threshold);
					return labSystemSigmaLog2Threshold;
				case HARDLIMIT:
					entityManager.detach(t.get());
					t.get().setId(null);
					Threshold labSystemHardLimitThreshold = saveHardLimitThreshold((HardLimitThreshold) t.get());
					entityManager.detach(labSystemHardLimitThreshold);
					labSystemHardLimitThreshold.setApiKey(UUID.randomUUID());
					saveThresholdParams(labSystemHardLimitThreshold);
					return labSystemHardLimitThreshold;
				case SIGMA:
					entityManager.detach(t.get());
					t.get().setId(null);
					Threshold labSystemSigmaThreshold = saveSigmaThreshold((SigmaThreshold) t.get());
					entityManager.detach(labSystemSigmaThreshold);
					labSystemSigmaThreshold.setApiKey(UUID.randomUUID());
					saveThresholdParams(labSystemSigmaThreshold);
					return labSystemSigmaThreshold;
				default:
					break;
				}
			}

		}
		return null;
	}

	private Threshold saveSigmaThreshold(SigmaThreshold threshold) {
		threshold.setApiKey(UUID.randomUUID());
		return sigmaThresholdRepository.save(threshold);
	}

	private Threshold saveSigmaLog2Threshold(SigmaLog2Threshold threshold) {
		threshold.setApiKey(UUID.randomUUID());
		return sigmaLog2ThresholdRepository.save(threshold);
	}

	private Threshold saveHardLimitThreshold(HardLimitThreshold threshold) {
		threshold.setApiKey(UUID.randomUUID());
		return hardLimitThresholdRepository.save(threshold);
	}

	private static final float log2(float f) {
		return (float) (Math.log(f) / Math.log(2.0));
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
			p.setIsEnabled(false);
			p.setThresholdParamsId(pid);
			thresholdParamsRepository.save(p);
		}
	}

	public void disableGuideSet(ManualGuideSet guideSet) {
		guideSet.setIsActive(false);
		guideSetRepository.save(guideSet);

	}

	public boolean checkGuideSet(LabSystem labSystem, GuideSet guideSet) {
		// Check dates
		if (guideSet.getStartDate().compareTo(guideSet.getEndDate()) > 0) {
			return false;
		}
		return true;

	}

	public ThresholdParams processThresholdParam(Threshold threshold, GuideSet guideSet, ThresholdParams tp) {
		ThresholdProcessor thresholdProcessor = threshold.getProcessor();
		if (thresholdProcessor.isGuideSetRequired()) {
			thresholdProcessor.setGuideSet(guideSet);
			// get the data for the processor
			List<Data> dataForProcessor = getDataForProcessor(threshold, tp, guideSet);
			if (dataForProcessor.size() == 0) {
				return null;
			}
			thresholdProcessor.setGuideSetData(dataForProcessor);
			thresholdProcessor.process(tp);
		} else {
			thresholdProcessor.process(tp);
		}
		return tp;
	}

	public void processThreshold(Threshold threshold, GuideSet guideSet) {
		ThresholdProcessor thresholdProcessor = threshold.getProcessor();
		if (thresholdProcessor.isGuideSetRequired()) {
			thresholdProcessor.setGuideSet(guideSet);
			// get the data for the processor
			for (ThresholdParams p : threshold.getThresholdParams()) {
				List<Data> dataForProcessor = getDataForProcessor(threshold, p, guideSet);
				if (dataForProcessor.size() == 0) {
					continue;
				}
				thresholdProcessor.setGuideSetData(dataForProcessor);
				thresholdProcessor.process(p);
			}
		}
	}

	private List<Data> getDataForProcessor(Threshold threshold, ThresholdParams p, GuideSet guideSet) {
		return dataRepository.findParamData(p.getContextSource().getId(), threshold.getParam().getId(),
				guideSet.getStartDate(), guideSet.getEndDate(), threshold.getLabSystem().getId(),
				threshold.getSampleType().getId());
	}

	public LabSystemStatus createLabSystemStatusByThresholdNonConformity(ThresholdNonConformity tnc) {

		LabSystemStatus ls = new LabSystemStatus();
		ls.setContextSource(tnc.getContextSource());
		ls.setParam(tnc.getThreshold().getParam());
		ls.setSampleTypeName(tnc.getFile().getSampleType().getSampleTypeCategory().getName());
		ls.setStatus(tnc.getStatus());
		ls.setFileChecksum(tnc.getFile().getChecksum());
		ls.setThresholdApiKey(tnc.getThreshold().getApiKey());

		return ls;
	}

	public LabSystemStatus createOfflineThresholdNonConformity(LabSystem labSystem) {
		LabSystemStatus ls = new LabSystemStatus();
		ls.setStatus(InstrumentStatus.OFFLINE);
		return ls;
	}

	public List<LabSystemStatus> createLabSystemStatusByThresholdNonConformity(List<ThresholdNonConformity> tncs) {
		List<LabSystemStatus> labSystemsStatus = new ArrayList<>();
		tncs.forEach(tnc -> {
			LabSystemStatus ls = new LabSystemStatus();
			ls.setContextSource(tnc.getContextSource());
			ls.setParam(tnc.getThreshold().getParam());
			ls.setSampleTypeName(tnc.getFile().getSampleType().getSampleTypeCategory().getName());
			ls.setStatus(tnc.getStatus());
			ls.setFileChecksum(tnc.getFile().getChecksum());
			ls.setThresholdApiKey(tnc.getThreshold().getApiKey());
			labSystemsStatus.add(ls);
		});

		return labSystemsStatus;
	}

	public Date getOfflineDate() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.HOUR, maxOfflineHours * -1);
		return cal.getTime();
	}

	public LabSystemStatus createPipelineErrorThresholdNonConformity(File f) {
		LabSystemStatus ls = new LabSystemStatus();
		ls.setStatus(InstrumentStatus.DANGER);
		ls.setFileChecksum(f.getChecksum());
		return ls;
	}

}
