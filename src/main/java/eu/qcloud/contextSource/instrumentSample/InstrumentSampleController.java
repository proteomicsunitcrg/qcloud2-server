package eu.qcloud.contextSource.instrumentSample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstrumentSampleController {
	@Autowired
	private InstrumentSampleService instrumentSampleService;
	
	@RequestMapping(value="/instrumentsample",method= RequestMethod.POST)
	public InstrumentSample addElement(@RequestBody InstrumentSample element) {
		return instrumentSampleService.addElement(element);
	}
}
