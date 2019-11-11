package eu.qcloud.file;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository.LabSystemNameAndApiKey;
import eu.qcloud.sampleType.SampleType;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

	public OnlySmalls findFileById(Long idFile);

	public List<File> findAllByOrderByIdDesc();

	public File findByChecksum(String checksum);

	public Optional<File> findTop1ByLabSystemIdOrderByCreationDateDesc(Long labSystemId);

	public File findTop1ByLabSystemIdOrderByCreationDateAsc(Long labSystemId);

	public File findTop1ByLabSystemIdAndSampleTypeIdOrderByCreationDateDesc(Long labSystemId, Long sampleTypeId);

	public long countByLabSystemApiKeyAndSampleTypeQualityControlControlledVocabularyAndCreationDateBetween(
			UUID labSystemApiKey, String sampleTypeQccv, Date startDate, Date endDate);

	public long countByLabSystemApiKey(UUID labSystemApiKey);

	public Long countByLabSystemApiKeyAndCreationDateAfter(UUID apiKey, Date date);

	public Long countByCreationDateAfter(Date date);

	public long countByLabSystemIdAndSampleTypeId(Long labSystemId, Long sampleTypeId);

	@Query("select count(f) from File f where f.labSystem.id = ?1 and f.sampleType.id=?2 and f.id in (select d.file.id from Data d where d.param.id = ?3 and d.contextSource.id = ?4 and d.file.labSystem.id = ?1)")
	public long countByLabSystemIdAndSampleTypeIdAndParamIdAndContextSourceId(Long labSystemId, Long sampleTypeId,
			Long paramId, Long contextSourceId);

	@Query("select distinct(f.sampleType) from File f where f.labSystem.id=?1")
	public List<SampleType> findDistinctSampleTypeByLabSystemId(Long labSystemId);

	@Query("select distinct(f.sampleType) from File f where f.labSystem.apiKey=?1")
	public List<SampleType> findDistinctSampleTypeByLabSystemApiKey(UUID labSystemApiKey);

	/**
	 * Find how many files exists after the given file
	 * 
	 * @param labSystemId
	 * @param sampleTypeId
	 * @param creationDate
	 * @return
	 */
	public long countByLabSystemIdAndSampleTypeIdAndCreationDateGreaterThan(Long labSystemId, Long sampleTypeId,
			Date creationDate);

	public List<OnlySmalls> findTop10ByLabSystemIdAndSampleTypeIdOrderByCreationDateDesc(Long labSystemId,
			Long sampleTypeId);

	public File findTop1BySampleTypeQualityControlControlledVocabularyAndLabSystemApiKeyOrderByCreationDateDesc(
			String sampleTypeQCCV, UUID labSystemApikey);

	public List<File> findByCreationDateAndLabSystemIdAndSampleTypeId(Date creationDate, Long LabSystemId,
			Long sampleTypeId);

	@Query(value = "select * from file f where f.labsystem_id = ?1 and f.sample_type_id = ?2 order by f.creation_date desc limit 15", nativeQuery = true)
	public List<File> findForAutoPlotWithZero(Long labSystemId, Long sampleTypeId);

	@Query(value = "select * from file f where f.labsystem_id = ?1 and f.sample_type_id = ?2 and f.creation_date <= ?3 order by f.creation_date desc limit 15", nativeQuery = true)
	public List<File> findForNonConformityPlot(Long labSystemId, Long sampleTypeId, Date creationDate);

	/**
	 * Get the files from a date excluding with a value of 0 at a given context
	 * source
	 * 
	 * @param labSystemId
	 * @param sampleTypeId
	 * @param creationDate
	 * @param paramId
	 * @param maxPages
	 * @return
	 */
	@Query("select f from File f where f.labSystem.id = ?1 and f.sampleType.id=?2 and f.creationDate <= ?3 and f.id in (select d.file.id from Data d where d.value !=0 and d.param.id = ?4 and d.contextSource.id = ?5 and d.file.labSystem.id = ?1)")
	public List<File> findFilesExcludingZeroValuesFromDate(Long labSystemId, Long sampleTypeId, Date creationDate,
			Long paramId, Long contextSourceId, Pageable maxPages);

	/**
	 * Get the files from a date including files with a value of 0 at a given
	 * context source
	 * 
	 * @param labSystemId
	 * @param sampleTypeId
	 * @param creationDate
	 * @param paramId
	 * @param contextSourceId
	 * @param maxPages
	 * @return
	 */
	@Query("select f from File f where f.labSystem.id = ?1 and f.sampleType.id=?2 and f.creationDate <= ?3 and f.id in (select d.file.id from Data d where d.param.id = ?4 and d.contextSource.id = ?5 and d.file.labSystem.id = ?1)")
	public List<File> findFilesIncludingZeroValuesFromDate(Long labSystemId, Long sampleTypeId, Date creationDate,
			Long paramId, Long contextSourceId, Pageable maxPages);

	/**
	 * Find the last files excluding files with a value of 0 at a given context
	 * source
	 * 
	 * @param labSystemId
	 * @param sampleTypeId
	 * @param paramId
	 * @param maxPages
	 * @return
	 */
	@Query("select f from File f where f.labSystem.id = ?1 and f.sampleType.id=?2 and f.id in (select d.file.id from Data d where d.value !=0 and d.param.id = ?3 and d.contextSource.id = ?4)")
	public List<File> findLastFilesExcludingZeroValues(Long labSystemId, Long sampleTypeId, Long paramId,
			Long contextSourceId, Pageable maxPages);

	/**
	 * Find the last files including files with a value of 0 at a given context
	 * source
	 * 
	 * @param labSystemId
	 * @param sampleTypeId
	 * @param paramId
	 * @param maxPages
	 * @return
	 */
	@Query("select f from File f where f.labSystem.id = ?1 and f.sampleType.id=?2 and f.id in (select d.file.id from Data d where d.param.id = ?3 and d.contextSource.id = ?4)")
	public List<File> findLastFilesIncludingZeroValues(Long labSystemId, Long sampleTypeId, Long paramId,
			Long contextSourceId, Pageable maxPages);

	/**
	 * 
	 * @param paramId
	 * @param contextSourceId
	 * @param labSystemId
	 * @param sampleTypeId
	 * @return
	 */
	@Query(value = "select * from file f where f.id in (select d.file_id from data d where d.value > 0 and d.param_id = ?1 and d.context_source_id = ?2)  and f.labsystem_id = ?3 and f.sample_type_id = ?4 order by f.creation_date desc limit 15", nativeQuery = true)
	public List<File> findForAutoPlotExcludeZero(Long paramId, Long contextSourceId, Long labSystemId,
			Long sampleTypeId);

	@Projection(name = "file_mini", types = File.class)
	public interface OnlySmalls {
		String getFilename();

		Long getLabSystemId();

		String getLabSystemName();

		@JsonFormat(pattern = "yyyy-MM-dd hh:mm")
		Date getCreationDate();
	}

	public interface OnlyChecksum {
		public String getChecksum();
	}

	interface FileLabSystemNameAndApiKey {
		SampleType getSampleType();

		LabSystemNameAndApiKey getLabSystem();

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		Date getCreationDate();

		String getChecksum();

		String getFilename();
	}

	@Query("select f from File f where filename = ?1")
	public OnlyChecksum getFileByFilename(String filename);

	public List<File> findByLabSystemIdAndSampleTypeId(Long labSystemId, Long sampleTypeId, Pageable maxFiles);

	public List<File> findByLabSystemIdAndSampleTypeIdAndCreationDateLessThanEqual(Long labSystemId, Long sampleTypeId,
			Date creationDate, Pageable maxFiles);

	public Optional<File> findOptionalByChecksum(String fileChecksum);

	public List<File> findByLabSystemApiKeyAndSampleTypeQualityControlControlledVocabularyAndCreationDateBetween(
			UUID labSystemApiKey, String sampleTypeQccv, Date startDate, Date endDate);

	public Long countByLabSystemIdAndSampleTypeIdAndCreationDateBetween(Long id, Long id2, Date startDate,
			Date endDate);

	public Long countByLabSystemApiKeyAndSampleTypeIdAndCreationDateBetween(UUID labSystemApiKey, Long sampleTypeId,
			Date startDate, Date endDate);

	// public Long countByLabSystemApiKey(UUID lsApiKey);

	public Page<File> findByFilenameContainingAndChecksumContainingAndLabSystemNameContainingAndSampleTypeQualityControlControlledVocabularyContainingAndLabSystemDataSourcesNodeNameContainingAndLabSystemDataSourcesCvCategoryIdOrderByCreationDateDesc(
			String filename, String cheksum, String labsystemName, String sampleTypeId, String nodeName,
			Long categoryId, Pageable page);

	public Page<File> findByFilenameContainingAndChecksumContainingAndLabSystemNameAndSampleTypeQualityControlControlledVocabularyContainingOrderByCreationDateDesc(
			String filename, String cheksum, String labsystemName, String sampleTypeId, Pageable page);

	public List<File> findByFilenameContainingAndChecksumContainingAndLabSystemNameContainingAndSampleTypeQualityControlControlledVocabularyContainingAndLabSystemDataSourcesNodeNameContainingAndLabSystemDataSourcesCvCategoryIdOrderByCreationDateDesc(
			String filename, String cheksum, String labsystemName, String sampleTypeId, String nodeName,
			Long categoryId);

	public List <File> findByFilenameContainingAndChecksumContainingAndLabSystemNameAndSampleTypeQualityControlControlledVocabularyContainingOrderByCreationDateDesc(
			String filename, String cheksum, String labsystemName, String sampleTypeId);

	
	public List <File> findByCreationDateAfter(Date date);

}
