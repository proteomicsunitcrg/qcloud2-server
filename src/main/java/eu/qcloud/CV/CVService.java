package eu.qcloud.CV;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.category.Category;
/**
 * Controlled vocabulary service
 * @author dmancera
 *
 */
@Service
public class CVService {
	@Autowired
	private CVRepository cvRepository;
	
	public CV addCV(CV cv, Long categoryId) {
		cv.setCategory(new Category(categoryId,""));
		cvRepository.save(cv);
		return cv;
	}
	
	public List<CV> getAllCV() {
		return cvRepository.findAll();
	}
	
	public Optional<CV> getCVbyId(Long id) {
		return cvRepository.findById(id);
	}
	
	public List<CV> getAllCVbyCategory(UUID categoryApiKey) {
		List<CV> cvs = new ArrayList<>();
		cvRepository.findByCategoryApiKey(categoryApiKey)
		.forEach(cvs::add);
		return cvs;
	}
	
	public CV changeEnabled(CV cv) {
		if(cv.isEnabled()) {
			cv.setEnabled(false);
		}else {
			cv.setEnabled(true);
		}
		cvRepository.save(cv);
		return cv;
		
	}

	public List<CV> getAllEnabledCV() {
		return cvRepository.findByEnabled(true);
	}

	public List<CV> getAllEnabledCVByCategory(UUID categoryId) {
		return cvRepository.findByCategoryApiKeyAndEnabled(categoryId,true);		
	}

	public CV getCvByCVId(String cvId) {
		Optional<CV> cv = cvRepository.getByCVId(cvId);
		if(!cv.isPresent()) {
			throw new DataRetrievalFailureException("CV not found");
		}
		return cv.get();
	}
	

	
	
}
