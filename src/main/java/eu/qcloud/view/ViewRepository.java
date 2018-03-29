package eu.qcloud.view;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends CrudRepository<View, Long> {

	View findByCvId(Long cvId);
	
	List<View> findByIsDefaultTrue();
		
}
