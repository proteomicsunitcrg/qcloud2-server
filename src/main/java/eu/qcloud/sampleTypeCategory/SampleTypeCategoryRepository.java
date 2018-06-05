package eu.qcloud.sampleTypeCategory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SampleTypeCategoryRepository extends CrudRepository<SampleTypeCategory, Long> {

	Optional<SampleTypeCategory> findByName(String name);

	List<SampleTypeCategory> findBySampleTypeComplexity(SampleTypeComplexity complexity);
	
	

}
