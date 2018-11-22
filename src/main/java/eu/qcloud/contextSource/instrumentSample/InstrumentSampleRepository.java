package eu.qcloud.contextSource.instrumentSample;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface InstrumentSampleRepository extends CrudRepository<InstrumentSample, Long> {
	
	public InstrumentSample findByQualityControlControlledVocabulary(String qualityControlControlledVocabulary);
	
	public Optional<InstrumentSample> findByApiKey(UUID apiKey);

}
