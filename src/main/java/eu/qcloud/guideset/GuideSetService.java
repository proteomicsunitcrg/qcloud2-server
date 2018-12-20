package eu.qcloud.guideset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.contextSource.ContextSourceRepository;
import eu.qcloud.data.DataRepository;
import eu.qcloud.file.FileRepository;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdRepo;
import eu.qcloud.threshold.params.ThresholdParams;

@Service
public class GuideSetService {

	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private DataRepository dataRepository;
	
	@Autowired
	private ThresholdRepo thresholdRepository;
	
	@Autowired
	private LabSystemRepository labSystemRepository;
	
	@Autowired
	private GuideSetRepository guideSetRepository;
	
	@Autowired
	private ContextSourceRepository contextSourceRepository;
	
	@Value("${qcloud.threshold.min-valid-context-source-points}")
	private int minValidPointsManualThreshold;
	
	@Value("${qcloud.threshold.min-points-manual}")
	private int recomendedValidPoints;

	public long getFilesInThreshold(UUID labSystemApiKey, Date startDate, Date endDate, String sampleTypeQccv) {

		return fileRepository
				.countByLabSystemApiKeyAndSampleTypeQualityControlControlledVocabularyAndCreationDateBetween(
						labSystemApiKey, sampleTypeQccv, startDate, endDate);
	}
	
	public List<GuideSetContextSourceStatus> evaluateGuideSet(UUID labSystemApiKey, Date startDate, Date endDate, String sampleTypeQccv) {
		
		List<GuideSetContextSourceStatus> evaluation = new ArrayList<>();
		List<Threshold> thresholds = thresholdRepository.findByLabSystemApiKeyAndSampleTypeQualityControlControlledVocabulary(labSystemApiKey, sampleTypeQccv);
			
		for(Threshold threshold: thresholds) {
			for(ThresholdParams thresholdParam: threshold.getThresholdParams()) {
				if(!thresholdParam.getIsEnabled()) {
					continue;
				}
				Long count = dataRepository.countByContextSourceIdAndParamIdAndFileLabSystemApiKeyAndFileCreationDateBetweenAndCalculatedValueIsNotNull(thresholdParam.getContextSource().getId(), threshold.getParam().getId(), labSystemApiKey, startDate, endDate);
				if(count < minValidPointsManualThreshold) {
					evaluation.add(new GuideSetContextSourceStatus(threshold.getParam(), thresholdParam.getContextSource(), ContextSourceStatus.NO_VALID, count));
				} else if(count < recomendedValidPoints) {
					evaluation.add(new GuideSetContextSourceStatus(threshold.getParam(), thresholdParam.getContextSource(), ContextSourceStatus.NOT_RECOMENDED, count));
				}
			}
		}
		return evaluation;
	}
	
	public GuideSetContextSourceStatus evaluateGuideSetContextSource(UUID labSystemApiKey, Date startDate, Date endDate, String sampleTypeQccv, ContextSource contextSource) {
		
		GuideSetContextSourceStatus evaluation = null;
		List<Threshold> thresholds = thresholdRepository.findByLabSystemApiKeyAndSampleTypeQualityControlControlledVocabulary(labSystemApiKey, sampleTypeQccv);
			
		for(Threshold threshold: thresholds) {
			for(ThresholdParams thresholdParam: threshold.getThresholdParams()) {
				if(thresholdParam.getContextSource().getId() == contextSource.getId()) {
					Long count = dataRepository.countByContextSourceIdAndParamIdAndFileLabSystemApiKeyAndFileCreationDateBetweenAndCalculatedValueIsNotNull(thresholdParam.getContextSource().getId(), threshold.getParam().getId(), labSystemApiKey, startDate, endDate);
					if(count < minValidPointsManualThreshold) {
						return  new GuideSetContextSourceStatus(threshold.getParam(), thresholdParam.getContextSource(), ContextSourceStatus.NO_VALID, count);
					} else if(count < recomendedValidPoints) {
						return new GuideSetContextSourceStatus(threshold.getParam(), thresholdParam.getContextSource(), ContextSourceStatus.NOT_RECOMENDED, count);
					}	
				}
			}
		}
		return evaluation;
	}

	public GuideSetContextSourceStatus checkCurrentGuideSet(UUID labSystemApiKey, String sampleTypeQccv,
			UUID contextSourceApiKey) {
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		if(!labSystem.isPresent()) {
			throw new DataRetrievalFailureException("Lab system not found");
		}
		
		GuideSet gs = labSystem.get().getActiveGuideSetBySampleTypeQccv(sampleTypeQccv);
		if(gs == null) {
			return null;
		}
		Optional<ContextSource> cs = contextSourceRepository.findByApiKey(contextSourceApiKey);
		if(!cs.isPresent()) {
			throw new DataRetrievalFailureException("Context source not found");
		}
		
		return evaluateGuideSetContextSource(labSystemApiKey, gs.getStartDate(), gs.getEndDate(), sampleTypeQccv, cs.get());
		
	}

	public void resetLabSystemGuideSetByThresholdSampleType(UUID labSystemApiKey, Threshold threshold) {
		if(!labSystemApiKey.equals(threshold.getLabSystem().getApiKey())) {
			throw new DataRetrievalFailureException("Lab system not found");
		}
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		if(!labSystem.isPresent()) {
			throw new DataRetrievalFailureException("Lab system not found");
		}
		GuideSet gs =labSystem.get().getActiveGuideSetBySampleTypeQccv(threshold.getSampleType().getQualityControlControlledVocabulary());
		gs.setIsActive(false);
		guideSetRepository.save(gs);
		
	}
	
	public void resetLabSystemGuideSetBySampleType(UUID labSystemApiKey, SampleType sampleType) {
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		if(!labSystem.isPresent()) {
			throw new DataRetrievalFailureException("Lab system not found");
		}
		GuideSet gs =labSystem.get().getActiveGuideSetBySampleTypeQccv(sampleType.getQualityControlControlledVocabulary());
		gs.setIsActive(false);
		guideSetRepository.save(gs);
	}
	

}
