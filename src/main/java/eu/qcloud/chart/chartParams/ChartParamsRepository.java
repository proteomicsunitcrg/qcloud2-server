package eu.qcloud.chart.chartParams;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import eu.qcloud.contextSource.ContextSource;

public interface ChartParamsRepository extends CrudRepository<ChartParams, ChartParamsId>{
	
	public List<FullParams> findByChartParamsIdChartId(Long chartId);
	
	interface OnlyParams {
		Long getChartId();
	}
	
	interface FullParams {
		String getParamName();
		ContextSource getContextSource();
	}
}
