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
/**
 * Controlled vocabulary service
 * @author dmancera
 *
 */
@Service
public class InstrumentService {
	@Autowired
	private InstrumentRepository cvRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Instrument addCV(Instrument cv, Long categoryId) {
		cv.setCategory(new Category(categoryId,""));
		cvRepository.save(cv);
		return cv;
	}
	
	public Instrument addCV(Instrument cv, UUID categoryApiKey) {
		Optional<Category> category = categoryRepository.findOptionalByApiKey(categoryApiKey);
		if(!category.isPresent()) {
			throw new DataRetrievalFailureException("Category not found");
		}
		cv.setCategory(category.get());
		cvRepository.save(cv);
		return cv;
	}
	
	public List<Instrument> getAllCV() {
		return cvRepository.findAll();
	}
	
	public Optional<Instrument> getCVbyId(Long id) {
		return cvRepository.findById(id);
	}
	
	public List<Instrument> getAllCVbyCategory(UUID categoryApiKey) {
		List<Instrument> cvs = new ArrayList<>();
		cvRepository.findByCategoryApiKey(categoryApiKey)
		.forEach(cvs::add);
		return cvs;
	}
	
	public Instrument changeEnabled(Instrument cv) {
		if(cv.isEnabled()) {
			cv.setEnabled(false);
		}else {
			cv.setEnabled(true);
		}
		cvRepository.save(cv);
		return cv;
		
	}

	public List<Instrument> getAllEnabledCV() {
		return cvRepository.findByEnabled(true);
	}

	public List<Instrument> getAllEnabledCVByCategory(UUID categoryId) {
		return cvRepository.findByCategoryApiKeyAndEnabled(categoryId,true);		
	}

	public Instrument getCvByCVId(String cvId) {
		Optional<Instrument> cv = cvRepository.getByCVId(cvId);
		if(!cv.isPresent()) {
			throw new DataRetrievalFailureException("CV not found");
		}
		return cv.get();
	}
	

	
	
}
