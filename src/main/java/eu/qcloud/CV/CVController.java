package eu.qcloud.CV;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.category.Category;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class CVController {
	
	@Autowired
	private CVRepository cvRepository;
	
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.POST)
	public CV addCV(@RequestBody CV cv, @PathVariable Long categoryId) {
		cv.setCategory(new Category(categoryId,""));
		cvRepository.save(cv);
		return cv;
	}
	@RequestMapping(value="/cv", method = RequestMethod.GET)
	public List<CV> getAllCV() {
		return cvRepository.findAll();
		
	}
	
	@RequestMapping(value="/category/{categoryId}", method = RequestMethod.GET)
	public List<CV> getAllCVByCategory(@PathVariable Long categoryId) {
		List<CV> cvs = new ArrayList<>();
		cvRepository.findByCategoryId(categoryId)
		.forEach(cvs::add);
		
		return cvs;
	}
	

}
