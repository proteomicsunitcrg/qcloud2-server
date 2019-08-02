package eu.qcloud.contextSource.instrumentSample;

import java.io.IOException;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.exceptions.InvalidActionException;

/**
 * Instrument sample controller
 * 
 * @author dmancera
 *
 */
@RestController
public class InstrumentSampleController {
	@Autowired
	private InstrumentSampleService instrumentSampleService;

	@RequestMapping(value = "/api/contextsource/instrumentsample", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public InstrumentSample addElement(@RequestBody InstrumentSample element) {
		return instrumentSampleService.addElement(element);
	}

	@RequestMapping(value = "/api/contextsource/instrumentsample", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public InstrumentSample updateInstrumentSample(@RequestBody InstrumentSample instrumentSample) {
		return instrumentSampleService.updateInstrumentSample(instrumentSample);
	}

	@RequestMapping(value = "/api/contextsource/instrumentsample", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public List<InstrumentSample> getAllInstrumentSamples() {
		return instrumentSampleService.getAllInstrumentSample();
	}

	/*
	 * Exception handlers
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

	@ExceptionHandler(PersistenceException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
	}

	@ExceptionHandler(InvalidActionException.class)
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
}
