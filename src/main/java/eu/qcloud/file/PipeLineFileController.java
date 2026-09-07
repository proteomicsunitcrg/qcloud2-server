package eu.qcloud.file;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;

/**
 * Tracks every raw file the pipeline sees (received/processing/processed/error),
 * unlike {@link FileController} which only ever surfaces successfully processed
 * files. Consumed by: trigger.sh (received), report_qcloud.nf (processed) and
 * atlas_checker.sh (error) - all authenticate as the same pipeline ADMIN user
 * already used by /api/file/{sampleTypeQCCV}/{labSystemApiKey}.
 */
@RestController
public class PipeLineFileController {

	@Autowired
	private PipeLineFileService pipeLineFileService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/api/pipelineFile/received/{sampleTypeQCCV}/{labSystemApiKey}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public PipeLineFile markReceived(@RequestBody PipeLineFile file, @PathVariable String sampleTypeQCCV,
			@PathVariable UUID labSystemApiKey) {
		return pipeLineFileService.markReceived(file, sampleTypeQCCV, labSystemApiKey);
	}

	@RequestMapping(value = "/api/pipelineFile/processed/{checksum}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public PipeLineFile markProcessed(@PathVariable String checksum, @RequestParam(required = false) String filename) {
		return pipeLineFileService.markProcessed(checksum, filename);
	}

	@RequestMapping(value = "/api/pipelineFile/error/{checksum}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public PipeLineFile markError(@RequestBody PipeLineFile errorInfo, @PathVariable String checksum) {
		errorInfo.setChecksum(checksum);
		return pipeLineFileService.markError(errorInfo);
	}

	@GetMapping(value = "/api/pipelineFile/dashboard")
	@PreAuthorize("hasRole('USER')")
	public Page<PipeLineFile> getDashboard(Pageable page, String filename) {
		return pipeLineFileService.getDashboard(getUserFromSecurityContext().getNode(), page, filename);
	}

	@GetMapping(value = "/api/pipelineFile/checksum/{checksum}")
	@PreAuthorize("hasRole('USER')")
	public PipeLineFile getByChecksum(@PathVariable String checksum) {
		return pipeLineFileService.getByChecksum(checksum);
	}

	private User getUserFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userService.getUserByUsername(authentication.getName());
	}

}
