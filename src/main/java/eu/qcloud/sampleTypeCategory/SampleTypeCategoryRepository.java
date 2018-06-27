package eu.qcloud.sampleTypeCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface SampleTypeCategoryRepository extends CrudRepository<SampleTypeCategory, Long> {

	Optional<SampleTypeCategory> findByName(String name);
	
	Optional<SampleTypeCategory> findByApiKey(UUID apiKey);

	List<SampleTypeCategory> findBySampleTypeComplexity(SampleTypeComplexity complexity);

}
