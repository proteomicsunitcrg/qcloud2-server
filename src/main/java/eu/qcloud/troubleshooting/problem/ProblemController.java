package eu.qcloud.troubleshooting.problem;

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
@RequestMapping(value = "/api/troubleshooting/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public List<Problem> getAllProblems() {
		return problemService.getAllProblems();
	}
	
	@RequestMapping(value = "/{apiKey}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public Problem getByApiKey(@PathVariable UUID apiKey) {
		return problemService.getByApiKey(apiKey);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public Problem saveAction(@RequestBody Problem action) {
		return problemService.saveProblem(action);
	}

	@RequestMapping(value = "/{actionApiKey}", method = RequestMethod.PATCH)
	@PreAuthorize("hasRole('ADMIN')")
	public Problem updateAction(@RequestBody Problem actionNew, @PathVariable UUID actionApiKey) {
		return problemService.updateProblem(actionNew, actionApiKey);
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
