package eu.qcloud.sampleComposition;

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
import eu.qcloud.sampleComposition.SampleCompositionRepository.PeptidesFromSample;
/**
 * Sample composition controller.
 * @author dmancera
 *
 */
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
	@RequestMapping(value="/api/samplecomposition/peptide/{peptideId}", method=RequestMethod.GET)
	public List<SampleComposition> getAllSampleCompositionByPeptideId(@PathVariable Long peptideId) {
		return sampleCompositionService.getAllSampleCompositionByPeptideId(peptideId);
	}
	@RequestMapping(value="/api/samplecomposition/peptide/{peptideId}/sample/{sampleTypeId}", method=RequestMethod.DELETE)
	public boolean deleteSampleComposition(@PathVariable Long peptideId,@PathVariable  Long sampleTypeId) {
		// Get the sample composition by id
		SampleCompositionId scId = new SampleCompositionId();
		scId.setPeptideId(peptideId);
		scId.setSampleTypeId(sampleTypeId);
		Optional<SampleComposition> sc = sampleCompositionService.getSampleCompositionById(scId);
		if(!sc.isPresent()) {
			throw new DataIntegrityViolationException("Sample composition does not exist.");
		}
		
		return sampleCompositionService.deleteSampleComposition(sc.get());
	}
	@RequestMapping(value="/api/samplecomposition/sample/{sampleTypeName}", method=RequestMethod.GET)
	public List<PeptidesFromSample> findAllPeptidesBySampleTypeName(@PathVariable String sampleTypeName) {
		return sampleCompositionService.findAllPeptidesBySampleTypeName(sampleTypeName);
		
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
