package eu.qcloud.nonconformity.thresholdnonconformity;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import eu.qcloud.nonconformity.thresholdnonconformity.ThresholdNonConformityRepository.ThreholdNonConformityWithoutThresholdParams;

@Service
public class ThresholdNonConformityService {

	@Value("${qcloud.threshold-nonconformity.page-size}")
	private int pageSize;

	@Autowired
	private ThresholdNonConformityRepository thresholdNonConformityRepository;

	public List<ThreholdNonConformityWithoutThresholdParams> findAll() {
		return thresholdNonConformityRepository.findAllNonConformities();
	}

	public List<ThreholdNonConformityWithoutThresholdParams> findLabSystemNonConformitiesByPage(UUID labSystemApiKey,
			int page) {
		// If page is 0 throw exception

		return thresholdNonConformityRepository.findByFileLabSystemApiKey(labSystemApiKey,
				PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "file.creationDate")));

	}

	public long getTotalByLabSystem(UUID labSystemApiKey) {
		long total = thresholdNonConformityRepository.countByFileLabSystemApiKey(labSystemApiKey);
		if (total % pageSize > 0) {
			return (total / pageSize) + 1;
		} else {
			return total / pageSize;
		}
	}

	public long getTotalByLabSystemAndSampleType(UUID labSystemApiKey, String sampleTypeQQCV) {
		long total = thresholdNonConformityRepository
				.countByFileLabSystemApiKeyAndFileSampleTypeQualityControlControlledVocabulary(labSystemApiKey,
						sampleTypeQQCV);
		if (total % pageSize > 0) {
			return (total / pageSize) + 1;
		} else {
			return total / pageSize;
		}
	}

	public List<ThreholdNonConformityWithoutThresholdParams> findLabSystemNonConformitiesBySampleTypeAndByPage(
			UUID labSystemApiKey, String sampleTypeQQCV, int page) {

		return thresholdNonConformityRepository
				.findByFileLabSystemApiKeyAndFileSampleTypeQualityControlControlledVocabulary(labSystemApiKey,
						sampleTypeQQCV, PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "file.creationDate")));

	}

}
