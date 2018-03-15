package eu.qcloud.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;
@Repository
public interface DataRepository extends CrudRepository<Data, DataId>{
	/*
	 * SELECT * from data d where (d.context_source_id, d.param_id) 
		in (select c.context_source_id, c.param_id from chart_params c where c.chart_id=56) 
		and d.file_id in (select f.id from file f where f.creation_date between '2018-01-01' and '2018-03-01' and f.data_source_id=58)
	 */
	MiniData findByDataIdFileId(Long fileId);
	
	@Query("SELECT d as d from Data d where (d.contextSource, d.param) in (select c.contextSource, c.param from ChartParams c where c.chart.id=?1 ) and d.file in (select f from File f where f.creationDate between ?2 and ?3 and f.dataSource.id=?4) order by d.file.id asc")
	List<Data> findPlotData(Long chartId, java.sql.Date start, java.sql.Date end, Long dataSourceId);
	
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
