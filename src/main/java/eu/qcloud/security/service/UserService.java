package eu.qcloud.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.security.model.Authority;
import eu.qcloud.security.model.AuthorityName;
import eu.qcloud.security.model.User;
import eu.qcloud.security.repository.UserRepository;
import eu.qcloud.security.repository.UserRepository.UserWithUuid;
import eu.qcloud.utils.RandomString;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Bean
	public PasswordEncoder passwordEncoderNodeController() {
		return new BCryptPasswordEncoder();
	}

	private final Log logger = LogFactory.getLog(this.getClass());

	public User saveUser(User u) {
		return userRepository.save(u);
	}

	public User getUserByUuid(UUID apiKey) {
		return userRepository.findOneByApiKey(apiKey);
	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public List<UserWithUuid> getUsersByNodeId(Long nodeId) {
		List<UserWithUuid> users = new ArrayList<>();
		userRepository.findAllByNodeIdAndEnabledTrue(nodeId).forEach(users::add);
		return users;
	}

	public boolean deleteUser(UUID userUuid, User manager) {
		// Check if suicidal
		User u = userRepository.findOneByApiKeyAndNodeId(userUuid, manager.getNode().getId());
		try {
			u.setEnabled(false);
			userRepository.save(u);
			logger.info("Node: " + manager.getNode().getName() + " " + manager.getUsername() + " deleted user: "
					+ u.getUsername());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<User> findUsersByNodeApiKey(UUID nodeApiKey) {
		System.out.println(nodeApiKey);
		List<User> cc = userRepository.findAllByNodeApiKey(nodeApiKey);
		System.out.println(cc);
		return cc;
	}

	public List<User> findAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	public List<UserWithUuid> findAllNodeUsers(Long nodeId) {
		List<UserWithUuid> users = new ArrayList<>();
		userRepository.findAllByNodeIdAndEnabledTrue(nodeId).forEach(users::add);
		return users;
	}

	/**
	 * Update a member role, if USER add MANAGER, if MANAGER removes MANAGER
	 * 
	 * @param userUuid the member apikey to update
	 * @return the updated member
	 */
	public UserWithUuid changeMemberRole(UUID apiKey, Long nodeId) {
		// Get user
		User u = userRepository.findOneByApiKeyAndNodeId(apiKey, nodeId);

		// Check if manager
		boolean isManager = false;
		for (Authority authority : u.getAuthorities()) {
			if (authority.getName().equals(AuthorityName.ROLE_MANAGER)) {
				isManager = true;
			}
		}
		// Modify roles
		List<Authority> newAuthorities = new ArrayList<>();
		if (isManager) {
			Authority userRole = new Authority();
			userRole.setId(1L);
			userRole.setName(AuthorityName.ROLE_USER);
			newAuthorities.add(userRole);
		} else {
			Authority userRole = new Authority();
			userRole.setId(1L);
			userRole.setName(AuthorityName.ROLE_USER);
			newAuthorities.add(userRole);
			Authority managerRole = new Authority();
			managerRole.setId(2L);
			managerRole.setName(AuthorityName.ROLE_MANAGER);
			newAuthorities.add(managerRole);
		}
		u.setAuthorities(newAuthorities);
		userRepository.save(u);

		return userRepository.findWithUuidById(u.getId());
	}

	public String resetTelegramCode(User user) {
		RandomString gen = new RandomString(8, ThreadLocalRandom.current());
		user.setTelegramChatId(null);
		user.setTelegram_code(gen.nextString());
		userRepository.save(user);
		return user.getTelegram_code();
	}

	public String generateString(int size) {
		RandomString gen = new RandomString(size, ThreadLocalRandom.current());
		return gen.nextString();
	}

	public User enableDisableUser(UUID apiKey) {
		User u = userRepository.findOneByApiKey(apiKey);
		u.setEnabled(!u.getEnabled());
		userRepository.save(u);
		return u;
	}

	public String hardResetPassword(UUID apiKey) {
		User u = userRepository.findOneByApiKey(apiKey);
		String newPassword = generateRandomString(15);
		u.setPassword(passwordEncoderNodeController().encode(newPassword));
		userRepository.save(u);
		System.out.println(newPassword);
		System.out.println(passwordEncoderNodeController().encode(newPassword));
		return newPassword;
	}

	private String generateRandomString(int n) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	public User giveRemoveAdmin(UUID apiKey, User userLogged) {
		boolean isAdmin = false;
		List<Authority> newAuthorities = new ArrayList<>();
		User u = userRepository.findOneByApiKey(apiKey);
		if (!u.getApiKey().equals(userLogged.getApiKey())) {
			for (Authority authority : u.getAuthorities()) {
				if (authority.getName().equals(AuthorityName.ROLE_ADMIN)) {
					isAdmin = true;
				}
			}
			if (isAdmin) { // if is ADMIN we have to downgrade his level to a simple mortal
				Authority userRole = new Authority();
				userRole.setId(1L);
				userRole.setName(AuthorityName.ROLE_USER);
				newAuthorities.add(userRole);
			} else { // I've created a GOD
				Authority userRole = new Authority();
				userRole.setId(1L);
				userRole.setName(AuthorityName.ROLE_USER);
				newAuthorities.add(userRole);
				Authority managerRole = new Authority();
				managerRole.setId(2L);
				managerRole.setName(AuthorityName.ROLE_MANAGER);
				newAuthorities.add(managerRole);
				Authority adminRole = new Authority();
				adminRole.setId(3L);
				adminRole.setName(AuthorityName.ROLE_ADMIN);
				newAuthorities.add(adminRole);
			}
			u.setAuthorities(newAuthorities);
			userRepository.save(u);
			return u;
		} else {
			throw new InvalidActionException(
					"You can't modify yourself");
		}

	}

}
