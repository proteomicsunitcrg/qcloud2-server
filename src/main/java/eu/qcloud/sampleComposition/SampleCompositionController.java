package eu.qcloud.sampleComposition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class SampleCompositionController {
	
	@Autowired
	private SampleCompositionService sampleCompositionService;
	
	@RequestMapping(value="/api/samplecomposition", method=RequestMethod.POST)
	public SampleComposition addSampleComposition(@RequestBody SampleComposition sampleComposition) {
		return sampleCompositionService.addSampleComposition(sampleComposition);
	}
	@RequestMapping(value="/api/samplecomposition", method=RequestMethod.GET)
	public List<SampleComposition> getAll() {
		return sampleCompositionService.getAllSampleComposition();
	}
}
