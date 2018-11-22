package eu.qcloud.contextSource.instrumentSample;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import eu.qcloud.traceColor.TraceColor;
import eu.qcloud.traceColor.TraceColorRepository;
/**
 * Instrument sample service
 * @author dmancera
 *
 */
@Service
public class InstrumentSampleService {
	@Autowired
	private InstrumentSampleRepository instrumentSampleRepository;
	
	@Autowired
	private TraceColorRepository traceColoRepository;
	
	public InstrumentSample addElement(InstrumentSample e) {
		// check if exists
		InstrumentSample is = instrumentSampleRepository.findByQualityControlControlledVocabulary(e.getqCCV());
		if(is != null) {
			throw new DataIntegrityViolationException("Instrument sample already exists...");
		}
		
		Optional<TraceColor> traceColor = traceColoRepository.findByApiKey(e.getTraceColor().getApiKey());
		if(!traceColor.isPresent()) {
			throw new DataIntegrityViolationException("Color not exists...");
		}
		e.setApiKey(UUID.randomUUID());
		e.setTraceColor(traceColor.get());
		return instrumentSampleRepository.save(e);
	}

	public List<InstrumentSample> getAllInstrumentSample() {
		List<InstrumentSample> instrumentSamples = new ArrayList<>();
		instrumentSampleRepository.findAll().forEach(instrumentSamples::add);;
		return instrumentSamples;
	}
	
	public Optional<InstrumentSample> findById(Long instrumentSampleId) {
		return instrumentSampleRepository.findById(instrumentSampleId);
	}
	
	public InstrumentSample findByQCCV(String qccv) {
		return instrumentSampleRepository.findByQualityControlControlledVocabulary(qccv);
	}

	public InstrumentSample updateInstrumentSample(InstrumentSample instrumentSample) {
		Optional<InstrumentSample> is = instrumentSampleRepository.findByApiKey(instrumentSample.getApiKey());
		InstrumentSample iss = instrumentSampleRepository.findByQualityControlControlledVocabulary(instrumentSample.getqCCV());
		Optional<TraceColor> traceColor = traceColoRepository.findByApiKey(instrumentSample.getTraceColor().getApiKey());
		if(!traceColor.isPresent()) {
			throw new DataIntegrityViolationException("Color not found.");
		}
		if(is.isPresent()) {
			if(iss!= null) {
				if(is.get().getId() != iss.getId()) {
					// throw repeteated qccv
					throw new DataIntegrityViolationException("This qccv is already in use.");
				} else {
					// update
					instrumentSample.setTraceColor(traceColor.get());
					instrumentSample.setId(is.get().getId());
					return instrumentSampleRepository.save(instrumentSample);
				}
			} else {
				instrumentSample.setTraceColor(traceColor.get());
				instrumentSample.setId(is.get().getId());
				return instrumentSampleRepository.save(instrumentSample);
			}
		} else {
			// throw not found
			throw new DataIntegrityViolationException("Instrument sample not found.");
		}
	}
	
}
