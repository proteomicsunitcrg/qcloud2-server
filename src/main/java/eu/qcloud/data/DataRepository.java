package eu.qcloud.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.qcloud.file.File;
@Repository
public interface DataRepository extends PagingAndSortingRepository<Data, DataId>{

	MiniData findByDataIdFileId(Long fileId);
	
	@Query("SELECT d as d from Data d where (d.contextSource, d.param) in (select c.contextSource, c.param from ChartParams c where c.chart.id=?1 ) and d.file in (select f from File f where f.creationDate between ?2 and ?3 and f.labSystem.id=?4 and f.sampleType.id=?5) order by d.file.creationDate asc")
	List<Data> findPlotData(Long chartId, java.util.Date start, java.util.Date end, Long labSystemId, Long sampleTypeId);
	
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
	@Query("SELECT d as d from Data d where d.contextSource.id=?1 and d.param.id=?2 and d.file in (select f from File f where f.creationDate between ?3 and ?4 and f.labSystem.id=?5 and f.sampleType.id=?6) order by d.file.creationDate asc")
	List<Data> findParamData(Long contextSourceId, Long paramId, java.util.Date start, java.util.Date end, Long labSystemId, Long sampleTypeId);
	
	@Query("SELECT d as d from Data d where d.contextSource.sequence=?1 and d.param.id=?2 and d.file in (select f from File f where f.creationDate between ?3 and ?4 and f.labSystem.id=?5 and f.sampleType.id=?6) order by d.file.creationDate asc")
	List<Data> findParamPeptideData(String contextSourceSequence, Long paramId, java.util.Date start, java.util.Date end, Long labSystemId, Long sampleTypeId);
	
	Data findByFileIdAndParamIdAndContextSourceId(Long fileId, Long paramId, Long contextSourceId);
	
	@Query("SELECT file from Data d where d.contextSource.abbreviated = ?1 and d.param.id=?2 and d.file.sampleType.id = ?3 and d.file.labSystem.id = ?4 and d.value != 0")
	List<File> findLastFilesWithoutZeroValue(String abbreviated, Long paramId, Long sampleTypeId, Long labSystemId, Pageable page);
	
	@Query("SELECT file from Data d where d.contextSource.abbreviated = ?1 and d.param.id=?2 and d.file.sampleType.id = ?3 and d.file.labSystem.id = ?4")
	List<File> findLastFilesIncludingZeroValue(String abbreviated, Long paramId, Long sampleTypeId, Long labSystemId, Pageable page);
	
	public interface MiniData {
		
		String getFileFilename();
		
		@JsonFormat(pattern="yyyy-MM-dd hh:mm")
		Date getFileCreationDate();
		
		String getContextSourceName();
		
		Float getValue();
		
		
	}
}
