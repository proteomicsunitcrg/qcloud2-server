package eu.qcloud.guideset;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface GuideSetRepository extends CrudRepository<GuideSet, Long> {

	Optional<GuideSet> findOptionalByApiKey(UUID guideSetApiKey);
	
	// GuideSet findByLabSystemIdAndIsActiveTrue(Long labSystemId);
	
	// GuideSet findByLabSystemIdAndSampleTypeIdAndIsActiveTrue(Long labSystemId, Long sampleTypeId);
	
	// List<GuideSet> findByLabSystemId(Long labSystemId);
	
	// List<GuideSet> findByLabSystemApiKeyAndIsActiveTrue(UUID labSystemApiKey);
	
	
}
