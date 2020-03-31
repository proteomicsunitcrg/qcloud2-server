package eu.qcloud.troubleshooting;

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

import eu.qcloud.exceptions.InvalidActionException;

@RestController
@RequestMapping(value = "/api/troubleshooting")
public class TroubleshootingController {

	@Autowired
	private TroubleshootingService troubleshootingService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public Troubleshooting addTroubleshootingItem(@RequestBody Troubleshooting troubleshooting) {
		return troubleshootingService.addTroubleshootingItem(troubleshooting);
	}

	@RequestMapping(value = "/{apiKey}", method = RequestMethod.PATCH)
	@PreAuthorize("hasRole('ADMIN')")
	public Troubleshooting updateTroubleshooting(@PathVariable UUID apiKey, @RequestBody Troubleshooting troubleshooting) {
		return troubleshootingService.updateTroubleshooting(troubleshooting);
	}

	// @RequestMapping(value = "/enableDisable/{apiKey}", method = RequestMethod.PATCH)
	// @PreAuthorize("hasRole('ADMIN')")
	// public Troubleshooting enableDisable(@PathVariable UUID apiKey) {
	// 	return troubleshootingService.enableDisable(apiKey);
	// }

	@RequestMapping(value = "/unlink/{apiKey}", method = RequestMethod.PATCH)
	@PreAuthorize("hasRole('ADMIN')")
	public Troubleshooting unLink(@PathVariable UUID apiKey) {
		return troubleshootingService.unLink(apiKey);
	}

	@RequestMapping(value = "/topParents", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List <Troubleshooting> getAllTopParents() {
		return troubleshootingService.getAllTopParents();
	}

	@RequestMapping(value = "/parentNullChildsNull/{type}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List <Troubleshooting> getByParentNullChildsNullAndType(@PathVariable TroubleshootingType type) {
		return troubleshootingService.getByParentNullChildsNullAndType(type);
	}

	@RequestMapping(value = "/{apiKey}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public Troubleshooting getTroubleshootingByApiKey(@PathVariable UUID apiKey) {
		return troubleshootingService.getByApiKey(apiKey);
	}

	@RequestMapping(value = "/parentsByType/{type}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List <Troubleshooting> getTroubleshootingParentByType(@PathVariable TroubleshootingType type) {
		return troubleshootingService.getTroubleshootingParentByType(type);
	}

	@RequestMapping(value = "/linkChild/{parentApiKey}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public Troubleshooting linkChild(@PathVariable UUID parentApiKey, @RequestBody Troubleshooting child) {
		return troubleshootingService.linkChild(parentApiKey, child);
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

	@ExceptionHandler(InvalidActionException.class)
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

}
