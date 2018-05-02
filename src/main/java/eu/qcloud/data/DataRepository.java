package eu.qcloud.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;
@Repository
public interface DataRepository extends CrudRepository<Data, DataId>{

	MiniData findByDataIdFileId(Long fileId);
	/*
	 * old query that works for datasources
	@Query("SELECT d as d from Data d where (d.contextSource, d.param) in (select c.contextSource, c.param from ChartParams c where c.chart.id=?1 ) and d.file in (select f from File f where f.creationDate between ?2 and ?3 and f.dataSource.id=?4 and f.sampleType.id=?5) order by d.file.id asc")
	List<Data> findPlotData(Long chartId, java.util.Date start, java.util.Date end, Long dataSourceId, Long sampleTypeId);
	*/
	//@Query("SELECT d as d from Data d where (d.contextSource, d.param) in (select c.contextSource, c.param from ChartParams c where c.chart.id=?1 ) and d.file in (select f from File f where f.creationDate between ?2 and ?3 and f.labSystem.id=?4 and f.sampleType.id=?5) order by d.file.id asc")
	@Query("SELECT d as d from Data d where (d.contextSource, d.param) in (select c.contextSource, c.param from ChartParams c where c.chart.id=?1 ) and d.file in (select f from File f where f.creationDate between ?2 and ?3 and f.labSystem.id=?4 and f.sampleType.id=?5) order by d.file.creationDate asc")
	List<Data> findPlotData(Long chartId, java.util.Date start, java.util.Date end, Long labSystemId, Long sampleTypeId);
	
	List<MiniData> findByFileCreationDateBetween(java.sql.Date start, java.sql.Date end);
	
	List<MiniData> findByFileCreationDateBetweenAndFileLabSystemId(java.sql.Date start, java.sql.Date end, Long labSystemId);
	
	public interface MiniData {
		
		String getFileFilename();
		
		@JsonFormat(pattern="yyyy-MM-dd hh:mm")
		Date getFileCreationDate();
		
		String getContextSourceName();
		
		Float getValue();
		
		
	}
}
