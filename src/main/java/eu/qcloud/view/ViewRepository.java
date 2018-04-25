package eu.qcloud.view;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.CV.CV;
import eu.qcloud.security.model.User;

@Repository
public interface ViewRepository extends CrudRepository<View, Long> {

	View findByCvId(Long cvId);
	
	List<View> findByIsDefaultTrue();
	
	interface ViewWithoutDisplay {
		Long getId();
		String getName();
		User getUser();
		CV getCv();
		
		
	}
		
}
