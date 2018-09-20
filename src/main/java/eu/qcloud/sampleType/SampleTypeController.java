package eu.qcloud.sampleType;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.sampleType.SampleTypeRepository.SampleTypeOnlyName;
import eu.qcloud.sampleType.SampleTypeRepository.WithPeptide;
import eu.qcloud.sampleTypeCategory.SampleTypeComplexity;
/**
 * Sample type service
 * @author dmancera
 *
 */
@RestController

public class SampleTypeController {
	@Autowired
	private SampleTypeService sampleTypeService;
	
	/**
	 * Add a new sample type to the database
	 * @param sampleType the sample type to insert
	 * @param sampleTypeCategoryApiKey the apikey of the category
	 * @return the inserted sample type
	 */	
	@RequestMapping(value="/api/sample/{sampleTypeCategoryApiKey}",method= RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public SampleType addSampleType(@RequestBody SampleType sampleType,@PathVariable UUID sampleTypeCategoryApiKey) {
		return sampleTypeService.addSampleType(sampleType,sampleTypeCategoryApiKey);
	}
	
	/**
	 * Return all sample types
	 * @return a list with all sample types 
	 */
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value="/api/sample", method=RequestMethod.GET)
	public List<SampleTypeOnlyName> getAllSampleType() {
		return sampleTypeService.getAllSampleType();
	}
	
	/**
	 * Return a list of all sample type that are not in a 
	 * HIGHWITHISOTOPOLOGUE complexity
	 * @return a list of sample types
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/sample/noiso", method=RequestMethod.GET)
	public List<SampleTypeOnlyName> getAllSampleTypesNoIsotopolgues() {
		return sampleTypeService.getAllSampleTypeNoIsotopologues();		
	}
	
	/**
	 * Return a list of all sample type that are 
	 * HIGHWITHISOTOPOLOGUE complexity
	 * @return a list of sample types
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/sample/yesiso", method=RequestMethod.GET)
	public List<SampleTypeOnlyName> getAllSampleTypesIsotopolgues() {
		return sampleTypeService.getAllSampleTypeIsotopologues();		
	}
	
	/**
	 * Get a list of all sample types with a list of 
	 * the sample type peptides
	 * @return a list of the sample types with a list its peptides
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/sample/composition", method=RequestMethod.GET)
	public List<WithPeptide> getFullSampleType() {
		return sampleTypeService.getAllSampleTypeWithPeptide();
	}
	
	/**
	 * Get a sample type with its QC CV
	 * @param qCCv the qccv to look for
	 * @return a peptide or null
	 */
	@RequestMapping(value="/api/sample/qccv/{qCCV}", method=RequestMethod.GET)
	public SampleType getSampleTypeByQCCv(@PathVariable String qCCV) {
		return sampleTypeService.getSampleTypeByQCCV(qCCV);
	}
	
	/**
	 * Make the given sample as main in its category
	 * Only a sample type per category can be main
	 * @param sampleType
	 */
	@RequestMapping(value="/api/sample/makemain/{sampleTypeCategoryApiKey}/{sampleTypeQCCV}", method=RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public void doMainSampleType(@PathVariable UUID sampleTypeCategoryApiKey,@PathVariable String sampleTypeQCCV) {
		sampleTypeService.makeMainSampleType(sampleTypeCategoryApiKey,sampleTypeQCCV);
	}
	
	/**
	 * Get all sample types by its complexity
	 * @param complexity the complexity to look into
	 * @return a list with the requested sample types
	 */
	@RequestMapping(value="/api/sample/type/{complexity}", method=RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List<SampleTypeOnlyName> findAllByCategoryComplexity(@PathVariable(value="complexity") SampleTypeComplexity complexity) {
		return sampleTypeService.findByCategorySampleTypeComplexity(complexity);
	}
	
	/**
	 * Get the default sample type by sample type category
	 * @param sampleTypeCategoryId
	 * @return
	 */
	@RequestMapping(value="/api/sample/main/{sampleTypeCategoryApiKey}", method=RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public SampleType getDefaultSampleTypeBySampleTypeCategoryApiKey(@PathVariable UUID sampleTypeCategoryApiKey) {
		return sampleTypeService.getMainSampleTypeBySampleTypeCategoryApiKey(sampleTypeCategoryApiKey);
	}
	
	
	/*
	 * Error handlers
	 */
	@ExceptionHandler(InvalidActionException.class)
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException{
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
	@ExceptionHandler(PersistenceException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
	}
	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNotFound(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}
	
}
