package eu.qcloud.passwordReset;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.mail.EmailService;
import eu.qcloud.mail.Mail;
import eu.qcloud.security.model.User;
import eu.qcloud.security.repository.UserRepository;
import freemarker.template.TemplateException;

@Service
public class PasswordResetService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordResetRepository passwordResetRepository;

	@Autowired
	private EmailService emailService;

	@Value("${qcloud.admin-email}")
	private String adminMail;

	@Value("${qcloud.app-url}")
	private String appUrl;

	@Bean
	public PasswordEncoder passwordEncoderReset() {
		return new BCryptPasswordEncoder();
	}

	public void resetPassword(User u) {
		User user = userRepository.findByUsername(u.getUsername());
		if (user == null) {
			throw new DataRetrievalFailureException("Email not found");
		}

		Optional<PasswordReset> passwordReset = passwordResetRepository.findOptionalByUserId(user.getId());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		Date date = calendar.getTime();
		if (passwordReset.isPresent()) {
			if (passwordReset.get().getNumberOfRequests() > 10) {
				throw new InvalidActionException("Too many password reset requests. "
						+ "If you have problems in order to reset your " + "password please check your spam inbox and "
						+ "if you do not find a password reset email please contact " + "us at qcloud@crg.eu. Thanks.");
			} else {
				passwordReset.get().setNumberOfRequests(passwordReset.get().getNumberOfRequests() + 1);
				passwordResetRepository.save(passwordReset.get());
				passwordReset.get().setExpirationDate(date);
				passwordResetRepository.save(passwordReset.get());

				// send email
				sendPasswordResetHtmlEmail(passwordReset.get());
			}
		} else {
			PasswordReset token = new PasswordReset();

			token.setUser(user);
			token.setToken(UUID.randomUUID());

			token.setExpirationDate(date);
			token.setNumberOfRequests(1);
			passwordResetRepository.save(token);
			// send email
			sendPasswordResetHtmlEmail(token);
		}
		// token actual: bd688def-2675-4108-9d37-a2716fdc1567

	}

	public void checkToken(UUID token) {
		Optional<PasswordReset> passwordReset = passwordResetRepository.findOptionalByToken(token);
		if (!passwordReset.isPresent()) {
			throw new DataRetrievalFailureException("Invalid token");
		}
		if (!checkTokenDate(passwordReset.get())) {
			throw new InvalidActionException("Token has expired!");
		}
	}

	private boolean checkTokenDate(PasswordReset passwordReset) {
		Calendar cal = Calendar.getInstance();
		if ((passwordReset.getExpirationDate().getTime() - cal.getTime().getTime()) <= 0) {
			return false;
		} else {
			return true;
		}

	}

	private void sendPasswordResetHtmlEmail(PasswordReset token) {
		Mail mail = new Mail();
		mail.setFrom("qcloud@crg.eu");
		mail.setTo(new String[] { token.getUser().getEmail() });
		mail.setSubject("QCloud 2.0 - Password reset");

		Map<String, String> model = new HashMap<>();
		model.put("name", token.getUser().getFirstname() + " " + token.getUser().getLastname());
		model.put("token", token.getToken().toString());
		model.put("appUrl", appUrl);

		ObjectMapper objectMapper = new ObjectMapper();
		String churro = null;
		token.setUser(createUserToSend(token.getUser()));
		try {
			churro = Base64.getEncoder().encodeToString(objectMapper.writeValueAsBytes(token));
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.put("base", churro);

		mail.setModel(model);

		try {
			emailService.sendPasswordResetHtmlMessage(mail);
		} catch (MessagingException | IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveNewPassword(UUID token, User u) {
		// perform checks
		Optional<PasswordReset> passwordReset = passwordResetRepository.findOptionalByToken(token);
		if (!passwordReset.isPresent()) {
			throw new DataRetrievalFailureException("Invalid token");
		}
		if (!checkTokenDate(passwordReset.get())) {
			throw new InvalidActionException("Token has expired!");
		}
		if (!u.getApiKey().equals(passwordReset.get().getUser().getApiKey())) {
			throw new InvalidActionException("This token is not valid for the current user!");
		}
		passwordReset.get().getUser().setPassword(passwordEncoderReset().encode(u.getPassword()));
		userRepository.save(passwordReset.get().getUser());
		passwordResetRepository.delete(passwordReset.get());
	}

	private User createUserToSend(User user) {
		User u = new User();
		u.setApiKey(user.getApiKey());
		return u;
	}

}
