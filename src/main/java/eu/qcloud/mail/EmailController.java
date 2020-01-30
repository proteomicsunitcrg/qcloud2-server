
package eu.qcloud.mail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * EmailController Main controller for email related operations
 * 
 * @author Marc Serret <marc.serret@crg.eu>
 */
@RestController
public class EmailController {

	@Autowired
	EmailService emailService;

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/email/send", method = RequestMethod.POST)
	public boolean sendManualEmail(@RequestBody Mail email) {
		return emailService.sendManualEmail(email);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/email/spamAll", method = RequestMethod.POST)
	public boolean sendSpamAll(@RequestBody Mail email) {
		return emailService.sendSpamAll(email);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/email/spamAllManagers", method = RequestMethod.POST)
	public boolean sendSpamAllManagers(@RequestBody Mail email) {
		return emailService.sendSpamAllManagers(email);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/email/templates", method = RequestMethod.GET)
	public List<String> getAllTemplates() {
		return emailService.getAllTemplates();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/api/email/template/{template}", method = RequestMethod.GET)
	public String getTemplate(@PathVariable String template) {
		return emailService.getTemplate(template);
	}

}
