package eu.qcloud.sampleType;

import java.io.IOException;
import java.util.List;

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

import eu.qcloud.sampleType.SampleTypeRepository.SampleTypeOnlyName;
import eu.qcloud.sampleType.SampleTypeRepository.WithPeptide;
import eu.qcloud.sampleTypeCategory.SampleTypeComplexity;
/**
 * Sample type service
 * @author dmancera
 *
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class SampleTypeController {
	@Autowired
	private SampleTypeService sampleTypeService;
	
	/**
	 * Add a new sample type to the database
	 * @param sampleType the sample type to insert
	 * @param sampleTypeCategoryId the id of the category
	 * @return the inserted sample type
	 */
	@RequestMapping(value="/api/sample/{sampleTypeCategoryId}",method= RequestMethod.POST)
	public SampleType addSampleType(@RequestBody SampleType sampleType,@PathVariable Long sampleTypeCategoryId) {
		return sampleTypeService.addSampleType(sampleType,sampleTypeCategoryId);
	}
	
	/**
	 * Return all sample types
	 * @return a list with all sample types 
	 */
	@RequestMapping(value="/api/sample", method=RequestMethod.GET)
	public List<SampleTypeOnlyName> getAllSampleType() {
		return sampleTypeService.getAllSampleType();
	}
	
	/**
	 * Return a list of all sample type that are not in a 
	 * HIGHWITHISOTOPOLOGUE complexity
	 * @return a list of sample types
	 */
	@RequestMapping(value="/api/sample/noiso", method=RequestMethod.GET)
	public List<SampleTypeOnlyName> getAllSampleTypesNoIsotopolgues() {
		return sampleTypeService.getAllSampleTypeNoIsotopologues();		
	}
	
	/**
	 * Return a list of all sample type that are 
	 * HIGHWITHISOTOPOLOGUE complexity
	 * @return a list of sample types
	 */
	@RequestMapping(value="/api/sample/yesiso", method=RequestMethod.GET)
	public List<SampleTypeOnlyName> getAllSampleTypesIsotopolgues() {
		return sampleTypeService.getAllSampleTypeIsotopologues();		
	}
	
	/**
	 * Get a list of all sample types with a list of 
	 * the sample type peptides
	 * @return a list of the sample types with a list its peptides
	 */
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
	@RequestMapping(value="/api/sample/makemain/{sampleTypeCategoryId}/{sampleTypeId}", method=RequestMethod.PUT)
	public void doMainSampleType(@PathVariable Long sampleTypeCategoryId,@PathVariable Long sampleTypeId) {
		sampleTypeService.makeMainSampleType(sampleTypeCategoryId,sampleTypeId);
	}
	
	/**
	 * Get all sample types by its complexity
	 * @param complexity the complexity to look into
	 * @return a list with the requested sample types
	 */
	@RequestMapping(value="/api/sample/type/{complexity}", method=RequestMethod.GET)
	public List<SampleTypeOnlyName> findAllByCategoryComplexity(@PathVariable(value="complexity") SampleTypeComplexity complexity) {
		return sampleTypeService.findByCategorySampleTypeComplexity(complexity);
	}
	
	/**
	 * Get the default sample type by sample type category
	 * @param sampleTypeCategoryId
	 * @return
	 */
	@RequestMapping(value="/api/sample/main/{sampleTypeCategoryId}", method=RequestMethod.GET)
	public SampleType getDefaultSampleTypeBySampleTypeCategoryId(@PathVariable Long sampleTypeCategoryId) {
		return sampleTypeService.getMainSampleTypeBySampleTypeCategory(sampleTypeCategoryId);
	}
	
	
	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}
	
}
