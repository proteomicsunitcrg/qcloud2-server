package eu.qcloud.sampleTypeCategory;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SampleTypeCategoryRepository extends CrudRepository<SampleTypeCategory, Long> {

	Optional<SampleTypeCategory> findByName(String name);
	
	

}
