package eu.qcloud.view;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import eu.qcloud.chart.ChartRepository.NoView;
import eu.qcloud.view.ViewRepository.ViewWithoutDisplay;

public interface ViewDisplayRepository extends CrudRepository<ViewDisplay, Long> {
	
	//List<DefaultView> findByViewId(Long viewId);
	
	List<WithOutViewDisplay> findByViewId(Long viewId);
	
	void deleteByViewId(Long viewId);
	
	int countByViewId(Long viewId);
	
	interface WithOutViewDisplay {
		Long getId();
		NoView getChart();
		//ViewWithoutDisplay getView();
		int getCol();
		int getRow();
	}
		
}
