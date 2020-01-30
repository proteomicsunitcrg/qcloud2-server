
package eu.qcloud.node;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.mail.EmailService;
import eu.qcloud.mail.Mail;
import eu.qcloud.security.model.Authority;
import eu.qcloud.security.model.AuthorityName;
import eu.qcloud.security.model.User;
import eu.qcloud.security.model.UserPasswordChange;
import eu.qcloud.security.repository.UserRepository.UserWithUuid;
import eu.qcloud.security.service.UserService;
import freemarker.template.TemplateException;

/**
 * NodeController Main controller for node related operations
 * 
 * @author Daniel Mancera <daniel.mancera@crg.eu>
 */
@RestController
public class NodeController {

	@Autowired
	NodeService nodeService;

	@Autowired
	UserService userService;

	@Autowired
	private EmailService emailService;

	private final Log logger = LogFactory.getLog(this.getClass());

	@Bean
	public PasswordEncoder passwordEncoderNodeController() {
		return new BCryptPasswordEncoder();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/node/allNoFiles")
	public List<Node> getAllNoFiles() {
		return nodeService.getAllNodesNoFiles();
	}

	/**
	 * Add a new node with a manager user to the database
	 * 
	 * @param n a JSON formed node with a user in the users array
	 * @return <Node> the inserted node
	 */
	@RequestMapping(value = "/api/node", method = RequestMethod.POST)
	public Node insertNewNode(@RequestBody Node n) {
		UUID nodeUuid = UUID.randomUUID();
		UUID userUuid = UUID.randomUUID();

		n.getUsers().get(0).setLastPasswordResetDate(new Date());
		n.getUsers().get(0).setEnabled(true);
		n.getUsers().get(0).setPassword(passwordEncoderNodeController().encode(n.getUsers().get(0).getPassword()));
		n.getUsers().get(0).setTelegram_code(userService.generateString(8));

		n.setApiKey(nodeUuid);
		n.getUsers().get(0).setApiKey(userUuid);

		Authority manager = new Authority();
		manager.setId(2L);
		manager.setName(AuthorityName.ROLE_MANAGER);
		Authority userRole = new Authority();
		userRole.setId(1L);
		userRole.setName(AuthorityName.ROLE_USER);
		n.getUsers().get(0).setAuthorities(Arrays.asList(userRole, manager));
		n.getUsers().get(0).setNode(n);
		Node insertedNode = null;
		try {
			insertedNode = nodeService.createNode(n);
		} catch (DataIntegrityViolationException e) {
			return null;

		} catch (PersistenceException ee) {

			throw new PersistenceException("No connection to the database");
		} catch (CannotCreateTransactionException eee) {
			throw new PersistenceException("No connection to the database");
		}
		logger.info("Node created: " + n.getName() + " " + n.getUsers().get(0).getEmail());
		sendNewNodeHtmlEmail(n.getUsers().get(0));
		return insertedNode;
	}

	/**
	 * Add a new user to the current node
	 * 
	 * @param newUser the user to add
	 * @return A list of the current node users
	 */
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/api/node/newmember", method = RequestMethod.POST)
	public List<UserWithUuid> addNewUserToNode(@RequestBody User newUser) {
		// get the current node from the user
		User manager = getManagerFromSecurityContext();
		UUID userUuid = UUID.randomUUID();
		newUser.setNode(manager.getNode());
		String passwordNormal = getEmailUsername(newUser.getEmail()) + "." + LocalDate.now().getYear();
		newUser.setPassword(passwordEncoderNodeController().encode(passwordNormal));
		Authority userRole = new Authority();
		userRole.setId(1L);
		userRole.setName(AuthorityName.ROLE_USER);
		newUser.setAuthorities(Arrays.asList(userRole));
		newUser.setLastPasswordResetDate(new Date());
		newUser.setEnabled(true);
		newUser.setApiKey(userUuid);
		newUser.setTelegram_code(userService.generateString(8));
		try {
			userService.saveUser(newUser);
			// send email to new user
			sendNewUserHtmlEmail(newUser, passwordNormal);
			logger.info("Node: " + manager.getNode().getName() + " added user: " + newUser.getUsername());

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Email already in use");
		} catch (ConstraintViolationException ee) {
			throw new DataIntegrityViolationException("User information do not meet the requeriments");
		}

		return userService.getUsersByNodeId(manager.getNode().getId());
	}

	/**
	 * Add a new manager to the current node
	 * 
	 * @param newUser the user to add
	 * @return A list of the current node users
	 */
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/node/newmanager", method = RequestMethod.POST)
	public List<UserWithUuid> addNewManagerToNode(@RequestBody User newUser) {
		// get the current node from the user
		User manager = getManagerFromSecurityContext();
		UUID userUuid = UUID.randomUUID();
		newUser.setNode(manager.getNode());

		newUser.setPassword(passwordEncoderNodeController().encode(newUser.getPassword()));

		Authority userRole = new Authority();
		userRole.setId(1L);
		userRole.setName(AuthorityName.ROLE_USER);

		Authority managerRole = new Authority();
		managerRole.setId(2L);
		managerRole.setName(AuthorityName.ROLE_MANAGER);

		newUser.setAuthorities(Arrays.asList(userRole, managerRole));
		newUser.setLastPasswordResetDate(new Date());
		newUser.setEnabled(true);
		newUser.setApiKey(userUuid);
		try {
			userService.saveUser(newUser);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Username already in use");
		}

		return userService.getUsersByNodeId(manager.getNode().getId());
	}

	/**
	 * Delete a node member from the database
	 * 
	 * @param userUuid the user apikey
	 * @return the list of node members
	 */
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/api/node/user/{userUuid}", method = RequestMethod.DELETE)
	public List<UserWithUuid> deleteLabMember(@PathVariable UUID userUuid) {
		User manager = getManagerFromSecurityContext();
		if (manager.getApiKey().equals(userUuid)) {
			throw new InvalidActionException("You can not remove yourself. Ask another lab manager to do it.");
		}
		if (userService.deleteUser(userUuid, manager)) {
			// return list

			return userService.getUsersByNodeId(manager.getNode().getId());
		} else {
			// throw exception
			throw new DataIntegrityViolationException("Can not delete user");
		}

	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/api/node/user/change/{userUuid}", method = RequestMethod.PUT)
	public UserWithUuid changeMemberRole(@PathVariable UUID userUuid) {
		User manager = getManagerFromSecurityContext();
		if (manager.getApiKey().equals(userUuid)) {
			throw new InvalidActionException("You can not change your role. Ask another lab manager to do it.");
		}

		return userService.changeMemberRole(userUuid, manager.getNode().getId());
	}

	@RequestMapping(value = "/api/node/telegram", method = RequestMethod.GET)
	public String getTelegramURL() {
		User user = getManagerFromSecurityContext();
		return user.getTelegram_code();
	}

	@RequestMapping(value = "api/node/telegram", method = RequestMethod.PUT)
	public String resetTelegramCode() {
		User user = getManagerFromSecurityContext();
		return userService.resetTelegramCode(user);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/node/usersByNodeApiKey", method = RequestMethod.GET)
	public List <User> getUsersByNodeApiKey(@RequestParam UUID apiKey) {
		System.out.println(apiKey);
		List <User> pedete = userService.findUsersByNodeApiKey(apiKey);
		return pedete;
	}


	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/api/node/users", method = RequestMethod.GET)
	public List<UserWithUuid> getUsers() {
		User manager = getManagerFromSecurityContext();
		return userService.findAllNodeUsers(manager.getNode().getId());
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/node/user/password", method = RequestMethod.PUT)
	public void updateUserPassword(@RequestBody UserPasswordChange userPassword) {
		// check current password
		User user = getManagerFromSecurityContext();
		if (passwordEncoderNodeController().matches(userPassword.getCurrentPassword(), user.getPassword())) {
			// password correct, store
			user.setPassword(passwordEncoderNodeController().encode(userPassword.getNewPassword()));
			userService.saveUser(user);
		} else {
			// throw exception
			throw new InvalidActionException("Incorrect current password.");
		}
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/node/user/subscribed", method = RequestMethod.GET)
	public boolean isUserSubscribed() {
		User user = getManagerFromSecurityContext();
		return user.isSpam();
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/api/node/user/updateSubscribed", method = RequestMethod.PATCH)
	public User updateSubscribed() {
		User user = getManagerFromSecurityContext();
		return nodeService.updateSubscribed(user);
	}

	/*
	 * Development functions
	 */

	private void sendNewNodeHtmlEmail(User user) {
		Mail mail = new Mail();
		mail.setFrom("qcloud@crg.eu");
		mail.setTo(new String[] { user.getEmail() });
		mail.setSubject("Welcome to QCloud 2.0");

		Map<String, String> model = new HashMap<>();
		mail.setModel(model);

		try {
			emailService.sendWelcomeNodeHtmlMessage(mail);
		} catch (MessagingException | IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendNewUserHtmlEmail(User user, String password) {
		Mail mail = new Mail();
		mail.setFrom("qcloud@crg.eu");
		mail.setTo(new String[] { user.getEmail() });
		mail.setSubject("Welcome to QCloud 2.0");

		Map<String, String> model = new HashMap<>();
		model.put("name", user.getFirstname() + " " + user.getLastname());
		model.put("password", password);
		mail.setModel(model);

		try {
			emailService.sendWelcomeHtmlMessage(mail);
		} catch (MessagingException | IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/node/info/{nodeUuidString}", method = RequestMethod.GET)
	public Node getNodeByUuid(@PathVariable UUID nodeUuidString) {

		return nodeService.getNodeByNodeUuid(nodeUuidString);
	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/api/nodes", method = RequestMethod.GET)
	public List<NodeWithTotalFiles> getAllNodes() {
		return nodeService.getAllNodes();
	}

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/node/user/{userUuidString}", method = RequestMethod.GET)
	public User getUserByUuid(@PathVariable UUID userUuidString) {
		return userService.getUserByUuid(userUuidString);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/node/enableDisableUser", method = RequestMethod.PATCH)
	public User enableDisable(@RequestParam UUID apiKey) {
		return userService.enableDisableUser(apiKey);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/node/hardResetPassword", method = RequestMethod.PATCH)
	public String hardResetPassword(@RequestParam UUID apiKey) {
		// String passwordNormal = getEmailUsername(newUser.getEmail()) + "." + LocalDate.now().getYear();
		// newUser.setPassword(passwordEncoderNodeController().encode(passwordNormal));
		return userService.hardResetPassword(apiKey);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/node/user/giveRemoveAdmin", method = RequestMethod.PATCH)
	public User giveRemoveAdmin(@RequestParam UUID apiKey) {
		// return getManagerFromSecurityContext();
		return userService.giveRemoveAdmin(apiKey, getManagerFromSecurityContext());
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

	private String getEmailUsername(String someEmail) {
		return someEmail.substring(0, someEmail.indexOf("@"));
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
