package eu.qcloud.file;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;

@Repository
public interface FileRepository extends CrudRepository<File, Long>{
	
	public OnlySmalls findFileById(Long idFile);
	
	public File findByChecksum(String checksum);
	
	public File findTop1ByLabSystemIdOrderByCreationDateDesc(Long labSystemId);
	
	public List<OnlySmalls> findTop10ByLabSystemIdAndSampleTypeIdOrderByCreationDateDesc(Long labSystemId,Long sampleTypeId);
	
	public File findTop1BySampleTypeQualityControlControlledVocabularyAndLabSystemApiKeyOrderByCreationDateDesc(String sampleTypeQCCV, UUID labSystemApikey);
	
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

	@Query("select f from File f where filename = ?1")
	public OnlyChecksum getFileByFilename(String filename);
}
