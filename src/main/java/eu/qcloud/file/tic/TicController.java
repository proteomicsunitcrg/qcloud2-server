package eu.qcloud.file.tic;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicController {

	@Autowired
	private TicService ticService;

	@RequestMapping(value = "/api/file/tic/{checksum}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public void addFileToTic(@PathVariable String checksum, @RequestBody Tic tic) {
		ticService.addFileToTic(checksum, tic);
	}

	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

}
