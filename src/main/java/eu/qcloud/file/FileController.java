package eu.qcloud.file;

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

import eu.qcloud.file.FileRepository.OnlyChecksum;
import eu.qcloud.file.FileRepository.OnlySmalls;
/**
 * File controller
 * @author dmancera
 *
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value="/api/file", method = RequestMethod.POST)
	public File addFile(@RequestBody File file) {
		return fileService.addNewFile(file);
	}
	@RequestMapping(value="/api/file", method = RequestMethod.GET)
	public List<File> getAllFiles() {
		return fileService.getAllFiles();
	}
	@RequestMapping(value="/api/file/{fileId}", method = RequestMethod.GET)
	public OnlySmalls getFileById(@PathVariable Long fileId) {
		return fileService.getFileById(fileId);
	}
		
	/**
	 * Add a new file into the system
	 * @param file
	 * @param sampleTypeQCCV
	 * @param labSystemApiKey
	 * @return
	 */
	@RequestMapping(value="/api/file/{sampleTypeQCCV}/{labSystemApiKey}", method = RequestMethod.POST)
	public File addFileSpecial(@RequestBody File file,@PathVariable String sampleTypeQCCV,@PathVariable UUID labSystemApiKey) {
		return fileService.addFromWorkflow(file, sampleTypeQCCV, labSystemApiKey);
	}

	/**
	 * Get the last file of a given lab system and sample type
	 * @param sampleTypeQCCV
	 * @param labSystemApikey
	 * @return
	 */
	@RequestMapping(value="/api/file/{sampleTypeQCCV}/{labSystemApikey}", method = RequestMethod.GET)
	public File getLastFileBySampleTypeQCCVAndLabSystemApikey(@PathVariable String sampleTypeQCCV,@PathVariable UUID labSystemApikey) {
		return fileService.getLastFileBySampleTypeQCCVAndLabSystemApikey(sampleTypeQCCV, labSystemApikey);
	}
	
	
	/**
	 * Add a file into the system. This API end point should be consumed
	 * by the pipeline
	 * @param sampleTypeQCCV the QC CV of the file sample type
	 * @param file the file parameters
	 * @return the inserted file
	 */
	@RequestMapping(value="/api/file/add/{sampleTypeQCCV}", method = RequestMethod.POST)
	public File addNewFile(@PathVariable String sampleTypeQCCV, @RequestBody File file) {
		return fileService.addFile(sampleTypeQCCV, file);
	}
	
	/**
	 * Delete a file from the system
	 * @param checksum
	 */
	@RequestMapping(value="/api/file/{checksum}", method = RequestMethod.DELETE)
	public void deleteNewFile(@PathVariable String checksum) {
		fileService.deleteFile(checksum);
	}
	/**
	 * Delete this when all the migration is completed
	 * @param filename
	 * @return
	 */
	@RequestMapping(value="/api/file/name/{filename}", method = RequestMethod.GET)
	public OnlyChecksum findByFilename(@PathVariable String filename) {
		return fileService.getFileByFilename(filename);
	}
	
	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
}
