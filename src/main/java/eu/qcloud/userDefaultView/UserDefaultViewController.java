package eu.qcloud.userDefaultView;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;
import eu.qcloud.userDefaultView.UserDefaultViewRepository.SmallUserDefaultView;

@RestController
public class UserDefaultViewController {
	
	@Autowired
	private UserDefaultViewService userDefaultViewService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/api/userdefaultview", method = RequestMethod.GET)
	public SmallUserDefaultView findUserDefaultView() {
		return userDefaultViewService.getUserDefaultView(getUserFromSecurityContext());		
	}
	
	@RequestMapping(value="/api/userdefaultview", method = RequestMethod.POST)
	public UserDefaultView saveUserDefaultView(@RequestBody UserDefaultView userDefaultView) {
		return userDefaultViewService.saveUserDefaultView(userDefaultView, getUserFromSecurityContext());		
	}
	
	/**
	 * Get the current user from the security context
	 * 
	 * @return the logged user
	 */
	private User getUserFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User manager = userService.getUserByUsername(authentication.getName());
		return manager;
	}
	
	@ExceptionHandler(DataRetrievalFailureException.class)
	void handleConflict(HttpServletResponse response, Exception e) throws IOException {
	    response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}

}
