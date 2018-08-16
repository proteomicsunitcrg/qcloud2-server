package eu.qcloud.guideset;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.file.FileRepository;

@Service
public class GuideSetService {

	@Autowired
	private GuideSetRepository guideSetRepository;

	@Autowired
	private FileRepository fileRepository;

	public long getFilesInThreshold(UUID labSystemApiKey, Date startDate, Date endDate, String sampleTypeQccv) {
		return fileRepository
				.countByLabSystemApiKeyAndSampleTypeQualityControlControlledVocabularyAndCreationDateBetween(
						labSystemApiKey, sampleTypeQccv, startDate, endDate);
	}

	public void setAllLabSystemGuideSetsBySampleTypeDisabled(List<GuideSet> guideSets, GuideSet guideSet) {
		guideSets.forEach(gs -> {
			if (gs.getSampleType().getQualityControlControlledVocabulary()
					.equals(guideSet.getSampleType().getQualityControlControlledVocabulary())) {
				gs.setIsActive(false);
				guideSetRepository.save(gs);	
			}
		});

	}

	public GuideSet addNewGuideSet(GuideSet guideSet) {
		guideSet.setIsActive(true);
		guideSet.setApiKey(UUID.randomUUID());
		return guideSetRepository.save(guideSet);
	}

}
