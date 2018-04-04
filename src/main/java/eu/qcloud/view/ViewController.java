package eu.qcloud.view;

import java.io.IOException;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.exceptions.InvalidActionException;
/**
 * View controller.
 * It handles the views and the displays.
 * There are 2 types of displays, default displays
 * limited to a CV and user views with any available
 * chart to the node.
 * @author dmancera
 *
 */
@RestController
public class ViewController {
	
	@Autowired
	private ViewService viewService;
	
	@RequestMapping(value="/api/views", method=RequestMethod.GET)
	public List<View> getAllViews() {
		return viewService.getAllViews(); 
	}
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/views/default", method=RequestMethod.POST)
	public View addDefaultView(@RequestBody View view) {
		view.setDefault(true);
		try {
			return viewService.addView(view);
		}catch(Exception e) {
			throw new DataIntegrityViolationException("There is a problem with the server");
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/views/default/layout", method=RequestMethod.POST)
	public List<ViewDisplay> addViewDisplayToView(@RequestBody List<DefaultView> layout) {
		return viewService.addDefaultViewDisplay(layout);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/views/default/layout/{viewId}", method=RequestMethod.PUT)
	public List<ViewDisplay> updateViewDisplayToView(@RequestBody List<DefaultView> layout,@PathVariable Long viewId) {
		// delete previous
		if(viewService.deleteLayoutByViewId(viewId)) {
			return viewService.addDefaultViewDisplay(layout);	
		}else {
			throw new PersistenceException("An error has occurred while updating your view. Try again later");
		}
		
	}
	@RequestMapping(value="/api/views/default", method=RequestMethod.GET)
	public List<View> findAllDefaultViews() {
		return viewService.findAllDefaultViews();
	}
	
	/**
	 * This method returns the view object by CV
	 * @param cvId
	 * @return
	 */	
	@RequestMapping(value="/api/views/default/{cvId}", method=RequestMethod.GET)
	public View getViewByCVId(@PathVariable Long cvId) {
		View v =viewService.getDefaultViewByCV(cvId); 
		return v;
	}
	
	@RequestMapping(value="/api/views/default/view/{viewId}", method=RequestMethod.GET)
	public List<DefaultView> getDefaultViewDisplayByViewId(@PathVariable Long viewId) {
		return viewService.getDefaultViewDisplayByViewId(viewId);
	}
	
	/*
	 * Exception handlers
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

	@ExceptionHandler(PersistenceException.class)
	void handleNonConnection(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
	}
	@ExceptionHandler(InvalidActionException.class)
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException{
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
}
