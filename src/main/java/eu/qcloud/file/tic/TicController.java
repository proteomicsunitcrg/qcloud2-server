package eu.qcloud.file.tic;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	/**
	 * Get the stored TIC series of a file.
	 *
	 * The response body is the raw stored string (currently
	 * "gz1:" + base64(gzip(JSON array of [rt_ms, msLevel, tic] points)));
	 * returning the bare string keeps the payload minimal and avoids
	 * serializing the File relations. Missing files/TICs answer a plain
	 * 404 (the ResponseEntity keeps the error off the sendError dispatch,
	 * which misbehaves as 406 on this produces=text/plain mapping).
	 */
	@RequestMapping(value = "/api/file/tic/{checksum}", method = RequestMethod.GET,
			produces = "text/plain;charset=UTF-8")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> getTicByChecksum(@PathVariable String checksum) {
		Tic tic = ticService.findTicByChecksum(checksum);
		if (tic == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TIC: No TIC stored for file");
		}
		return ResponseEntity.ok(tic.getTic());
	}

	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

}
