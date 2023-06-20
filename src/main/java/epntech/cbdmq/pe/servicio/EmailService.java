
package epntech.cbdmq.pe.servicio;

import static epntech.cbdmq.pe.constante.EmailConst.DEFAULT_PORT;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SMTP_SERVER;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT1;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT2;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_AUTH;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_HOST;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_PORT;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_STARTTLS_ENABLE;
import static epntech.cbdmq.pe.constante.EmailConst.PROP_SMTP_STARTTLS_REQUIRED;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

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
        MimeMessage message = this.createEmailHtml(firstName, password, email);

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

    private MimeMessage /* Message */ createEmailHtml(String firstName, String password, String email)
            throws MessagingException {
        System.out.println("hasta aqui llega");
        MimeMessage message = this.getJavaMailSender().createMimeMessage();
        InternetAddress fromAddress = new InternetAddress(USERNAME);
        System.out.println("from: " + fromAddress);
        message.setFrom(fromAddress);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(EMAIL_SUBJECT);
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Nuevas Credenciales</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .logo {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .logo img {\n" +
                "            max-width: 200px;\n" +
                "        }\n" +
                "        .message {\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .message h2 {\n" +
                "            margin-top: 0;\n" +
                "        }\n" +
                "        .message p {\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .cta-button {\n" +
                "            display: inline-block;\n" +
                "            background-color: #007bff;\n" +
                "            color: #ffffff;\n" +
                "            padding: 10px 20px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 3px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"logo\">\n" +
                "            <img src=\"data:image/jpeg;base64,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\" alt=\"Logo\">\n" +
                "        </div>\n" +
                "        <div class=\"message\">\n" +
                "            <h2>Recuperación de contraseña</h2>\n" +
                "            <p>Estimado(a) Jair </p>\n" +
                "            <p>Hemos recibido una solicitud para restablecer la contraseña de tu cuenta. Haz clic en el siguiente botón para continuar:</p>\n" +
                "            <p><a href=\"https://example.com/restablecer-contrasena\" class=\"cta-button\">Restablecer contraseña</a></p>\n" +
                "            <p>Si no has solicitado restablecer la contraseña, puedes ignorar este correo electrónico.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Este correo electrónico fue enviado automáticamente. Por favor, no respondas a este correo.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";


        message.setContent(htmlContent, "text/html; charset=utf-8");
        System.out.println("Mensajeeeeeee" + message);

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

    private SimpleMailMessage /* Message */ validateEmail(String firstName, String codigo, String email)
            throws MessagingException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USERNAME);
        message.setTo(email);
        message.setSubject(EMAIL_SUBJECT1);
        message.setText("Hola " + firstName + ", \n \n El código de validación es: " + codigo
                + "\n \n Plataforma educativa - CBDMQ");

        return message;
    }

    public void validateCodeEmail(String firstName, String codigo, String email) throws MessagingException {
        JavaMailSender emailSender = this.getJavaMailSender();
        SimpleMailMessage message = this.validateEmail(firstName, codigo, email);

        emailSender.send(message);

    }

    private SimpleMailMessage /* Message */ notificacionSendEmail(LocalDateTime fecha, String mensaje, String email)
            throws MessagingException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USERNAME);
        message.setTo(email);
        message.setSubject(EMAIL_SUBJECT2);
        message.setText("Para el dia " + fecha + ", \n \n " + mensaje
                + "\n \n Plataforma educativa - CBDMQ");

        return message;
    }

    public void notificacionEmail(LocalDateTime fecha, String mensaje, String email) throws MessagingException {
        JavaMailSender emailSender = this.getJavaMailSender();
        SimpleMailMessage message = this.notificacionSendEmail(fecha, mensaje, email);

        emailSender.send(message);

    }

    private SimpleMailMessage /* Message */ sendEmail(String email, String subject, String texto)
            throws MessagingException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USERNAME);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(texto);

        return message;
    }

    public void enviarEmail(String email, String subject, String texto) throws MessagingException {
        JavaMailSender emailSender = this.getJavaMailSender();
        SimpleMailMessage message = this.sendEmail(email, subject, texto);

        emailSender.send(message);

    }

    private MimeMessage sendEmail(String[] destinatarios, String subject, String texto, JavaMailSender emailSender)
            throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        message.setFrom(USERNAME);
        message.setSubject(subject);
        message.setText(texto);

        List<InternetAddress> recipientList = new ArrayList<>();
        for (String destinatario : destinatarios) {
            recipientList.add(new InternetAddress(destinatario));
        }
        message.setRecipients(MimeMessage.RecipientType.TO, recipientList.toArray(new InternetAddress[0]));


        return message;
    }

    public void enviarEmail(String[] destinatarios, String subject, String texto) throws MessagingException {
        JavaMailSender emailSender = this.getJavaMailSender();
        MimeMessage message = this.sendEmail(destinatarios, subject, texto, emailSender);

        emailSender.send(message);

    }
}

