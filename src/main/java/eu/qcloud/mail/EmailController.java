
package eu.qcloud.mail;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.qcloud.mail.Mail;

/**
 * EmailController
 * Main controller for email related operations
 * @author Marc Serret <marc.serret@crg.eu> 
 */
@RestController
public class EmailController {

	@Autowired
	EmailService emailService;
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/email/send", method = RequestMethod.POST)
	public boolean getAllMessages(@RequestBody Mail email){
        System.out.println(email.toString());
        return emailService.sendManualEmail(email);
	}

}
