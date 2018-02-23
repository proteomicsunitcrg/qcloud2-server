package eu.qcloud.contextSource.peptide;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.contextSource.peptide.PeptideRepository.PeptideWithSampleType;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeService;

@RestController
public class PeptideController {
	@Autowired
	private PeptideService peptideService;
	
	@Autowired
	private SampleTypeService sampleTypeService;
	
	@RequestMapping(value="/api/contextsource/peptide",method= RequestMethod.POST)
	public Peptide addPeptide(@RequestBody Peptide peptide) {		
		for(SampleType s: peptide.getSampleTypes()) {
			SampleType ss = sampleTypeService.getSampleTypeById(s.getId());
			ss.getPeptides().add(peptide);
			sampleTypeService.addSampleType(ss);
		}
		
		return null;
		//return peptideService.addPeptide(peptide);
	}
	
	/*
	@RequestMapping(value="/api/contextsource/peptide",method= RequestMethod.GET)
	public List<OnlyPeptide> getAllPeptides() {
		return peptideService.getAllPeptides();
	}
	*/
	@RequestMapping(value="/api/contextsource/peptide",method= RequestMethod.GET)
	public List<PeptideWithSampleType> getAllPeptides() {
		return peptideService.getOnlyPeptides();
	}
	
}
