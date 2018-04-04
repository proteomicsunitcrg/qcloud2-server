package eu.qcloud.view;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ViewDisplayRepository extends CrudRepository<ViewDisplay, Long> {
	
	List<DefaultView> findByViewId(Long viewId);
	
	void deleteByViewId(Long viewId);
	
	int countByViewId(Long viewId);
		
}
