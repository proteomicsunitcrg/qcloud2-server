package eu.qcloud.websocket;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import eu.qcloud.data.PlotTrace;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.message.Message;
import eu.qcloud.node.Node;
import eu.qcloud.param.Param;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.security.model.User;
import eu.qcloud.security.repository.UserRepository;
import eu.qcloud.thresholdnonconformity.ThresholdNonConformity;
import eu.qcloud.troubleshooting.annotation.Annotation;
import eu.qcloud.utils.ThresholdUtils;
import eu.qcloud.utils.factory.ThresholdForPlotImpl;

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

	public void sendNonConformityToNodeUsers(Node node, List<ThresholdNonConformity> thresholdNonConformities,
			LabSystem labSystem) {
		// get connected users

		for (SimpUser s : userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if (user.getNode().getId() == node.getId()) {
				// send message
				messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply", new WebSocketNotification(
						"nc-" + labSystem.getApiKey(), null, null,
						thresholdUtils.createLabSystemStatusByThresholdNonConformity(thresholdNonConformities)));
			}
		}
	}

	public void sendAnnotationToNodeUsers(Node node, Annotation annotation) {
		for (SimpUser s : userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if (user.getNode().getId() == node.getId()) {
				// send message
				messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply", new WebSocketNotification(
						"annotation-" + annotation.getLabSystem().getApiKey(), null, null, annotation));
			}
		}
	}

	public void sendDeleteAnnotationToNodeUsers(Node node, UUID annotationApiKey) {
		for (SimpUser s : userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if (user.getNode().getId() == node.getId()) {
				// send message
				messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply",
						new WebSocketNotification("deleteannotation-", null, null, annotationApiKey));
			}
		}
	}

	public void sendEnableDisableLS(Node node, LabSystem ls) {
		for (SimpUser s : userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if (user.getNode().getId() == node.getId()) {
				messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply",
						new WebSocketNotification("enableDisableLS-", null, null, ls));
			}
		}
	}

	public void sendUpdateAnnotationToNodeUsers(Node node, Annotation annotation) {
		for (SimpUser s : userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if (user.getNode().getId() == node.getId()) {
				// send message
				messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply", new WebSocketNotification(
						"updateannotation-" + annotation.getLabSystem().getApiKey(), null, null, annotation));
			}
		}
	}

	public void sendTracePointDataToNodeUsers(Node node, Param param, List<PlotTrace> dataForPlot, LabSystem labSystem,
			SampleType sampleType) {
		for (SimpUser s : userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if (user.getNode().getId() == node.getId()) {
				// send message
				messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply",
						new WebSocketNotification("data-" + param.getqCCV(), labSystem.getApiKey(),
								sampleType.getQualityControlControlledVocabulary(), dataForPlot));
			}
		}
	}

	public void sendThresholdToNodeUsers(Node node, Param param, ThresholdForPlotImpl threshold, LabSystem labSystem,
			SampleType sampleType) {
		for (SimpUser s : userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if (user.getNode().getId() == node.getId()) {
				// send message
				messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply",
						new WebSocketNotification("threshold-" + param.getqCCV(), labSystem.getApiKey(),
								sampleType.getQualityControlControlledVocabulary(), threshold));
			}
		}
	}

	public void sendNewLabSystemToNodeUsers(Node node, LabSystem labSystem) {
		for (SimpUser s : userRegistry.getUsers()) {
			User user = userRepository.findByUsername(s.getName());
			if (user.getNode().getId() == node.getId()) {
				// send message
				messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply",
						new WebSocketNotification("labsystem-" + labSystem.getMainDataSource().getCv().getCVId(),
								labSystem.getApiKey(), null, labSystem));
			}
		}
	}

	public void sendMessageToAllUsers(Message message) {
		for (SimpUser s : userRegistry.getUsers()) {
			messagingTemplate.convertAndSendToUser(s.getName(), "/queue/reply",
					new WebSocketNotification("message-", null, null, message));
		}
	}

}
