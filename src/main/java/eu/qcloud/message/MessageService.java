package eu.qcloud.message;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * Try to insert a new node in the database
	 * 
	 * @author Daniel Mancera
	 * @param n the node to insert
	 * @return <Node> inserted node or <null> if error
	 */
	// public Node createNode(Node n) {

	// return nodeRepository.save(n);
	// }

	/**
	 * Delete a node
	 * 
	 * @param the node to delete
	 * @return true or false
	 */
	// public boolean deleteNode(Node n) {
	// nodeRepository.delete(n);
	// if(nodeRepository.findOneById(n.getId()) == null) {
	// return true;
	// }else {
	// return false;
	// }

	// }

	// public Node getNodeByNodeUuid(UUID nodeUuid) {
	// return nodeRepository.findOneByApiKey(nodeUuid);
	// }

	// public Node getNodeByNodeId(Long nodeId) {
	// return nodeRepository.findOneById(nodeId);
	// }

	public List<Message> getAllMessages() {
		List<Message> messages = new ArrayList<>();
		messageRepository.findAll().forEach(messages::add);
		return messages;
	}

	public Message getLastMessage() {
		return messageRepository.findFirstByOrderByIdDesc();
	}

	public Message saveMessage(Message msg) {
		webSocketService.sendMessageToAllUsers(msg);
		return messageRepository.save(msg);
	}

}
