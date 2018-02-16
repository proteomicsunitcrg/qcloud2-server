package eu.qcloud.CV;

import java.io.IOException;
import java.util.List;

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

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class CVController {
	
	@Autowired
	private CVService cvService;
	
	@RequestMapping(value = "/api/cv/category/{categoryId}", method = RequestMethod.POST)
	public CV addCV(@RequestBody CV cv, @PathVariable Long categoryId) {
		return cvService.addCV(cv, categoryId);
	}
	@RequestMapping(value="/api/cv", method = RequestMethod.GET)
	public List<CV> getAllCV() {
		return cvService.getAllCV();
		
	}
	
	@RequestMapping(value="/api/cv/category/{categoryId}", method = RequestMethod.GET)
	public List<CV> getAllCVByCategory(@PathVariable Long categoryId) {
		return cvService.getAllCVbyCategory(categoryId);
	}
	
	@RequestMapping(value="/api/cv/{cvId}", method = RequestMethod.PUT)
	public CV changeEnabled(@PathVariable Long cvId) {
		CV cv = cvService.getCVbyId(cvId);
		if(cv == null) {
			throw new DataIntegrityViolationException("Controlled vocabulary id not found.");
		}
		return cvService.changeEnabled(cv);
	}
	
	/*
	 * Exception handlers
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
}
