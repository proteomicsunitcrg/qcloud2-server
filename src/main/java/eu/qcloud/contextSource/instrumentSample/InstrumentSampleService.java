package eu.qcloud.contextSource.instrumentSample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentSampleService {
	@Autowired
	private InstrumentSampleRepository elementRepository;
	
	public InstrumentSample addElement(InstrumentSample e) {
		return elementRepository.save(e);		
	}
	
}
