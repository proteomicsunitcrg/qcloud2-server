package eu.qcloud.chart.chartParams;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.param.Param;

public interface ChartParamsRepository extends CrudRepository<ChartParams, ChartParamsId>{
	
	public List<FullParams> findByChartParamsIdChartId(Long chartId);
	
	public ChartParams findTopByChartId(Long chartId);
	
	interface OnlyParams {
		Long getChartId();
	}
	
	public void deleteByChartId(Long chartId);
	
	@Query("select c from ChartParams c where c.chartParamsId.chartId=?1")
	public List<ChartParams> getByChartParamsIdChartId(Long chartId);
	
	/**
	 * This interface is used to avoid returning
	 * circular and extra information to the user.
	 * @author dmancera
	 *
	 */
	interface FullParams {
		Param getParam();
		ContextSource getContextSource();
	}
	
}
