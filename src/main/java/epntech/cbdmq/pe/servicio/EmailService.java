
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
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

    @Value("${pecb.email.ruta-plantillas}")
    private String RUTA_PLANTILLAS;

    private String FROM_EMAIL = USERNAME;

    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException, IOException {
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
            throws MessagingException, IOException {
        MimeMessage message = this.getJavaMailSender().createMimeMessage();
        InternetAddress fromAddress = new InternetAddress(USERNAME);
        message.setFrom(fromAddress);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(EMAIL_SUBJECT);
                /*-----------CON HTML DIRECTO----------
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
                "            <img src=\"https://cem.epn.edu.ec/imagenes/logos_institucionales/big_jpg/EPN_logo_big.jpg\" alt=\"Logo\">\n" +
                "        </div>\n" +
                "        <div class=\"message\">\n" +
                "            <h2>Recuperación de contraseña</h2>\n" +
                "            <p>Estimado(a) " + firstName + "</p>\n" +
                "            <p>Hemos recibido una solicitud para generar la contraseña de tu cuenta </p>\n" +
                "            <p>Tu nueva contraseña es: " + password + "</p>\n" +
                "            <p>Si no has solicitado restablecer la contraseña, puedes ignorar este correo electrónico.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Este correo electrónico fue enviado automáticamente. Por favor, no respondas a este correo.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";

                message.setContent(htmlContent, "text/html; charset=utf-8");

         */
        String Path= RUTA_PLANTILLAS + "templateCorreo.html"; //"src\\main\\resources\\templateCorreo.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${usuario}", firstName);
        htmlTemplate = htmlTemplate.replace("${password}", password);
        message.setContent(htmlTemplate, "text/html; charset=utf-8");
        return message;

    }
    private String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
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
    private SimpleMailMessage /* Message */ notificacionAprobadoSendEmail( String nombrePrueba, String email)
            throws MessagingException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USERNAME);
        message.setTo(email);
        message.setSubject(EMAIL_SUBJECT2);
        message.setText("Usted ha aprobado la prueba " + nombrePrueba
                + "\n \n Plataforma educativa - CBDMQ");

        return message;
    }
    public String notificacionAprobadoEmail(String nombrePrueba, String email) throws MessagingException {
        JavaMailSender emailSender = this.getJavaMailSender();
        SimpleMailMessage message = this.notificacionAprobadoSendEmail(nombrePrueba, email);

        emailSender.send(message);
        return message.getText();

    }

    public void enviarEmailHtml(String[] destinatarios, String subject, String texto) {
        try {
            JavaMailSender emailSender = this.getJavaMailSender();
            MimeMessage message = this.createEmailHtml(destinatarios, subject, texto);
            emailSender.send(message);
        } catch (MessagingException me) {
            throw new BusinessException("Error al enviar correo");
        }
    }

    private MimeMessage createEmailHtml(String[] destinatarios, String subject, String texto)
            throws MessagingException {
        MimeMessage message = this.getJavaMailSender().createMimeMessage();
        InternetAddress fromAddress = new InternetAddress(USERNAME);
        message.setFrom(fromAddress);

        List<InternetAddress> recipientList = new ArrayList<>();
        for (String destinatario : destinatarios) {
            recipientList.add(new InternetAddress(destinatario));
        }

        message.setRecipients(MimeMessage.RecipientType.TO, recipientList.toArray(new InternetAddress[0]));
        message.setSubject(subject);

        message.setContent(texto, "text/html; charset=utf-8");
        return message;

    }
}

