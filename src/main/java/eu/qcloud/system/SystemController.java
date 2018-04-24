package eu.qcloud.system;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;

/**
 * Controller for system
 * @author Daniel Mancera <daniel.mancera@crg.eu>
 *
 */
@RestController
public class SystemController {
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * Save the given system into the database
	 * @param system
	 */
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value="/api/system",method= RequestMethod.POST)
	public System saveSystem(@RequestBody System system) {
		return systemService.saveSystem(system);
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value="/api/system/datasources/{systemId}",method= RequestMethod.POST)
	public void saveDataSourcesToSystem(@PathVariable Long systemId, @RequestBody List<DataSource> dataSources) {
		// get the system
		Optional<System> s = systemService.findSystemBySystemId(systemId);
		if(s.isPresent()) {
			// add the data sources
			System system = s.get();
			
			system.setDataSources(dataSources);
			// save the system
			systemService.saveSystem(system);
		}else {
			throw new InvalidActionException("System not found");
		}
	}
	
	
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value="/api/system/{systemId}",method= RequestMethod.GET)
	public System getSystemById(@PathVariable Long systemId) {
		Optional<System> system = systemService.findSystemBySystemId(systemId);
		if(system.isPresent()) {
			return system.get();
		}else {
			throw new InvalidActionException("System not found");
		}
	}
	
	
	
	/**
	 * Returns all the systems in the database
	 * of the current node
	 * @return a list of systems
	 */
	@RequestMapping(value="/api/system",method= RequestMethod.GET)
	public List<System> findAllNodeSystems() {		
		User u = getManagerFromSecurityContext();
		return systemService.findAllByNode(u.getNode().getId());
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
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException{
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
	@ExceptionHandler(PersistenceException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
	}
	
	

}
