package eu.qcloud.nonconformity.thresholdnonconformity;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.file.FileRepository.FileLabSystemNameAndApiKey;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.threshold.InstrumentStatus;
import eu.qcloud.threshold.ThresholdRepository.ThresholdApiAndParam;

@Repository
public interface ThresholdNonConformityRepository extends PagingAndSortingRepository<ThresholdNonConformity, Long> {

	@Query("select t from ThresholdNonConformity t")
	public List<ThreholdNonConformityWithoutThresholdParams> findAllNonConformities();

	@Query("select t from ThresholdNonConformity t where t.file.labSystem.apiKey = ?1 and t.status='DANGER'")
	public List<ThreholdNonConformityWithoutThresholdParams> findByFileLabSystemApiKey(UUID labSystemApiKey, Pageable page);

	public List<ThresholdNonConformity> findByFileId(Long fileId);
	
	public Long countByFileLabSystemApiKey(UUID labSystemApiKey);
	
	public Long countByFileLabSystemApiKeyAndFileSampleTypeQualityControlControlledVocabulary(UUID labSystemApiKey, String sampleTypeQQCV);
	
	interface ThreholdNonConformityWithoutThresholdParams {
		GuideSet getGuideSet();

		FileLabSystemNameAndApiKey getFile();

		ContextSource getContextSource();

		ThresholdApiAndParam getThreshold();
		
		InstrumentStatus getStatus();
	}
	
	@Query("select t from ThresholdNonConformity t where t.file.labSystem.apiKey = ?1 and t.file.sampleType.qualityControlControlledVocabulary = ?2")
	public List<ThreholdNonConformityWithoutThresholdParams> findByFileLabSystemApiKeyAndFileSampleTypeQualityControlControlledVocabulary(
			UUID labSystemApiKey, String sampleTypeQQCV, Pageable page);

	
	@Transactional
	@Modifying
	@Query(value="delete from threshold_non_conformity where status = ?3 and file_id in (select id from file where labsystem_id = ?1 and sample_type_id = ?2)", nativeQuery = true)
	public void deletePreviousWarnings(Long labSystemId, Long sampleTypeId, String status);
	
	@Transactional
	@Modifying
	@Query(value="delete from threshold_non_conformity where status = ?3 and threshold_id = ?4 and file_id in (select id from file where labsystem_id = ?1 and sample_type_id = ?2)", nativeQuery = true)
	public void deletePreviousParamWarnings(Long labSystemId, Long sampleTypeId, String status, Long thresholdId);

}
