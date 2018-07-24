package eu.qcloud.contextSource.instrumentSample;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Instrument sample service
 * @author dmancera
 *
 */
@Service
public class InstrumentSampleService {
	@Autowired
	private InstrumentSampleRepository elementRepository;
	
	public InstrumentSample addElement(InstrumentSample e) {
		e.setApiKey(UUID.randomUUID());
		return elementRepository.save(e);
	}

	public List<InstrumentSample> getAllInstrumentSample() {
		List<InstrumentSample> instrumentSamples = new ArrayList<>();
		elementRepository.findAll().forEach(instrumentSamples::add);;
		return instrumentSamples;
	}
	
	public Optional<InstrumentSample> findById(Long instrumentSampleId) {
		return elementRepository.findById(instrumentSampleId);
	}
	
	public InstrumentSample findByQCCV(String qccv) {
		return elementRepository.findByQualityControlControlledVocabulary(qccv);
	}
	
}
