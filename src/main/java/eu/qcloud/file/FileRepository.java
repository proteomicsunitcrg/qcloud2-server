package eu.qcloud.file;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonFormat;


public interface FileRepository extends CrudRepository<File, Long>{
	
	public OnlySmalls findFileById(Long idFile);
	
	public File findByChecksum(String checksum);
	
	@Projection(name = "file_mini", types = File.class)
	public interface OnlySmalls {
		String getFilename();
		Long getDataSourceId();
		String getDataSourceName();
		
		@JsonFormat(pattern="yyyy-MM-dd hh:mm")
		Date getCreationDate();
	}
}
