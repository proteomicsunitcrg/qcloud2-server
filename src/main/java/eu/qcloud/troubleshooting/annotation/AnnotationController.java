package eu.qcloud.troubleshooting.annotation;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.troubleshooting.annotation.AnnotationRepository.AnnotationForPlot;

@RestController
public class AnnotationController {
	
	@Autowired
	private AnnotationService annotationService;
	
	@RequestMapping(value = "/api/troubleshooting/annotation", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public void addAnnotation(@RequestBody Annotation annotation) {
		annotationService.addAnnotation(annotation);
	}
	
	@Deprecated
	@RequestMapping(value = "/api/troubleshooting/annotation", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List<AnnotationForPlot> getAllAnnotations() {
		return annotationService.getAllAnnotations();
		
	}
	
	public void removeAnnotation() {
		
	}
	
	public void updateAnnotation() {
		
	}
	
	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNotFound(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

}
