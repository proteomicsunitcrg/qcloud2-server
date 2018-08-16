package eu.qcloud.nonconformity.thresholdnonconformity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.file.FileRepository.FileLabSystemNameAndApiKey;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.threshold.ThresholdRepository.ThresholdApiAndParam;

@Repository
public interface ThresholdNonConformityRepository extends PagingAndSortingRepository<ThresholdNonConformity, Long> {

	@Query("select t from ThresholdNonConformity t")
	public List<ThreholdNonConformityWithoutThresholdParams> findAllNonConformities();

	@Query("select t from ThresholdNonConformity t where t.file.labSystem.apiKey = ?1")
	public List<ThreholdNonConformityWithoutThresholdParams> findByFileLabSystemApiKey(UUID labSystemApiKey, Pageable page);

	public Long countByFileLabSystemApiKey(UUID labSystemApiKey);
	
	public Long countByFileLabSystemApiKeyAndFileSampleTypeQualityControlControlledVocabulary(UUID labSystemApiKey, String sampleTypeQQCV);
	
	interface ThreholdNonConformityWithoutThresholdParams {
		GuideSet getGuideSet();

		FileLabSystemNameAndApiKey getFile();

		ContextSource getContextSource();

		ThresholdApiAndParam getThreshold();
	}
	
	@Query("select t from ThresholdNonConformity t where t.file.labSystem.apiKey = ?1 and t.file.sampleType.qualityControlControlledVocabulary = ?2")
	public List<ThreholdNonConformityWithoutThresholdParams> findByFileLabSystemApiKeyAndFileSampleTypeQualityControlControlledVocabulary(
			UUID labSystemApiKey, String sampleTypeQQCV, Pageable page);

}
