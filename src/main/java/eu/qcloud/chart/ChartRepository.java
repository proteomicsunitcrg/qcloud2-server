package eu.qcloud.chart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartRepository extends CrudRepository<Chart, Long>{

	Optional<Chart> findById(Long chartId);
	
	List<Chart> findByCvId(Long cvId);
	
	interface ChartDescription {
		Long getId();
		String getName();
	}

}
