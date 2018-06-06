package eu.qcloud.sampleTypeCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;

/**
 * Service for sample type category
 * @author dmancera
 *
 */
@Service
public class SampleTypeCategoryService {
	
	@Autowired
	private SampleTypeCategoryRepository sampleTypeCategoryRepository;
	
	@Autowired
	private SampleTypeRepository sampleTypeRepository;
	
	/**
	 * Add a sample type category to the database
	 * @param sampleTypeCategory
	 * @return the added sample type
	 */
	public void addSampleTypeCategory(SampleTypeCategory sampleTypeCategory) {
		Optional<SampleTypeCategory> stc = sampleTypeCategoryRepository.findByName(sampleTypeCategory.getName());
		if(stc.isPresent()) {
			throw new DataIntegrityViolationException("Category name already exists");
		}
		
		sampleTypeCategoryRepository.save(sampleTypeCategory);		
	}
	/**
	 * Find all sample type categories
	 * @return a list with the sample type catgories
	 */
	public List<SampleTypeCategory> getAll() {
		List<SampleTypeCategory> sampleTypeCategories = new ArrayList<>();
		sampleTypeCategoryRepository.findAll().forEach(sampleTypeCategories::add);
		return sampleTypeCategories;
	}
	/**
	 * Add a sample type to a category
	 * If it fails will throw a 409 error, if not a success 200
	 * @param sampleTypeCategoryId
	 * @param sampleType
	 */
	public void addSampleTypeToSampleTypeCategory(Long sampleTypeCategoryId, SampleType sampleType) {
		Optional<SampleTypeCategory> stc = sampleTypeCategoryRepository.findById(sampleTypeCategoryId);
		Optional<SampleType> st = sampleTypeRepository.findById(sampleType.getId());
		if(stc.isPresent() && st.isPresent()) {
			sampleType.setSampleTypeCategory(stc.get());
			sampleTypeRepository.save(sampleType);
		}else {
			throw new DataIntegrityViolationException("Category or sample type do not exist");
		}
		
	}
	/**
	 * Delete a sample type category
	 * If there is a sample in the category it will fail
	 * @param sampleTypeCategoryId
	 */
	public void deleteSampleTypeCategory(Long sampleTypeCategoryId) {
		sampleTypeCategoryRepository.deleteById(sampleTypeCategoryId);
	}
	
	public void deleteSampleTypeFromSampleTypeCategory(Long sampleTypeCategoryId, SampleType sampleType) {
		Optional<SampleType> st = sampleTypeRepository.findById(sampleType.getId());
		if(st.isPresent()) {
			st.get().setSampleTypeCategory(null);
			sampleTypeRepository.save(st.get());
		}else {
			throw new DataIntegrityViolationException("Wrong values");
		}
	}
	
	public Optional<SampleTypeCategory> getSampleTypeCategoryById(Long sampleTypeCategoryId) {
		return sampleTypeCategoryRepository.findById(sampleTypeCategoryId);
		
	}
	public List<SampleTypeCategory> findByComplexity(SampleTypeComplexity complexity) {
		return sampleTypeCategoryRepository.findBySampleTypeComplexity(complexity);
	}
}
