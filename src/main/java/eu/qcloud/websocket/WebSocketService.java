package eu.qcloud.websocket;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.node.Node;
import eu.qcloud.nonconformity.thresholdnonconformity.ThresholdNonConformity;
import eu.qcloud.param.Param;
import eu.qcloud.security.model.User;
import eu.qcloud.security.repository.UserRepository;
import eu.qcloud.utils.ThresholdUtils;

@Service
public class WebSocketService {
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private SimpUserRegistry userRegistry;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ThresholdUtils thresholdUtils;
	
	@MessageMapping("/bye")
    public void greetingDos(Principal principal, String message) throws  Exception {
        
        messagingTemplate.convertAndSendToUser("dmance@outlook.es", "/queue/reply", "caca " + message);
    }
	
	public void sendNonConformityToNodeUsers(Node node, List<ThresholdNonConformity> thresholdNonConformities, LabSystem labSystem) {
		// get connected users
		
		for(SimpUser s: userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if(user.getNode().getId() == node.getId()) {
				// send message
				messagingTemplate.convertAndSendToUser(s.getName(),
						"/queue/reply",
						new WebSocketNotification("nc-" + labSystem.getApiKey(), thresholdUtils.createLabSystemStatusByThresholdNonConformity(thresholdNonConformities)));
			}
		}
	}
	
	public void sendDataParameterToNodeUsers(Node node, Param param) {
		for(SimpUser s: userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if(user.getNode().getId() == node.getId()) {
				// send message
				messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply", new WebSocketNotification("param", param));
			}
		}
	}
	
	

}
