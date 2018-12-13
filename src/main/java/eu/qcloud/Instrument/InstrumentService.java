package eu.qcloud.Instrument;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.category.Category;
import eu.qcloud.category.CategoryRepository;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;

/**
 * Controlled vocabulary service
 * 
 * @author dmancera
 *
 */
@Service
public class InstrumentService {
	@Autowired
	private InstrumentRepository instrumentRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SampleTypeRepository sampleTypeRepository;

	public Instrument addCV(Instrument cv, Long categoryId) {
		cv.setCategory(new Category(categoryId, ""));
		instrumentRepository.save(cv);
		return cv;
	}

	public Instrument addCV(Instrument cv, UUID categoryApiKey) {
		Optional<Category> category = categoryRepository.findOptionalByApiKey(categoryApiKey);
		if (!category.isPresent()) {
			throw new DataRetrievalFailureException("Category not found");
		}
		cv.setCategory(category.get());
		instrumentRepository.save(cv);
		return cv;
	}

	public List<Instrument> getAllCV() {
		return instrumentRepository.findAll();
	}

	public Optional<Instrument> getCVbyId(Long id) {
		return instrumentRepository.findById(id);
	}

	public List<Instrument> getAllCVbyCategory(UUID categoryApiKey) {
		List<Instrument> cvs = new ArrayList<>();
		instrumentRepository.findByCategoryApiKey(categoryApiKey).forEach(cvs::add);
		return cvs;
	}

	public Instrument changeEnabled(Instrument cv) {
		if (cv.isEnabled()) {
			cv.setEnabled(false);
		} else {
			cv.setEnabled(true);
		}
		instrumentRepository.save(cv);
		return cv;

	}

	public List<Instrument> getAllEnabledCV() {
		return instrumentRepository.findByEnabled(true);
	}

	public List<Instrument> getAllEnabledCVByCategory(UUID categoryId) {
		return instrumentRepository.findByCategoryApiKeyAndEnabled(categoryId, true);
	}

	public Instrument getCvByCVId(String cvId) {
		Optional<Instrument> cv = instrumentRepository.getByCVId(cvId);
		if (!cv.isPresent()) {
			throw new DataRetrievalFailureException("CV not found");
		}
		return cv.get();
	}

	public void addSampleTypeToInstrument(String cvId, SampleType sampleTypeToAdd) {
		// Check for have only one st per st category
		Optional<Instrument> instrument = instrumentRepository.findByQccv(cvId);
		if (!instrument.isPresent()) {
			throw new DataRetrievalFailureException("CV not found");
		}

		Optional<SampleType> sampleType = sampleTypeRepository
				.findByQualityControlControlledVocabulary(sampleTypeToAdd.getQualityControlControlledVocabulary());
		if (!sampleType.isPresent()) {
			throw new DataRetrievalFailureException("Sample type not found");
		}

		// delete previous and add new one
		instrument.get().getSampleTypes().removeIf(s -> {
			return s.getSampleTypeCategory().getId() == sampleType.get().getSampleTypeCategory().getId();
		});
		instrument.get().getSampleTypes().add(sampleType.get());
		instrumentRepository.save(instrument.get());

	}

}
