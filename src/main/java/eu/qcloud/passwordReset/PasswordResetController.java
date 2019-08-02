package eu.qcloud.passwordReset;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.security.model.User;

@RestController
public class PasswordResetController {

	@Autowired
	private PasswordResetService passwordResetService;

	@RequestMapping(value = "/api/passwordreset", method = RequestMethod.POST)
	public void requestPasswordReset(@RequestBody User u) {
		passwordResetService.resetPassword(u);
	}

	@RequestMapping(value = "/api/passwordreset/check/{token}", method = RequestMethod.GET)
	public void checkPasswordResetToken(@PathVariable UUID token) {
		passwordResetService.checkToken(token);
	}

	@RequestMapping(value = "/api/passwordreset/reset/{token}", method = RequestMethod.PUT)
	public void saveNewPassword(@PathVariable UUID token, @RequestBody User u) {
		passwordResetService.saveNewPassword(token, u);
	}

	/*
	 * Helper classes
	 */

	@ExceptionHandler(IllegalArgumentException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
	}

	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleConflict(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	@ExceptionHandler(InvalidActionException.class)
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

}
