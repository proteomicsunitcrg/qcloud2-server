package eu.qcloud.utils;

import java.util.Arrays;
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

import eu.qcloud.data.Data;
import eu.qcloud.data.DataRepository;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.guideset.GuideSetRepository;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdRepo;
import eu.qcloud.threshold.ThresholdType;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThreshold;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThresholdRepository;
import eu.qcloud.threshold.params.ThresholdParams;
import eu.qcloud.threshold.params.ThresholdParamsId;
import eu.qcloud.threshold.params.ThresholdParamsRepository;
import eu.qcloud.threshold.processor.ThresholdProcessor;
import eu.qcloud.threshold.sigmathreshold.SigmaThreshold;
import eu.qcloud.threshold.sigmathreshold.SigmaThresholdRepository;

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
	private SigmaThresholdRepository sigmaThresholdRepository;

	@Autowired
	private HardLimitThresholdRepository hardLimitThresholdRepository;

	@Autowired
	private ThresholdParamsRepository thresholdParamsRepository;
	
	@Autowired
	private DataRepository dataRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private final Log logger = LogFactory.getLog(this.getClass());

	@Value("${qcloud.threshold.max-auto-guidesets}")
	private int maxFilesForAutoGuideSet;

	/**
	 * Generate a guideset with the last date of the file before the last file.
	 * 
	 * @param sampleType
	 * @param labSystem
	 * @return
	 */
	public GuideSet generateAutoGuideSet(SampleType sampleType, LabSystem labSystem) {

		Pageable maxFiles = PageRequest.of(0, maxFilesForAutoGuideSet,
				new Sort(Sort.Direction.DESC, Arrays.asList("creationDate")));

		List<File> files = fileRepository.findByLabSystemIdAndSampleTypeId(labSystem.getId(), sampleType.getId(),
				maxFiles);
		// Get the first and the last file
		
		File firstFile = files.get(files.size() - 1);
		File lastFile = files.get(files.size() == 1 ? 0 : 1);
		
		GuideSet guideSet = null;
		
		guideSet = new GuideSet(firstFile.getCreationDate(), lastFile.getCreationDate());
		guideSet.setIsActive(true);
		guideSet.setIsUserDefined(false);
		guideSet.setSampleType(sampleType);
		return guideSet;
	}

	public GuideSet generateAutoGuideSetFromFile(File file) {

		Pageable maxFiles = PageRequest.of(0, maxFilesForAutoGuideSet,
				new Sort(Sort.Direction.DESC, Arrays.asList("creationDate")));

		List<File> files = fileRepository.findByLabSystemIdAndSampleTypeIdAndCreationDateBefore(file.getLabSystem().getId(),
				file.getSampleType().getId(), file.getCreationDate(), maxFiles);
		// Get the first and the last file
		File firstFile = files.get(files.size() - 1);
		// File lastFile = files.get(files.size() == 1 ? 0 : 1);
		File lastFile = files.get(0);
		GuideSet guideSet = null;
		
		guideSet = new GuideSet(firstFile.getCreationDate(), lastFile.getCreationDate());
		guideSet.setIsActive(true);
		guideSet.setIsUserDefined(false);
		guideSet.setSampleType(file.getSampleType());
		return guideSet;
	}
	
	public GuideSet generateExactGuideSetFromFile(SampleType sampleType, LabSystem labSystem, File file) {

		Pageable maxFiles = PageRequest.of(0, maxFilesForAutoGuideSet,
				new Sort(Sort.Direction.DESC, Arrays.asList("creationDate")));

		List<File> files = fileRepository.findByLabSystemIdAndSampleTypeIdAndCreationDateLessThanEqual(labSystem.getId(),
				sampleType.getId(), file.getCreationDate(), maxFiles);
		// Get the first and the last file
		File firstFile = files.get(files.size() - 1);
		// File lastFile = files.get(files.size() == 1 ? 0 : 1);
		File lastFile = files.get(0);
		GuideSet guideSet = null;
		
		guideSet = new GuideSet(firstFile.getCreationDate(), lastFile.getCreationDate());
		guideSet.setIsActive(true);
		guideSet.setIsUserDefined(false);
		guideSet.setSampleType(sampleType);
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
				// t.get().setGuideSet(ls.get().getGuideSet(sampleTypeId));
				switch (t.get().getThresholdType()) {
				case SIGMA:
					entityManager.detach(t.get());
					t.get().setId(null);
					// save threshold params for new labsystem threshold
					Threshold labSystemSigmaThreshold = saveSigmaThreshold((SigmaThreshold) t.get());
					entityManager.detach(labSystemSigmaThreshold);
					labSystemSigmaThreshold.setApiKey(UUID.randomUUID());
					saveThresholdParams(labSystemSigmaThreshold);
					return labSystemSigmaThreshold;
				case HARDLIMIT:
					entityManager.detach(t.get());
					t.get().setId(null);
					Threshold labSystemHardLimitThreshold = saveHardLimitThreshold((HardLimitThreshold) t.get());
					entityManager.detach(labSystemHardLimitThreshold);
					labSystemHardLimitThreshold.setApiKey(UUID.randomUUID());
					saveThresholdParams(labSystemHardLimitThreshold);
					return labSystemHardLimitThreshold;
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
			p.setThresholdParamsId(pid);
			thresholdParamsRepository.save(p);
		}
	}

	public void disableGuideSet(GuideSet guideSet) {
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
	
	public void processThreshold(Threshold threshold, GuideSet guideSet) {
		ThresholdProcessor thresholdProcessor = threshold.getProcessor();
		if (thresholdProcessor.isGuideSetRequired()) {
			thresholdProcessor.setGuideSet(guideSet);
			// get the data for the processor
			for (ThresholdParams p : threshold.getThresholdParams()) {
				List<Data> dataForProcessor = getDataForProcessor(threshold, p, guideSet);
				thresholdProcessor.setGuideSetData(dataForProcessor);
				thresholdProcessor.process(p);
			}
		}
	}
	
	private List<Data> getDataForProcessor(Threshold threshold, ThresholdParams p, GuideSet guideSet) {
		return dataRepository.findParamData(p.getContextSource().getId(), threshold.getParam().getId(),
				guideSet.getStartDate(), guideSet.getEndDate(),
				threshold.getLabSystem().getId(), threshold.getSampleType().getId());
	}
}
