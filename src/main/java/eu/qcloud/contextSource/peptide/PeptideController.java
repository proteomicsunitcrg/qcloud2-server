package eu.qcloud.contextSource.peptide;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

import eu.qcloud.exceptions.InvalidActionException;
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
	
	@RequestMapping(value="/api/contextsource/peptide/{peptideId}",method= RequestMethod.GET)
	public Optional<Peptide> findPeptide(@PathVariable Long peptideId) {
		return peptideService.findPeptideById(peptideId);
	}

	@RequestMapping(value="/api/contextsource/peptide",method= RequestMethod.GET)
	public List<Peptide> getAllPeptides() {
		return peptideService.getAllPeptides();
	}
	/**
	 * Update a peptide in the database. If the updated peptide shares the name with
	 * another peptide it will throw an error.
	 * @param peptide
	 * @param peptideId
	 * @return
	 */
	@RequestMapping(value="/api/contextsource/peptide/{peptideId}",method= RequestMethod.PUT)
	public Peptide updatePeptide(@RequestBody Peptide peptide,@PathVariable Long peptideId) {
		Optional<Peptide> p = peptideService.getPeptideById(peptideId);
		if(peptide.getId() == p.get().getId()) {
			if(checkIfPeptideNameExists(peptide.getName()) && !peptide.getName().equals(p.get().getName())) {
				throw new DataIntegrityViolationException("This peptide already exists");
			}else {
				return peptideService.addPeptide(peptide);				
			}			
		}else {
			throw new DataIntegrityViolationException("Invalid peptide");
		}
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
