package eu.qcloud.contextSource.instrumentSample;

import org.springframework.data.repository.CrudRepository;

public interface InstrumentSampleRepository extends CrudRepository<InstrumentSample, Long> {
	
	public InstrumentSample findByQualityControlControlledVocabulary(String qualityControlControlledVocabulary);

}
