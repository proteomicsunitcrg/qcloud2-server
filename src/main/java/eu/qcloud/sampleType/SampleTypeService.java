package eu.qcloud.sampleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.sampleType.SampleTypeRepository.SampleTypeOnlyName;
import eu.qcloud.sampleType.SampleTypeRepository.WithPeptide;
import eu.qcloud.sampleTypeCategory.SampleTypeCategory;
import eu.qcloud.sampleTypeCategory.SampleTypeCategoryRepository;
import eu.qcloud.sampleTypeCategory.SampleTypeComplexity;

@Service
@Transactional
public class SampleTypeService {
	@Autowired
	private SampleTypeRepository sampleTypeRepository;

	@Autowired
	private SampleTypeCategoryRepository sampleTypeCategoryRepository;

	/**
	 * Add a new sample type to the database
	 * 
	 * @param s                    the sample type to add
	 * @param sampleTypeCategoryId the category id of the sample type
	 * @return inserted sample type
	 */
	public SampleType addSampleType(SampleType s, UUID sampleTypeCategoryApiKey) {
		Optional<SampleTypeCategory> stc = sampleTypeCategoryRepository.findByApiKey(sampleTypeCategoryApiKey);
		if (stc.isPresent()) {
			s.setSampleTypeCategory(stc.get());
			return sampleTypeRepository.save(s);
		} else {
			throw new DataIntegrityViolationException("Category does not exists");
		}
	}

	/**
	 * Return all sample types in the database
	 * 
	 * @return a list with all the sample types in the database
	 */
	public List<SampleTypeOnlyName> getAllSampleType() {
		List<SampleTypeOnlyName> samples = new ArrayList<>();
		sampleTypeRepository.findAllSampleTypes().forEach(samples::add);
		return samples;
	}

	/**
	 * Find a sample type by id. Use this method for use inside the spring
	 * application.
	 * 
	 * @param id the id of the sample type
	 * @return the requested sample type
	 */
	public Optional<SampleType> getSampleTypeById(Long id) {
		return sampleTypeRepository.findById(id);
	}

	/**
	 * Find a sample type by id, use this method for return data to the client.
	 * 
	 * @param id
	 * @return the requested sample type
	 */
	public SampleTypeOnlyName getSampleById(Long id) {
		return sampleTypeRepository.findOnlyNameById(id);
	}

	/**
	 * Get a list of all sample types with its peptides
	 * 
	 * @return a list of sample types with peptides
	 */
	public List<WithPeptide> getAllSampleTypeWithPeptide() {
		return sampleTypeRepository.findAllSampleType();
	}

	/**
	 * Return the main sample type of a given category
	 * 
	 * @param sampleTypeCategoryId the category to look into for the requested main
	 *                             sample type
	 * @return
	 */
	public SampleType getMainSampleTypeBySampleTypeCategory(Long sampleTypeCategoryId) {
		return sampleTypeRepository.findByIsMainSampleTypeTrueAndSampleTypeCategoryId(sampleTypeCategoryId);
	}

	public SampleType getMainSampleTypeBySampleTypeCategoryApiKey(UUID sampleTypeCategoryApiKey) {
		return sampleTypeRepository.findByIsMainSampleTypeTrueAndSampleTypeCategoryApiKey(sampleTypeCategoryApiKey);
	}

	/**
	 * This function makes a sample type the main of its category while makes the
	 * other sample types of the category non-main
	 * 
	 * @param sampleTypeCategoryId
	 * @param sampleTypeId
	 */
	public void makeMainSampleType(UUID sampleTypeCategoryApiKey, String sampleTypeQCCV) {
		// check if this sample type belongs to the sample type category
		Optional<SampleType> st = sampleTypeRepository
				.findByQualityControlControlledVocabularyAndSampleTypeCategoryApiKey(sampleTypeQCCV,
						sampleTypeCategoryApiKey);
		if (!st.isPresent()) {
			throw new InvalidActionException("This sample type does not belong to this sample type category");
		}
		sampleTypeRepository.findBySampleTypeCategoryApiKey(sampleTypeCategoryApiKey).forEach(s -> {
			if (!s.getQualityControlControlledVocabulary().equals(sampleTypeQCCV)) {
				s.setMainSampleType(false);
			} else {
				s.setMainSampleType(true);
			}
			sampleTypeRepository.save(s);
		});
	}

	/**
	 * Return a list of the sample types by sample type category complexity
	 * 
	 * @param complexity a value of the SampleTypeComplexity enumeration
	 * @return a list with the requested sample types
	 */
	public List<SampleTypeOnlyName> findByCategorySampleTypeComplexity(SampleTypeComplexity complexity) {
		return sampleTypeRepository.findBySampleTypeCategorySampleTypeComplexity(complexity);
	}

	/**
	 * Return all sample types that are not HIGHWITHISOTOPOLOGUES complexity
	 * 
	 * @return
	 */
	public List<SampleTypeOnlyName> getAllSampleTypeNoIsotopologues() {
		return sampleTypeRepository
				.findBySampleTypeCategorySampleTypeComplexityNot(SampleTypeComplexity.HIGHWITHISOTOPOLOGUES);
	}

	public List<SampleTypeOnlyName> getAllSampleTypeIsotopologues() {
		return sampleTypeRepository
				.findBySampleTypeCategorySampleTypeComplexity(SampleTypeComplexity.HIGHWITHISOTOPOLOGUES);
	}

	public SampleType getSampleTypeByQCCV(String qCCV) {
		Optional<SampleType> st = sampleTypeRepository.findByQualityControlControlledVocabulary(qCCV);
		if (st.isPresent()) {
			return st.get();
		} else {
			throw new DataRetrievalFailureException("No sample type found with this sample type " + qCCV);
		}
	}

}
