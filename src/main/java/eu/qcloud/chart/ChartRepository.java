package eu.qcloud.chart;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartRepository extends CrudRepository<Chart, Long>{

	List<ChartDescription> findById(Long chartId);
	
	interface ChartDescription {
		Long getId();
		String getName();
	}

}
