package eu.qcloud.contextSource.peptide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.contextSource.peptide.PeptideRepository.PeptideWithSampleType;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class PeptideController {
	@Autowired
	private PeptideService peptideService;
	
	@RequestMapping(value="/api/contextsource/peptide",method= RequestMethod.POST)
	public Peptide addPeptide(@RequestBody Peptide peptide) {		
		return peptideService.addPeptide(peptide);
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
