package eu.qcloud.sampleComposition;

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

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.sampleComposition.SampleCompositionRepository.PeptidesFromSample;

/**
 * Sample composition controller.
 *
 * @author dmancera
 *
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class SampleCompositionController {

	@Autowired
	private SampleCompositionService sampleCompositionService;

	/**
	 * Add a new peptide to a sample type
	 *
	 * @param sampleComposition a sample composition
	 * @return the inserted peptide within a sample composition
	 */
	@RequestMapping(value = "/api/samplecomposition", method = RequestMethod.POST)
	public SampleComposition addSampleComposition(@RequestBody SampleComposition sampleComposition) {
		return sampleCompositionService.addSampleComposition(sampleComposition);
	}

	/**
	 * Get all sample compositions
	 *
	 * @return a list with all sample compositions
	 */
	@RequestMapping(value = "/api/samplecomposition", method = RequestMethod.GET)
	public List<SampleComposition> getAll() {
		return sampleCompositionService.getAllSampleComposition();
	}

	/**
	 * Return a list of all sample composition that have a certain peptide
	 *
	 * @param peptideSequence the sequence of the peptide
	 * @return a list of sample composition matching the required criteria
	 */
	@RequestMapping(value = "/api/samplecomposition/peptide/{peptideSequence}", method = RequestMethod.GET)
	public List<SampleComposition> getAllSampleCompositionByPeptideSequence(@PathVariable String peptideSequence) {
		return sampleCompositionService.getAllSampleCompositionByPeptideSequence(peptideSequence);
	}

	/**
	 * Delete a peptide from a sample
	 *
	 * @param peptideSequence the peptide sequence to delete
	 * @param sampleTypeQCCV  the sample type qccv to delete from
	 * @return true if ok, false if something wrong
	 */
	@RequestMapping(value = "/api/samplecomposition/peptide/{peptideSequence}/sample/{sampleTypeQCCV}", method = RequestMethod.DELETE)
	public boolean deleteSampleComposition(@PathVariable String peptideSequence, @PathVariable String sampleTypeQCCV) {
		// Get the sample composition by id
		sampleCompositionService.deleteFromSampleComposition(peptideSequence, sampleTypeQCCV);

		return true;
	}

	/**
	 * Find all peptides from a given sample type
	 *
	 * @param sampleTypeName a list of peptides from a sample type
	 * @return
	 */
	@RequestMapping(value = "/api/samplecomposition/sample/{sampleTypeName}", method = RequestMethod.GET)
	public List<PeptidesFromSample> findAllPeptidesBySampleTypeName(@PathVariable String sampleTypeName) {
		return sampleCompositionService.findAllPeptidesBySampleTypeName(sampleTypeName);
	}

	/**
	 * Find all peptides by its qqcv
	 *
	 * @param qqcv
	 * @return
	 */
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/samplecomposition/qqcv/{qqcv}", method = RequestMethod.GET)
	public List<SampleComposition> findAllBySampleTypeQQCV(@PathVariable String qqcv) {
		return sampleCompositionService.getAllSampleCompositionBySampleTypeQQCV(qqcv);
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
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

}
