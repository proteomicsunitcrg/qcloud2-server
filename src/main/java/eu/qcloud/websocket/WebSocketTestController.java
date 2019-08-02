package eu.qcloud.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.security.repository.UserRepository;

@RestController
public class WebSocketTestController {

	@Autowired
	private SimpUserRegistry userRegistry;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/api/websocket/users", method = RequestMethod.GET)
	public void getActiveConnections() {

		for (SimpUser s : userRegistry.getUsers()) {
			getUserFromUsername(s.getName());
		}
	}

	private void getUserFromUsername(String username) {
		System.out.println(userRepository.findByUsername(username).getNode().getName());

	}

}
