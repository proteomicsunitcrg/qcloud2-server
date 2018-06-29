package eu.qcloud.CV;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlled vocabulary controller
 * @author dmancera
 *
 */
@RestController
public class CVController {
	
	@Autowired
	private CVService cvService;
	
	/**
	 * Add a CV to the database by category id
	 * @param cv
	 * @param categoryId
	 * @return the inserted CV
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/cv/category/{categoryId}", method = RequestMethod.POST)
	public CV addCV(@RequestBody CV cv, @PathVariable Long categoryId) {
		return cvService.addCV(cv, categoryId);
	}
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value="/api/cv", method = RequestMethod.GET)
	public List<CV> getAllCV() {
		return cvService.getAllCV();
		
	}
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value="/api/cv/category/{categoryApiKey}", method = RequestMethod.GET)
	public List<CV> getAllCVByCategory(@PathVariable UUID categoryApiKey) {		
		return cvService.getAllCVbyCategory(categoryApiKey);
	}
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value="/api/cv/category/{categoryApiKey}/enabled", method = RequestMethod.GET)
	public List<CV> getAllEnabledCV(@PathVariable UUID categoryApiKey) {
		return cvService.getAllEnabledCVByCategory(categoryApiKey);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/cv/{cvId}", method = RequestMethod.GET)
	public CV getCvByCvId(@PathVariable String cvId) {
		return cvService.getCvByCVId(cvId);
	}
	
	@RequestMapping(value="/api/cv/{cvId}", method = RequestMethod.PUT)
	public CV changeEnabled(@PathVariable String cvId) {
		CV cv = cvService.getCvByCVId(cvId);		
		return cvService.changeEnabled(cv);
	}
	
	/*
	 * Exception handlers
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNotFound(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}
}
