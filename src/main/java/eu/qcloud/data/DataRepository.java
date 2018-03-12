package eu.qcloud.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;
@Repository
public interface DataRepository extends CrudRepository<Data, DataId>{
	
	MiniData findByDataIdFileId(Long fileId);
	
	List<MiniData> findByFileCreationDateBetween(java.sql.Date start, java.sql.Date end);
	
	List<MiniData> findByFileCreationDateBetweenAndFileDataSourceId(java.sql.Date start, java.sql.Date end, Long dataSourceId);
	
	public interface MiniData {
		
		String getFileFilename();
		
		@JsonFormat(pattern="yyyy-MM-dd hh:mm")
		Date getFileCreationDate();
		
		String getContextSourceName();
		
		Float getValue();
		
		
	}
}
