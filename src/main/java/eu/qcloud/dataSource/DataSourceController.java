package eu.qcloud.dataSource;

import java.io.IOException;
import java.util.List;
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

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;
/**
 * Data source controller.
 * @author dmancera
 *
 */
@RestController
@PreAuthorize("hasRole('MANAGER')")
public class DataSourceController {

	@Autowired
	private DataSourceService dataSourceService;
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/datasource/node/{nodeId}",method= RequestMethod.GET)
	public List<DataSource> getDataSourcesByNodeId(@PathVariable Long nodeId) {
		return dataSourceService.getAllDataSourceByNodeId(nodeId);
	}
	@RequestMapping(value="/api/datasource",method= RequestMethod.GET)
	public List<DataSource> getAllNodeDataSources() {
		User u = getManagerFromSecurityContext();
		return dataSourceService.getAllDataSourceByNodeId(u.getNode().getId());
	}
	
	@RequestMapping(value="/api/datasource/category/{categoryId}",method= RequestMethod.GET)
	public List<DataSource> getDataSourcesByNode(@PathVariable Long categoryId) {
		User u = getManagerFromSecurityContext();
		return dataSourceService.getAllDataSourceByNodeIdAndCategoryId(u.getNode().getId(),categoryId);
	}
	@RequestMapping(value="/api/datasource/{apiKey}",method= RequestMethod.GET)
	public DataSource getDataSourceByApikey(@PathVariable UUID apiKey) {
		return dataSourceService.findByApiKey(apiKey);
	}
	
	/**
	 * Add a new data source. It will generate an UUID
	 * @param dataSource
	 * @return a list with the node current datasources
	 */
	@RequestMapping(value="/api/datasource",method= RequestMethod.POST)
	public List<DataSource> addDataSource(@RequestBody DataSource dataSource) {
		// Get the current node
		User u = getManagerFromSecurityContext();
		dataSource.setNode(u.getNode());
		UUID dataSourceUuid = UUID.randomUUID();
		dataSource.setApiKey(dataSourceUuid);
		return dataSourceService.addNewDataSource(dataSource);
	}
	/**
	 * Delete a data source from the server. It will perform some
	 * checks before delete.
	 * @param apiKey
	 * @return
	 */
	@RequestMapping(value="/api/datasource/{apiKey}",method= RequestMethod.DELETE)
	public List<DataSource> deleteDataSource(@PathVariable UUID apiKey) {
		// Get the current node
		User u = getManagerFromSecurityContext();
		// get the requested to delete datasource
		DataSource dataSource = dataSourceService.findByApiKey(apiKey);
		if(dataSource== null) {
			throw new PersistenceException("What is going on...");
		}
		Long idToDelete = dataSource.getId();
		Long categoryId = dataSource.getCv().getCategory().getId();
		// Check if node has this instrument
		if(dataSourceService.checkIfNodeHasDataSource(idToDelete,u.getNode().getId())) {
			dataSourceService.deleteDataSource(dataSource);
			System.out.println(idToDelete + " " + categoryId + " " + apiKey);
			if(dataSourceService.findById(dataSource.getId()) != null) {
				// not deleted throw error
				throw new PersistenceException("Instrument not delete due a server error.");
			}
		} else {
			//not node owner, throw error
			throw new InvalidActionException("You do not own this instrument.");
		}
		return dataSourceService.getAllDataSourceByNodeIdAndCategoryId(u.getNode().getId(), categoryId);
	}
	@RequestMapping(value="/api/datasource",method= RequestMethod.PUT)
	public DataSource updateDataSource(@RequestBody DataSource dataSource) {
		// Get the current node
		User u = getManagerFromSecurityContext();
		// Check if node has this instrument
		if(dataSourceService.checkIfNodeHasDataSource(dataSource.getId(),u.getNode().getId())) {
			DataSource ds = dataSourceService.findByApiKey(dataSource.getApiKey());
			if(ds == null) {
				throw new InvalidActionException("Instrument not found.");
			}			
			ds.setName(dataSource.getName());
			ds.setGuideSet(dataSource.getGuideSet());
			return dataSourceService.updateDataSource(ds);
		}else {
			throw new InvalidActionException("You do not own this instrument.");
		}
	}
	@RequestMapping(value="/api/datasource/guideset/{apiKey}",method= RequestMethod.POST)
	public DataSource addGuideSetToDataSource(@PathVariable UUID apiKey, @RequestBody GuideSet guideSet) {
		User u = getManagerFromSecurityContext();
		DataSource dataSource = dataSourceService.findByApiKey(apiKey);
		if(dataSourceService.checkIfNodeHasDataSource(dataSource.getId(), u.getNode().getId())) {
			guideSet.setDataSource(dataSource);
			
			dataSource.setGuideSet(dataSourceService.saveGuideSet(guideSet));
			return dataSourceService.updateDataSource(dataSource);
		}
		return null;
	}
	
	
	
	/*
	 * Helper classes
	 */
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
