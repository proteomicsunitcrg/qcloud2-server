package eu.qcloud.CV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import eu.qcloud.category.Category;

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
	
	public CV getCVbyId(Long id) {
		return cvRepository.findOne(id);
	}
	
	public List<CV> getAllCVbyCategory(Long categoryId) {
		List<CV> cvs = new ArrayList<>();
		cvRepository.findByCategoryId(categoryId)
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

	public List<CV> getAllEnabledCVByCategory(Long categoryId) {
		return cvRepository.findByCategoryIdAndEnabled(categoryId,true);		
	}
	

	
	
}
