package eu.qcloud.file;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.qcloud.labsystem.LabSystemRepository.LabSystemNameAndApiKey;
import eu.qcloud.sampleType.SampleType;

@Repository
public interface FileRepository extends JpaRepository<File, Long>{
	
	public OnlySmalls findFileById(Long idFile);
	
	public File findByChecksum(String checksum);
	
	public File findTop1ByLabSystemIdOrderByCreationDateDesc(Long labSystemId);
	
	public File findTop1ByLabSystemIdOrderByCreationDateAsc(Long labSystemId);
	
	public File findTop1ByLabSystemIdAndSampleTypeIdOrderByCreationDateAsc(Long labSystemId, Long sampleTypeId);
	
	public File findTop1ByLabSystemIdAndSampleTypeIdOrderByCreationDateDesc(Long labSystemId, Long sampleTypeId);
	
	public long countByLabSystemIdAndSampleTypeIdAndCreationDateBetween(Long labSystemId, Long sampleTypeId, Date startDate, Date endDate);
	
	public long countByLabSystemApiKeyAndSampleTypeQualityControlControlledVocabularyAndCreationDateBetween(UUID labSystemApiKey, String sampleTypeQccv, Date startDate, Date endDate);
	
	public long countByLabSystemIdAndSampleTypeId(Long labSystemId, Long sampleTypeId);
	
	@Query("select distinct(f.sampleType) from File f where f.labSystem.id=?1")
	public List<SampleType> findDistinctSampleTypeByLabSystemId(Long labSystemId);
	
	@Query("select distinct(f.sampleType) from File f where f.labSystem.apiKey=?1")
	public List<SampleType> findDistinctSampleTypeByLabSystemApiKey(UUID labSystemApiKey);
	
	public List<OnlySmalls> findTop10ByLabSystemIdAndSampleTypeIdOrderByCreationDateDesc(Long labSystemId,Long sampleTypeId);
	
	public File findTop1BySampleTypeQualityControlControlledVocabularyAndLabSystemApiKeyOrderByCreationDateDesc(String sampleTypeQCCV, UUID labSystemApikey);
	
	public List<File> findByCreationDateAndLabSystemIdAndSampleTypeId(Date creationDate, Long LabSystemId, Long sampleTypeId);
	
	// @Query(value = "select * from file f where f.id in (select d.file_id from data d where d.param_id = ?1 and d.context_source_id = ?2)  and f.labsystem_id = ?3 and f.sample_type_id = ?4 order by f.creation_date desc limit 15",nativeQuery = true )
	@Query(value = "select * from file f where f.labsystem_id = ?1 and f.sample_type_id = ?2 order by f.creation_date desc limit 15",nativeQuery = true )
	public List<File> findForAutoPlotWithZero(Long labSystemId, Long sampleTypeId);
	
	@Query(value = "select * from file f where f.labsystem_id = ?1 and f.sample_type_id = ?2 and f.creation_date <= ?3 order by f.creation_date desc limit 15",nativeQuery = true )
	public List<File> findForNonConformityPlot(Long labSystemId, Long sampleTypeId, Date creationDate);
	
	@Query(value = "select * from file f where f.id in (select d.file_id from data d where d.value > 0 and d.param_id = ?1 and d.context_source_id = ?2)  and f.labsystem_id = ?3 and f.sample_type_id = ?4 order by f.creation_date desc limit 15",nativeQuery = true )
	public List<File> findForAutoPlotExcludeZero(Long paramId, Long contextSourceId, Long labSystemId, Long sampleTypeId);
	
	@Projection(name = "file_mini", types = File.class)
	public interface OnlySmalls {
		String getFilename();
		Long getLabSystemId();
		String getLabSystemName();
		
		@JsonFormat(pattern="yyyy-MM-dd hh:mm")
		Date getCreationDate();
	}
	
	public interface OnlyChecksum {
		public String getChecksum();
	}
	
	interface FileLabSystemNameAndApiKey {
		SampleType getSampleType();
		LabSystemNameAndApiKey getLabSystem();
		@JsonFormat(pattern="yyyy-MM-dd hh:mm")
		Date getCreationDate();
		String getChecksum();
		String getFilename();
	}

	@Query("select f from File f where filename = ?1")
	public OnlyChecksum getFileByFilename(String filename);

	public List<File> findByLabSystemIdAndSampleTypeId(Long id, Long id2, Pageable maxFiles);
	
	public List<File> findByLabSystemIdAndSampleTypeIdAndCreationDateBefore(Long id, Long id2, Date creationDate, Pageable maxFiles);

	public Optional<File> findOptionalByChecksum(String fileChecksum);

	

	
}
