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
		messageRepository.findAllByOrderByIdDesc().forEach(messages::add);
		return messages;
	}

	public Message getLastMessage() {
		Optional<Message> msg = messageRepository.findFirstByOrderByIdDesc();
		if (msg.isPresent()) {
			return msg.get();
		} else {
			return null;
		}
	}

	public Message saveMessage(Message msg) {
		messageRepository.save(msg);
		webSocketService.sendActiveMessagesToAllUsers(getActiveMessages());
		return msg;
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

	public boolean deleteMessage(Long msgId) {
		try {
			messageRepository.deleteById(msgId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<Message> getActiveMessages() {
		return messageRepository.findAllByShowOrderByPriorityAsc(true);
	}

}
