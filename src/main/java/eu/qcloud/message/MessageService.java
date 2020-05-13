package eu.qcloud.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.websocket.WebSocketService;

/**
 * Main service for node
 *
 * @author Daniel Mancera <daniel.mancera@crg.eu>
 */

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	private WebSocketService webSocketService;

	public List<Message> getAllMessages() {
		List<Message> messages = new ArrayList<>();
		messageRepository.findAll().forEach(messages::add);
		return messages;
	}

	public Message getLastMessage() {
		return messageRepository.findFirstByOrderByIdDesc().get();
	}

	public Message saveMessage(Message msg) {
		webSocketService.sendMessageToAllUsers(msg);
		return messageRepository.save(msg);
	}

	public boolean showNotification() {
		Optional <Message> msg = messageRepository.findFirstByOrderByIdDesc();
		if (!msg.isPresent()) {
			return false;
		}
		if (!msg.get().getShow()) {
			return false;
		}
		Date now = new Date();
		Long hoursDiff = TimeUnit.MILLISECONDS.toHours(now.getTime() - msg.get().getCreationDate().getTime());
		if (hoursDiff < 3) {
			return true;
		} else {
			return false;
		}
	}

}
