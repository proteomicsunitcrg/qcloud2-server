package eu.qcloud.contextSource.instrumentSample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Instrument sample controller
 * @author dmancera
 *
 */
@RestController
public class InstrumentSampleController {
	@Autowired
	private InstrumentSampleService instrumentSampleService;
	
	@RequestMapping(value="/api/contextsource/instrumentsample",method= RequestMethod.POST)
	public InstrumentSample addElement(@RequestBody InstrumentSample element) {
		return instrumentSampleService.addElement(element);
	}
	@RequestMapping(value="/api/contextsource/instrumentsample",method= RequestMethod.GET)
	public List<InstrumentSample> getAllInstrumentSamples() {
		return instrumentSampleService.getAllInstrumentSample();
	}
}
