package eu.qcloud.sampleTypeCategory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import eu.qcloud.exceptions.NotFoundException;
import eu.qcloud.sampleType.SampleType;

/**
 * Controller for the sample type category
 * @author dmancera
 *
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class SampleTypeCategoryController {
	
	@Autowired
	private SampleTypeCategoryService sampleTypeCategoryService;
	
	/**
	 * Add a new sample type category into the database
	 * @param sampleTypeCategory
	 * @return SampleTypeCategory the inserted sample type category
	 */
	@RequestMapping(value="/api/samplecategory",method= RequestMethod.POST)
	public void saveSampleTypeCategory(@RequestBody SampleTypeCategory sampleTypeCategory) {
		sampleTypeCategoryService.addSampleTypeCategory(sampleTypeCategory);	
	}
	/**
	 * Return a list of sample type categories
	 * @return
	 */
	@RequestMapping(value="/api/samplecategory",method= RequestMethod.GET)
	public List<SampleTypeCategory> getSampleTypeCategories() {
		return sampleTypeCategoryService.getAll();
	}
	
	@RequestMapping(value="/api/samplecategory/{sampleTypeCategoryApiKey}",method= RequestMethod.GET)
	public SampleTypeCategory getSampleTypeCategoryByApiKey(@PathVariable UUID sampleTypeCategoryApiKey) {
		Optional<SampleTypeCategory> stc = sampleTypeCategoryService.getSampleTypeCategoryByApiKey(sampleTypeCategoryApiKey);
		if(stc.isPresent()) {
			return stc.get();
		}else {
			throw new NotFoundException("Sample type category does not exists");
		}
	}
	
	/**
	 * Delete a sample type category from the database
	 * @param sampleTypeCategory
	 */
	@RequestMapping(value="/api/samplecategory/{sampleTypeCategoryId}",method= RequestMethod.DELETE)
	public void deleteSampleTypeCategory(@PathVariable Long sampleTypeCategoryId) {
		sampleTypeCategoryService.deleteSampleTypeCategory(sampleTypeCategoryId);
		
	}
	
	/**
	 * Add a sample type into a sample type category
	 * @param sampleTypeCategoryId the category id
	 * @param sampleType the sample type to insert into
	 * @return
	 */
	@RequestMapping(value="/api/samplecategory/add/{sampleTypeCategoryId}",method= RequestMethod.POST)
	public void addSampleTypeToSampleTypeCategory(@PathVariable Long sampleTypeCategoryId,@RequestBody SampleType sampleType) {
		sampleTypeCategoryService.addSampleTypeToSampleTypeCategory(sampleTypeCategoryId, sampleType);
	}
	
	/**
	 * Remove a sample type from a sampletype category
	 * @param sampleTypeCategoryId the category id
	 * @param sampleType the sample type to remove from the sample type category
	 */
	@RequestMapping(value="/api/samplecategory/remove/{sampleTypeCategoryId}",method= RequestMethod.DELETE)
	public void removeSampleTypeFromSampleTypeCategory(@PathVariable Long sampleTypeCategoryId, @RequestBody SampleType sampleType) {
		sampleTypeCategoryService.deleteSampleTypeFromSampleTypeCategory(sampleTypeCategoryId, sampleType);
		
	}
	
	/**
	 * Return the values of the complexity enumeration
	 * @return
	 */
	@RequestMapping(value="/api/samplecategory/complexities",method= RequestMethod.GET)
	public SampleTypeComplexity[] getSampleTypeComplexitiess() {
		return SampleTypeComplexity.values();
	}
	
	/**
	 * Get the sampletype categories by complexity
	 * @param complexity
	 * @return
	 */
	@RequestMapping(value="/api/samplecategory/complexities/{complexity}",method= RequestMethod.GET)
	public List<SampleTypeCategory> getSampleTypeComplexity(@PathVariable SampleTypeComplexity complexity) {
		return sampleTypeCategoryService.findByComplexity(complexity);		
	}
	
	@RequestMapping(value="/api/samplecategory/generate",method= RequestMethod.GET)
	public void generate() {
		sampleTypeCategoryService.generate();		
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
	@ExceptionHandler(NotFoundException.class)
	void handleNotFound(HttpServletResponse response, Exception e) throws IOException{
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

}
