package epntech.cbdmq.pe.servicio;

import static epntech.cbdmq.pe.constante.EmailConst.DEFAULT_PORT;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SMTP_SERVER;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_AUTH;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_HOST;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_PORT;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_STARTTLS_ENABLE;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_STARTTLS_REQUIRED;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


import jakarta.mail.MessagingException;
import jakarta.mail.Session;

@Service
public class EmailService {

	@Value("${pecb.email.username}")
	private String USERNAME;

	@Value("${pecb.email.password}")
	private String PASSWORD;
	
	private String FROM_EMAIL = USERNAME;

	public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException {
		/*
		 * Message message = createEmail(firstName, password, email); SMTPTransport
		 * smtpTransport = (SMTPTransport)
		 * getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
		 * smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
		 * smtpTransport.sendMessage(message, message.getAllRecipients());
		 * smtpTransport.close();
		 */

		JavaMailSender emailSender = this.getJavaMailSender();
		SimpleMailMessage message = this.createEmail(firstName, password, email);

		emailSender.send(message);

	}

	private SimpleMailMessage /* Message */ createEmail(String firstName, String password, String email)
			throws MessagingException {
		/*
		 * Message message = new MimeMessage(getEmailSession()); message.setFrom(new
		 * InternetAddress(FROM_EMAIL)); message.setRecipients(TO,
		 * InternetAddress.parse(email, false)); message.setRecipients(CC,
		 * InternetAddress.parse(CC_EMAIL, false)); message.setSubject(EMAIL_SUBJECT);
		 * message.setText( "Hello " + firstName +
		 * ", \n \n Your new account password is: " + password +
		 * "\n \n The Support Team"); message.setSentDate(new Date());
		 * message.saveChanges();
		 */

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(USERNAME);
		message.setTo(email);
		message.setSubject(EMAIL_SUBJECT);
		message.setText("Hola " + firstName + ", \n \n Tu nueva contraseña es: " + password
				+ "\n \n Plataforma educativa - CBDMQ");

		return message;
	}

	private Session getEmailSession() {
		Properties properties = System.getProperties();
		properties.put(PROP_SMTP_HOST, EMAIL_SMTP_SERVER);
		properties.put(PROP_SMTP_AUTH, "true");
		properties.put(PROP_SMTP_PORT, DEFAULT_PORT);
		properties.put(PROP_SMTP_STARTTLS_ENABLE, "true");
		properties.put(PROP_SMTP_STARTTLS_REQUIRED, "true");
		return Session.getInstance(properties, null);
	}

	// Impl con Spring mail
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(EMAIL_SMTP_SERVER);
		mailSender.setPort(DEFAULT_PORT);

		mailSender.setUsername(USERNAME);
		mailSender.setPassword(PASSWORD);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}
