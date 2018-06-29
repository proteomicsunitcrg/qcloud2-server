package eu.qcloud.contextSource.peptide;

import java.io.IOException;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.contextSource.peptide.PeptideRepository.OnlyPeptide;
import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.sampleTypeCategory.SampleTypeComplexity;
/**
 * Peptide controller. A peptide is an extension
 * of contextSource.
 * @author dmancera
 *
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class PeptideController {
	@Autowired
	private PeptideService peptideService;
	
	/**
	 * Add a new peptide into the database. If there
	 * is already a peptide with the same name it will 
	 * throw an exception. It should be catched in the client.
	 * @param peptide the peptide to add
	 * @return the added peptide
	 */
	@RequestMapping(value="/api/contextsource/peptide",method= RequestMethod.POST)
	public Peptide addPeptide(@RequestBody Peptide peptide) {		
		if(peptideService.findePeptideByName(peptide.getName())==null) {
			return peptideService.addPeptide(peptide);			
		}else {
			throw new DataIntegrityViolationException("This peptide already exists");
		}
	}
	/**
	 * Add a new peptide into the database. THis function
	 * will be used only with the db migrator
	 * @param peptide the peptide to add
	 * @return the added peptide
	 */
	@RequestMapping(value="/api/contextsource/peptideauto",method= RequestMethod.POST)
	public Peptide addPeptideAuto(@RequestBody Peptide peptide) {		
		if(peptideService.findePeptideByName(peptide.getName())==null) {
			return peptideService.addPeptide(peptide);			
		}else {
			return peptideService.findPeptideBySequence(peptide.getSequence());
		}
	}
	
	/**
	 * Find a peptide by its sequence
	 * @param peptideSequence the sequence to look for
	 * @return a peptide
	 */
	@RequestMapping(value="/api/contextsource/peptide/{peptideSequence}",method= RequestMethod.GET)
	public Peptide findPeptide(@PathVariable String peptideSequence) {
		return peptideService.getPeptideBySequence(peptideSequence);
	}
	
	/**
	 * Find all peptides that are not isotopologues
	 * @return a list of peptids
	 */
	@RequestMapping(value="/api/contextsource/peptide",method= RequestMethod.GET)
	public List<Peptide> getAllPeptides() {
		return peptideService.getAllPeptides();
	}
	
	/**
	 * Get all peptides except the given sample type complexity
	 * @param complexity the complexity to exclude peptides from
	 * @return a list of peptides
	 */
	@RequestMapping(value="/api/contextsource/peptide/not/{complexity}",method= RequestMethod.GET)
	public List<Peptide> getAllPeptidesNotComplexity(@PathVariable(value="complexity") SampleTypeComplexity complexity) {
		
		return null;
	}
	
	
	/**
	 * Update a peptide in the database. If the updated peptide shares the name with
	 * another peptide it will throw an error.
	 * @param peptide
	 * @param peptideId
	 * @return
	 */
	@RequestMapping(value="/api/contextsource/peptide/{peptideSequence}",method= RequestMethod.PUT)
	public Peptide updatePeptide(@RequestBody Peptide peptide,@PathVariable String peptideSequence) {
		Peptide p = peptideService.getPeptideBySequence(peptideSequence);
		if(checkIfPeptideNameExists(peptide.getName()) && !peptide.getName().equals(p.getName())) {
			throw new DataIntegrityViolationException("This peptide already exists");
		}else {
			return peptideService.addPeptide(peptide);				
		}			
		
	}
	
	/**
	 * Find a peptide by its sequence
	 * @param sequence
	 * @return
	 */
	@RequestMapping(value="/api/contextsource/peptide/sequence/{sequence}",method= RequestMethod.GET)
	public OnlyPeptide getPeptideBySequence(@PathVariable String sequence) {
		return peptideService.findOnlyPeptideBySequence(sequence);
	}
	
	private boolean checkIfPeptideNameExists(String name) {
		if(peptideService.findePeptideByName(name)!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * Exception handlers
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

	@ExceptionHandler(PersistenceException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
	}
	@ExceptionHandler(InvalidActionException.class)
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException{
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
}
