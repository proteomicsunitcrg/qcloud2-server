
package eu.qcloud.message;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * MessageController Main controller for message related operations
 *
 * @author Marc Serret <marc.serret@crg.eu>
 */
@RestController
public class MessageController {

	@Autowired
	MessageService messageService;

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/message", method = RequestMethod.POST)
	public List<Message> getAllMessages() {
		return messageService.getAllMessages();
	}

	@RequestMapping(value = "/api/message/last", method = RequestMethod.POST)
	public Message getLastMessage() {
		return messageService.getLastMessage();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/message/save", method = RequestMethod.POST)
	public Message saveMessage(@RequestBody Message msg) {
		msg.setCreationDate(new Date());
		return messageService.saveMessage(msg);
	}

	@RequestMapping(value = "/api/message/showNotification", method = RequestMethod.GET)
	public boolean showNotification() {
		return messageService.showNotification();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/api/message/{msgId}")
	public boolean deleteMessage(@PathVariable Long msgId) {
		return messageService.deleteMessage(msgId);
	}

}
