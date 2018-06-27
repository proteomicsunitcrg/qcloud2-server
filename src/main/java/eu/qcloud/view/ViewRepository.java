package eu.qcloud.view;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.CV.CV;
import eu.qcloud.security.model.User;

@Repository
public interface ViewRepository extends CrudRepository<View, Long> {

	//List<View> findByCvId(Long cvId);
	
	@Query("SELECT v from View v where v.cv.CVId=?1 and v.isDefault=true")
	List<View> findByCvCVId(String cvId);
	
	
	List<View> findByIsDefaultTrue();
	
	View findByCvIdAndSampleTypeCategoryId(Long cvId, Long sampleTypeCategoryId);
	
	View findByCvIdAndSampleTypeCategoryApiKey(Long cvId, UUID sampleTypeCategoryApiKey);
	
	interface ViewWithoutDisplay {
		Long getId();
		String getName();
		User getUser();
		CV getCv();
		
		
	}
		
}
