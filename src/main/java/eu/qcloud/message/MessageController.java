
package eu.qcloud.message;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.helper.PasswordGenerator;
import eu.qcloud.mail.EmailService;
import eu.qcloud.mail.Mail;
import eu.qcloud.security.model.Authority;
import eu.qcloud.security.model.AuthorityName;
import eu.qcloud.security.model.User;
import eu.qcloud.security.model.UserPasswordChange;
import eu.qcloud.security.repository.UserRepository.UserWithUuid;
import eu.qcloud.security.service.UserService;
import freemarker.template.TemplateException;
/**
 * NodeController
 * Main controller for node related operations
 * @author Daniel Mancera <daniel.mancera@crg.eu> 
 */
@RestController
public class MessageController {

	@Autowired
	MessageService messageService;

	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/message", method = RequestMethod.POST)
	public List<Message>getAllMessages(){
		return messageService.getAllMessages();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/message/save", method = RequestMethod.POST)
	public Message saveMessage(@RequestBody Message msg){
		return messageService.saveMessage(msg);
	}

}
