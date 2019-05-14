
package eu.qcloud.message;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * MessageController
 * Main controller for message related operations
 * @author Marc Serret <marc.serret@crg.eu> 
 */
@RestController
public class MessageController {

	@Autowired
	MessageService messageService;
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/message", method = RequestMethod.POST)
	public List<Message>getAllMessages(){
		return messageService.getAllMessages();
	}

	@RequestMapping(value = "/api/message/last", method = RequestMethod.POST)
	public Message getLastMessage(){
		return messageService.getLastMessage();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/message/save", method = RequestMethod.POST)
	public Message saveMessage(@RequestBody Message msg){
		return messageService.saveMessage(msg);
	}

}
