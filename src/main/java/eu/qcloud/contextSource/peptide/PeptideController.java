package eu.qcloud.contextSource.peptide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeptideController {
	@Autowired
	private PeptideService peptideService;
	
	@RequestMapping(value="/peptide",method= RequestMethod.POST)
	public Peptide addPeptide(@RequestBody Peptide peptide) {
		return peptideService.addPeptide(peptide);
	}
	
	@RequestMapping(value="/peptide",method= RequestMethod.GET)
	public List<Peptide> getAllPeptides() {
		return peptideService.getAllPeptides();
	}
	@RequestMapping(value="/peptide/{peptideId}", method=RequestMethod.GET)
	public Peptide getPeptideByPeptideId(@PathVariable Long peptideId) {
		return peptideService.getPeptideById(peptideId);
		
	}
}
