package eu.qcloud.sampleType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.contextSource.peptide.PeptideService;
import eu.qcloud.sampleType.SampleTypeRepository.OnlyName;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class SampleTypeController {
	@Autowired
	private SampleTypeService sampleTypeService;
	@Autowired
	private PeptideService peptideService;
	
	@RequestMapping(value="/sample",method= RequestMethod.POST)
	public SampleType addElement(@RequestBody SampleType sampleType) {
		return sampleTypeService.addSampleType(sampleType);
	}
	@RequestMapping(value="/sample", method=RequestMethod.GET)
	public List<SampleType> getAllSampleType() {
		return sampleTypeService.getAllSampleType();
	}
	
	
	@RequestMapping(value="/sample/{sampleTypeId}",method= RequestMethod.POST)
	public SampleType addPeptideToSampleType(@PathVariable Long sampleTypeId,@RequestBody List<Peptide> peptides) {
		SampleType s = sampleTypeService.getSampleTypeById(sampleTypeId);
		List<Peptide> peptideList = peptideService.getPeptidesByIds(peptides);
		s.setPeptides(peptideList);
		return sampleTypeService.addPeptideToSampleType(s, peptideList);
		//return sampleTypeService.addSampleType(s);
	}
	@RequestMapping(value="/sample/{sampleId}", method=RequestMethod.GET)
	public OnlyName getById(@PathVariable Long sampleId) {
		return sampleTypeService.getSampleById(sampleId);
	}
	
}
