package eu.qcloud.file;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.data.Data;
import eu.qcloud.file.FileRepository.OnlyChecksum;
import eu.qcloud.file.FileRepository.OnlySmalls;
import eu.qcloud.helper.DataAndAnnotation;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;

/**
 * File controller
 *
 * @author dmancera
 *
 */
@RestController
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/api/file", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public File addFile(@RequestBody File file) {
		return fileService.addNewFile(file);
	}

	@RequestMapping(value = "/api/file", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public List<File> getAllFiles() {
		return fileService.getAllFiles();
	}

	@RequestMapping(value = "/api/file/{fileId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public OnlySmalls getFileById(@PathVariable Long fileId) {
		return fileService.getFileById(fileId);
	}

	/**
	 * Add a new file into the system THIS IS THE USED IN THE PIPELINE
	 *
	 * @param file
	 * @param sampleTypeQCCV
	 * @param labSystemApiKey
	 * @return
	 */
	@RequestMapping(value = "/api/file/{sampleTypeQCCV}/{labSystemApiKey}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public File addFileSpecial(@RequestBody File file, @PathVariable String sampleTypeQCCV,
			@PathVariable UUID labSystemApiKey) {
		return fileService.addFromWorkflow(file, sampleTypeQCCV, labSystemApiKey, getUserFromSecurityContext());
	}

	/**
	 * Get the last file of a given lab system and sample type
	 *
	 * @param sampleTypeQCCV
	 * @param labSystemApikey
	 * @return
	 */
	@RequestMapping(value = "/api/file/{sampleTypeQCCV}/{labSystemApikey}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public File getLastFileBySampleTypeQCCVAndLabSystemApikey(@PathVariable String sampleTypeQCCV,
			@PathVariable UUID labSystemApikey) {
		return fileService.getLastFileBySampleTypeQCCVAndLabSystemApikey(sampleTypeQCCV, labSystemApikey);
	}

	/**
	 * Add a file into the system. This API end point should be consumed by the
	 * pipeline
	 *
	 * @param sampleTypeQCCV the QC CV of the file sample type
	 * @param file           the file parameters
	 * @return the inserted file
	 */
	@RequestMapping(value = "/api/file/add/{sampleTypeQCCV}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public File addNewFile(@PathVariable String sampleTypeQCCV, @RequestBody File file) {
		return fileService.addFile(sampleTypeQCCV, file);
	}

	/**
	 * Delete a file from the system
	 *
	 * @param checksum
	 */
	@RequestMapping(value = "/api/file/{checksum}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteNewFile(@PathVariable String checksum) {
		fileService.deleteFile(checksum);
	}

	@RequestMapping(value = "/api/file/name/{filename}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public OnlyChecksum findByFilename(@PathVariable String filename) {
		return fileService.getFileByFilename(filename);
	}

	@RequestMapping(value = "/api/file/checksum/{checksum}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public File findByChecksum(@PathVariable String checksum) {
		return fileService.getFileByChecksumWithUserCheck(checksum, getUserFromSecurityContext());
	}

	@RequestMapping(value = "/api/file/checksum/qcrawler/{checksum}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public void checkIfFileExistsByChecksum(@PathVariable String checksum) {
		fileService.checkIfFileExistsByChecksum(checksum);
	}

	@RequestMapping(value = "/api/file/checksum/qcrawler/{checksum}", method = RequestMethod.PUT)
	public void updateQCrawlerFile(@PathVariable String checksum, @RequestBody File file) {
		fileService.updateFile(checksum, file);
	}

	@RequestMapping(value = "/api/file/sampletypes/{labSystemApiKey}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public List<SampleType> findSampleTypesUseByLabSystemByLabSystemApiKey(@PathVariable UUID labSystemApiKey) {
		return fileService.findSampleTypesByLabSystemApiKey(labSystemApiKey);
	}

	@GetMapping(value = "/api/file/dashboard")
	@PreAuthorize("hasRole('USER')")
	public Page<File> getFilesByNodePaged(Pageable page, String filename, String labsystemApiKey, String sampleTypeQCCV) {
		return fileService.getFilesByNodePaged(getUserFromSecurityContext().getNode(), page, filename, labsystemApiKey, sampleTypeQCCV);
	}

	@GetMapping(value = "/api/file/fileStatus/{checksum}")
	@PreAuthorize("hasRole('USER')")
	public boolean getFileStatusByChecksum(@PathVariable String checksum) {
		return this.fileService.getFileStatusByChecksum(checksum);
	}

	@GetMapping(value = "/api/file/summary/{checksum}")
	@PreAuthorize("hasRole('USER')")
	public List<Summary> getResumen(@PathVariable String checksum){
		return fileService.getSummary(checksum);
	}

	@GetMapping(value = "/api/file/summaryByDates/{lsApiKey}")
	@PreAuthorize("hasRole('USER')")
	public List<DataAndAnnotation> getResumenByDates(@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) java.util.Date startDate,
	@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) java.util.Date endDate, @PathVariable UUID lsApiKey){
		return fileService.getSummaryByDates(startDate, endDate, lsApiKey);
	}

	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

	@RequestMapping(value = "/api/data/between/{startDate}/{endDate}/{labSystemApiKey}/{sampleTypeName}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MANAGER')")
	public Long getDataBetweenDates(@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) java.util.Date startDate,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) java.util.Date endDate,
			@PathVariable UUID labSystemApiKey, @PathVariable String sampleTypeName) {
		return fileService.getCountDataBetweenDates(labSystemApiKey, sampleTypeName, startDate, endDate);
	}

	private User getUserFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUsername(authentication.getName());
		return user;
	}
}
