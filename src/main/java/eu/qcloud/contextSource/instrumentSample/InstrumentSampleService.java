package eu.qcloud.contextSource.instrumentSample;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentSampleService {
	@Autowired
	private InstrumentSampleRepository elementRepository;
	
	public InstrumentSample addElement(InstrumentSample e) {
		return elementRepository.save(e);		
	}

	public List<InstrumentSample> getAllInstrumentSample() {
		List<InstrumentSample> instrumentSamples = new ArrayList<>();
		elementRepository.findAll().forEach(instrumentSamples::add);;
		return instrumentSamples;

	}
	
}
