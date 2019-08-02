package eu.qcloud.guideset.manual;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.guideset.GuideSetRepository;

@Service
public class ManualGuideSetService {

	@Autowired
	private ManualGuideSetRepository manualGuideSetRepository;

	@Autowired
	private GuideSetRepository guideSetRepository;

	@Autowired
	private FileRepository fileRepository;

	public long getFilesInThreshold(UUID labSystemApiKey, Date startDate, Date endDate, String sampleTypeQccv) {
		return fileRepository
				.countByLabSystemApiKeyAndSampleTypeQualityControlControlledVocabularyAndCreationDateBetween(
						labSystemApiKey, sampleTypeQccv, startDate, endDate);
	}

	public void setAllLabSystemGuideSetsBySampleTypeDisabled(List<GuideSet> guideSets, ManualGuideSet guideSet) {
		guideSets.forEach(gs -> {
			if (gs.getSampleType().getQualityControlControlledVocabulary()
					.equals(guideSet.getSampleType().getQualityControlControlledVocabulary())) {
				gs.setIsActive(false);
				guideSetRepository.save(gs);
			}
		});

	}

	public ManualGuideSet addNewManualGuideSet(ManualGuideSet guideSet) {
		guideSet.setIsActive(true);
		// guideSet.setEndDate(new Date(guideSet.getEndDate().getTime() + (86399 *
		// 1000)));
		guideSet.setApiKey(UUID.randomUUID());
		return manualGuideSetRepository.save(guideSet);
	}
}
