package eu.qcloud.guideset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.data.DataRepository;
import eu.qcloud.file.FileRepository;
import eu.qcloud.param.Param;
import eu.qcloud.param.ParamRepository;
import eu.qcloud.sampleComposition.SampleComposition;
import eu.qcloud.sampleComposition.SampleCompositionRepository;
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
	
	

	
//	public void setAllLabSystemGuideSetsBySampleTypeDisabled(List<GuideSet> guideSets, GuideSet guideSet) {
//		guideSets.forEach(gs -> {
//			if (gs.getSampleType().getQualityControlControlledVocabulary()
//					.equals(guideSet.getSampleType().getQualityControlControlledVocabulary())) {
//				gs.setIsActive(false);
//				guideSetRepository.save(gs);	
//			}
//		});
//
//	}
//
//	public GuideSet addNewGuideSet(GuideSet guideSet) {
//		guideSet.setIsActive(true);
//		guideSet.setApiKey(UUID.randomUUID());
//		return guideSetRepository.save(guideSet);
//	}
	

}
