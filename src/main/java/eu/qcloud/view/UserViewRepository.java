package eu.qcloud.view;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserViewRepository extends CrudRepository<UserView, UserViewId> {
	
	
	public List<ViewDisplay> findByViewId(Long viewId);
	
	interface ViewDisplay {
		Long getViewId();
		Long getChartId();
		String getChartName();
		int getCol();
		int getRow();
		Long getDataSourceId();
	}
	
}
