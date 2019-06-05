package eu.qcloud.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;
@Repository
public interface DataRepository extends PagingAndSortingRepository<Data, DataId>{

	MiniData findByDataIdFileId(Long fileId);
	
	@Query("SELECT d as d from Data d where d.param.id=?1 and d.file.id = ?2 and d.contextSource.id = ?3")
	List<Data> findParamDataByFileAndContextSource(Long paramId, Long fileId, Long contextSourceId);
	
	/**
	 * Retrieve data of a single context source
	 * @param contextSourceId
	 * @param paramId
	 * @param start
	 * @param end
	 * @param labSystemId
	 * @param sampleTypeId
	 * @return
	 */
	@Query("SELECT d as d from Data d, File f where d.contextSource.id=?1 and d.param.id=?2 and d.file.id = f.id and f.creationDate between ?3 and ?4 and f.labSystem.id=?5 and f.sampleType.id=?6 order by d.file.creationDate asc")
	List<Data> findParamData(Long contextSourceId, Long paramId, java.util.Date start, java.util.Date end, Long labSystemId, Long sampleTypeId);
	
	Data findByFileIdAndParamIdAndContextSourceId(Long fileId, Long paramId, Long contextSourceId);
	
	Long countByContextSourceIdAndParamIdAndFileLabSystemApiKeyAndFileCreationDateBetweenAndCalculatedValueIsNotNull(Long contextSourceId, Long paramId, UUID labSystemApiKey, Date startDate, Date endDate);
	public interface MiniData {
		
		String getFileFilename();
		
		@JsonFormat(pattern="yyyy-MM-dd hh:mm")
		Date getFileCreationDate();
		
		String getContextSourceName();
		
		Float getValue();
		
		
	}
}
