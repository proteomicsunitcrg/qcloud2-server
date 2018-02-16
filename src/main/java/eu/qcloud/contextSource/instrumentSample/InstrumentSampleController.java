package eu.qcloud.contextSource.instrumentSample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstrumentSampleController {
	@Autowired
	private InstrumentSampleService elementService;
	
	@RequestMapping(value="/element",method= RequestMethod.POST)
	public InstrumentSample addElement(@RequestBody InstrumentSample element) {
		return elementService.addElement(element);
	}
}
