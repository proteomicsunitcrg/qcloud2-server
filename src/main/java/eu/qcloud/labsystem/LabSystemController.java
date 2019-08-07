package eu.qcloud.labsystem;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import eu.qcloud.dataSource.DataSource;
import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.guideset.manual.ManualGuideSet;
import eu.qcloud.guideset.manual.ManualGuideSetService;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;

/**
 * Controller for system
 * 
 * @author Daniel Mancera <daniel.mancera@crg.eu>
 *
 */
@RestController
public class LabSystemController {

	@Autowired
	private LabSystemService labSystemService;

	@Autowired
	private UserService userService;

	@Autowired
	private ManualGuideSetService manualGuideSetService;

	/**
	 * Save the given system into the database
	 * 
	 * @param system
	 */
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/api/system", method = RequestMethod.POST)
	public LabSystem saveSystem(@RequestBody LabSystem system) {
		UUID labSystemUuid = UUID.randomUUID();
		system.setApiKey(labSystemUuid);
		return labSystemService.saveSystem(system);
	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/api/system/datasources/{apiKey}", method = RequestMethod.POST)
	public void saveDataSourcesToSystem(@PathVariable UUID apiKey, @RequestBody List<DataSource> dataSources) {
		// get the system
		Optional<LabSystem> s = labSystemService.findSystemByApiKey(apiKey);
		if (s.isPresent()) {
			labSystemService.addDataSourcesToLabSystem(getManagerFromSecurityContext(), s.get(), dataSources);
		} else {
			throw new InvalidActionException("System not found");
		}
	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/api/system/{apiKey}", method = RequestMethod.PUT)
	public void updateLabSystem(@PathVariable UUID apiKey, @RequestBody LabSystem labSystem) {
		Optional<LabSystem> s = labSystemService.findSystemByApiKey(apiKey);
		if (s.isPresent()) {
			LabSystem ss = s.get();
			ss.setName(labSystem.getName());
			labSystemService.saveSystem(s.get());
		}

	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/api/system/guideset/{apikey}", method = RequestMethod.POST)
	public LabSystem addGuideSetToLabSystem(@PathVariable UUID apikey, @RequestBody ManualGuideSet guideSet) {

		Optional<LabSystem> labSystem = labSystemService.findSystemByApiKey(apikey);
		if (labSystem.isPresent()) {
			LabSystem ls = labSystem.get();

			// Check for others guideset and set it to false before add the new one
			manualGuideSetService.setAllLabSystemGuideSetsBySampleTypeDisabled(ls.getGuideSets(), guideSet);
			ls.getGuideSets().add(manualGuideSetService.addNewManualGuideSet(guideSet));
			return labSystemService.saveSystem(ls);
		}
		return null;
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/system/qcrawler", method = RequestMethod.GET)
	public QcrawlerLabSystemList getNodeLabSystemsForQcrawler() {
		User u = getManagerFromSecurityContext();
		return labSystemService.findAllByNodeForQcrawler(u.getNode().getId());
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/system/{apikey}", method = RequestMethod.GET)
	public LabSystem getSystemById(@PathVariable UUID apikey) {
		Optional<LabSystem> system = labSystemService.findSystemByApiKey(apikey);
		if (system.isPresent()) {
			return system.get();
		} else {
			throw new InvalidActionException("System not found");
		}
	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "api/system/enableDisable", method = RequestMethod.POST)
	public boolean requestMethodName(@RequestBody LabSystem ls) {
		return labSystemService.enableDisable(ls);
	}

	/**
	 * Returns all the systems in the database of the current node
	 * 
	 * @return a list of systems
	 */
	@RequestMapping(value = "/api/system", method = RequestMethod.GET)
	public List<LabSystem> findAllNodeSystems() {
		User u = getManagerFromSecurityContext();
		return labSystemService.findAllByNode(u.getNode().getId());
	}

	/**
	 * Get the current user from the security context
	 * 
	 * @return the logged user
	 */
	private User getManagerFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User manager = userService.getUserByUsername(authentication.getName());
		return manager;
	}

	/*
	 * Error handlers
	 */
	@ExceptionHandler(InvalidActionException.class)
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

	@ExceptionHandler(PersistenceException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
	}

}
