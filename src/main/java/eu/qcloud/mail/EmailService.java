package eu.qcloud.mail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import eu.qcloud.utils.FileUtils;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class EmailService {

    private FileUtils fileUtils = new FileUtils();

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public boolean sendManualEmail(Mail mail) {
        try {
            Map<String, String> model = new HashMap<>();
            model.put("mailContent", mail.getContent());
            mail.setModel(model);
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.addAttachment("logo.png", new ClassPathResource("images/logo-qcloud.png"));
            helper.addAttachment("logoCrg.png", new ClassPathResource("images/crgLogo.png"));
            helper.addAttachment("logoUpf.png", new ClassPathResource("images/upfLogo.png"));
            Template t = freemarkerConfig.getTemplate("default-mail.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
            helper.setBcc(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(html, true);
            helper.setFrom("qcloud@crg.eu", "QCloud 2.0");
            helper.setReplyTo("qcloud@crg.eu", "QCloud 2.0 ");
            message.setContentLanguage(new String[] { "en" });
            message.setDescription("Informative email");
            emailSender.send(message);
            return true;

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendWelcomeHtmlMessage(Mail mail) throws MessagingException, IOException, TemplateException {
        System.out.println("ewlcome msg");
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.addAttachment("logo.png", new ClassPathResource("images/logo-qcloud.png"));
        helper.addAttachment("logoCrg.png", new ClassPathResource("images/crgLogo.png"));
        helper.addAttachment("logoUpf.png", new ClassPathResource("images/upfLogo.png"));
        Template t = freemarkerConfig.getTemplate("new-user.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
        helper.setTo(mail.getTo());
        helper.setBcc("roger.olivella@crg.eu");
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        // helper.setFrom(mail.getFrom());
        helper.setFrom(mail.getFrom(), "QCloud 2.0");
        emailSender.send(message);
    }

    public void sendWelcomeNodeHtmlMessage(Mail mail) throws MessagingException, TemplateNotFoundException,
            MalformedTemplateNameException, ParseException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.addAttachment("logo.png", new ClassPathResource("images/logo-qcloud.png"));
        helper.addAttachment("logoCrg.png", new ClassPathResource("images/crgLogo.png"));
        helper.addAttachment("logoUpf.png", new ClassPathResource("images/upfLogo.png"));
        Template t = freemarkerConfig.getTemplate("new-node.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
        helper.setTo(mail.getTo());
        helper.setBcc("roger.olivella@crg.eu");
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setReplyTo("qcloud@crg.eu", "QCloud 2.0 ");
        helper.notify();
        // helper.setFrom(mail.getFrom());
        helper.setFrom(mail.getFrom(), "QCloud 2.0");
        emailSender.send(message);
    }

    public void sendPasswordResetHtmlMessage(Mail mail) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.addAttachment("logo.png", new ClassPathResource("images/logo-qcloud.png"));
        helper.addAttachment("logoCrg.png", new ClassPathResource("images/crgLogo.png"));
        helper.addAttachment("logoUpf.png", new ClassPathResource("images/upfLogo.png"));

        Template t = freemarkerConfig.getTemplate("password-reset-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        // helper.setFrom(mail.getFrom());
        helper.setFrom(mail.getFrom(), "QCloud 2.0");

        emailSender.send(message);
    }

    public List<String> getAllTemplates() {
        try {
            return fileUtils.listDirFiles("templates/htmlMails");

        } catch (IOException e) {
            return null;
        }
    }

    public String getTemplate(String template) {
        try {
            return fileUtils.readFile("templates/htmlMails/" + template, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
