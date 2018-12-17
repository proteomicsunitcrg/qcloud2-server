package eu.qcloud.troubleshooting.annotation;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;
import eu.qcloud.troubleshooting.annotation.AnnotationRepository.AnnotationForPlot;

@RestController
public class AnnotationController {

	@Autowired
	private AnnotationService annotationService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/api/troubleshooting/annotation", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public void addAnnotation(@RequestBody Annotation annotation) {
		annotationService.addAnnotation(annotation);
	}

	@RequestMapping(value = "/api/troubleshooting/annotation/dates/{startDate}/{endDate}/{labSystemApiKey}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public List<AnnotationForPlot> getAnnotationsByDate(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) Date startDate,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) Date endDate,
			@PathVariable UUID labSystemApiKey) {
		return annotationService.getAnnotationsBetweenDates(labSystemApiKey, startDate, endDate, getUserFromSecurityContext());
	}

	public void removeAnnotation() {

	}

	public void updateAnnotation() {

	}

	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNotFound(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	private User getUserFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUsername(authentication.getName());
		return user;
	}

}
