package eu.qcloud.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.security.model.Authority;
import eu.qcloud.security.model.AuthorityName;
import eu.qcloud.security.model.User;
import eu.qcloud.security.repository.UserRepository;
import eu.qcloud.security.repository.UserRepository.UserWithUuid;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

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

}
