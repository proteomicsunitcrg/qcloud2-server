package eu.qcloud.view;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
import eu.qcloud.view.UserViewRepository.UserDisplayWithOutViewDisplay;
import eu.qcloud.view.ViewDisplayRepository.WithOutViewDisplay;
import eu.qcloud.view.ViewRepository.UserViewWithoutUser;

/**
 * View controller. It handles the views and the displays. There are 2 types of
 * displays, default displays limited to a CV and user views with any available
 * chart to the node.
 *
 * @author dmancera
 *
 */
@RestController
public class ViewController {

	@Autowired
	private ViewService viewService;

	@RequestMapping(value = "/api/views", method = RequestMethod.GET)
	public List<View> getAllViews() {
		return viewService.getAllViews();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/views/default", method = RequestMethod.POST)
	public View addDefaultView(@RequestBody View view) {
		view.setDefault(true);
		try {
			return viewService.addView(view);
		} catch (Exception e) {
			throw new DataIntegrityViolationException(e.getMessage());
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/views/default", method = RequestMethod.PUT)
	public View updateDefaultView(@RequestBody View view) {
		return viewService.updateView(view);
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/views/user", method = RequestMethod.PUT)
	public View updateUserView(@RequestBody View view) {
		return viewService.updateView(view);
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/views/user", method = RequestMethod.POST)
	public View addUserView(@RequestBody View view) {
		view.setDefault(false);
		return viewService.addUserView(view);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/views/default/layout", method = RequestMethod.POST)
	public void addViewDisplayToView(@RequestBody List<DefaultView> layout) {
		viewService.addDefaultViewDisplay(layout);
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/views/user/layout", method = RequestMethod.POST)
	public void addUserViewDisplayToView(@RequestBody List<UserView> layout) {
		viewService.addUserViewDisplay(layout);
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/views/user/{viewApiKey}", method = RequestMethod.GET)
	public UserViewWithoutUser findViewByApiKey(@PathVariable UUID viewApiKey) {
		return viewService.findUserViewByApiKey(viewApiKey);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/views/default/layout/{viewApiKey}", method = RequestMethod.PUT)
	public void updateViewDisplayToView(@RequestBody List<DefaultView> layout, @PathVariable UUID viewApiKey) {
		// delete previous
		if (viewService.deleteLayoutByViewApiKey(viewApiKey)) {
			viewService.addDefaultViewDisplay(layout);
		} else {
			throw new PersistenceException("An error has occurred while updating your view. Try again later");
		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/views/user/layout/{viewApiKey}", method = RequestMethod.PUT)
	public void updateUserViewDisplayToView(@RequestBody List<UserView> layout, @PathVariable UUID viewApiKey) {
		// delete previous
		if (viewService.deleteLayoutByViewApiKey(viewApiKey)) {
			viewService.addUserViewDisplay(layout);
		} else {
			throw new PersistenceException("An error has occurred while updating your view. Try again later");
		}

	}

	@RequestMapping(value = "/api/views/default", method = RequestMethod.GET)
	public List<View> findAllDefaultViews() {
		return viewService.findAllDefaultViews();
	}

	/**
	 * Get all views by user. The user is retrieved via the security context
	 *
	 * @return a list of views
	 */
	@RequestMapping(value = "/api/views/user", method = RequestMethod.GET)
	public List<UserViewWithoutUser> findAllUserViews() {
		return viewService.findAllUserViews();
	}

	/**
	 * This method returns the views by CV
	 *
	 * @param cvId
	 * @return
	 */
	@RequestMapping(value = "/api/views/default/{cvId}", method = RequestMethod.GET)
	public List<View> getViewByCVId(@PathVariable String cvId) {
		return viewService.getDefaultViewsByCV(cvId);
	}

	/**
	 * This method returns the views by CV but first checks if the ls has qc3
	 *
	 * @param cvId
	 * @return
	 */
	@RequestMapping(value = "/api/views/default/check/{cvId}/{lsApiKey}", method = RequestMethod.GET)
	public List<View> getViewByCVIdAndLsApiKey(@PathVariable String cvId, @PathVariable UUID lsApiKey) {
		return viewService.getDefaultViewsByCVAndLsApiKey(cvId, lsApiKey);
	}

	/**
	 * Returns a view by cv and sample type category
	 *
	 * @param cvId
	 * @param sampleTypeCategoryId
	 * @return
	 */
	@RequestMapping(value = "/api/views/default/{cvId}/{sampleTypeCategoryApiKey}", method = RequestMethod.GET)
	public View getViewByCVIdAndSampleTypeCategoryId(@PathVariable Long cvId,
			@PathVariable UUID sampleTypeCategoryApiKey) {
		View v = viewService.getDefaultViewByCVIdAndSampleTypeCategoryApiKey(cvId, sampleTypeCategoryApiKey);
		return v;
	}

	@RequestMapping(value = "/api/views/default/view/{viewApiKey}", method = RequestMethod.GET)
	public List<WithOutViewDisplay> getDefaultViewDisplayByViewApiKey(@PathVariable UUID viewApiKey) {
		return viewService.getDefaultViewDisplayByViewApiKey(viewApiKey);
	}

	@RequestMapping(value = "/api/views/user/view/{viewApiKey}", method = RequestMethod.GET)
	public List<UserDisplayWithOutViewDisplay> getUserViewDisplayByViewApiKey(@PathVariable UUID viewApiKey) {
		return viewService.getUserViewDisplayByViewApiKey(viewApiKey);
	}

	@RequestMapping(value = "/api/views", method = RequestMethod.DELETE)
	public View deleteUserView(@RequestBody View view) {
		return viewService.deleteView(view);
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
	void handleBadAction(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
}
