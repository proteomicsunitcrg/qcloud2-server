package eu.qcloud.sampleType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.sampleType.SampleTypeRepository.SampleTypeOnlyName;
import eu.qcloud.sampleType.SampleTypeRepository.WithPeptide;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class SampleTypeController {
	@Autowired
	private SampleTypeService sampleTypeService;
	
	
	@RequestMapping(value="/api/sample",method= RequestMethod.POST)
	public SampleType addSampleType(@RequestBody SampleType sampleType) {
		return sampleTypeService.addSampleType(sampleType);
	}
	
	@RequestMapping(value="/api/sample", method=RequestMethod.GET)
	public List<SampleTypeOnlyName> getAllSampleType() {
		return sampleTypeService.getAllSampleType();
	}
	
	
	@RequestMapping(value="/api/sample/composition", method=RequestMethod.GET)
	public List<WithPeptide> getFullSampleType() {
		return sampleTypeService.getAllSampleTypeWithPeptide();
	}
	
	@RequestMapping(value="/sample/{sampleId}", method=RequestMethod.GET)
	public SampleTypeOnlyName getById(@PathVariable Long sampleId) {
		return sampleTypeService.getSampleById(sampleId);
	}
	
}
