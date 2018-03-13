package eu.qcloud.view;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import eu.qcloud.chart.Chart;

public interface DefaultViewRepository extends CrudRepository<DefaultView, DefaultViewId> {
	
	public DefaultViewName findFirstByChartCvId(Long cvId);
	
	public List<DefaultViewDisplay> findByViewId(Long viewId);
	
	interface DefaultViewName {
		Long getViewId();
		String getViewName();
	}
	
	
	interface DefaultViewDisplay {
		View getView();
		Chart getChart();
		int getCol();
		int getRow();
		/*
		Long getViewId();
		Long getChartId();
		String getChartName();
		int getCol();
		int getRow();
		*/
	}
	
}
