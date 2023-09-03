
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoria;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionDto;
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

    public void sendInscripcionFormacionEmail(String toEmail, ProConvocatoria convocatoria, ProInscripcionDto proInscripcion){
        try {
            JavaMailSender emailSender = this.getJavaMailSender();
            MimeMessage message = this.getJavaMailSender().createMimeMessage();
            InternetAddress fromAddress = new InternetAddress(USERNAME);
            message.setFrom(fromAddress);
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject(EMAIL_SUBJECT);
            String Path = RUTA_PLANTILLAS + "templateInscripcion.html"; //"src\\main\\resources\\templateCorreo.html";
            String htmlTemplate = readFile(Path);
            htmlTemplate = htmlTemplate.replace("${numeroConvocatoria}", convocatoria.getCodigoUnicoConvocatoria());
            htmlTemplate = htmlTemplate.replace("${usuario}", proInscripcion.getApellido() + " " + proInscripcion.getNombre());

            message.setContent(htmlTemplate, "text/html; charset=utf-8");
            emailSender.send(message);
        }
        catch (Exception ex){

        }
    }

    public void sendConvocatoriaProfesionalizacionEmail(String toEmail){
        try {
            JavaMailSender emailSender = this.getJavaMailSender();
            MimeMessage message = this.getJavaMailSender().createMimeMessage();
            InternetAddress fromAddress = new InternetAddress(USERNAME);
            message.setFrom(fromAddress);
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("Notificacion de convocatoria");
            String htmlTemplate = htmlTemplateRenderConvocatoriaProfesionalizacion();
            message.setContent(htmlTemplate, "text/html; charset=utf-8");
            emailSender.send(message);
        }
        catch (Exception ex){

        }
    }

    public void sendvValidacionAprobadoEmail(String toEmail){
        try {
            JavaMailSender emailSender = this.getJavaMailSender();
            MimeMessage message = this.getJavaMailSender().createMimeMessage();
            InternetAddress fromAddress = new InternetAddress(USERNAME);
            message.setFrom(fromAddress);
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("Aprobación de inscripción");
            String htmlTemplate = htmlTemplateRenderAprobacionInscripcion();
            message.setContent(htmlTemplate, "text/html; charset=utf-8");
            emailSender.send(message);
        }
        catch (Exception ex){

        }
    }

    public void sendValidacionRechazadoEmail(String toEmail){
        try {
            JavaMailSender emailSender = this.getJavaMailSender();
            MimeMessage message = this.getJavaMailSender().createMimeMessage();
            InternetAddress fromAddress = new InternetAddress(USERNAME);
            message.setFrom(fromAddress);
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("Rechazo de inscripción");
            String htmlTemplate = htmlTemplateRenderRechazoInscripcion();
            message.setContent(htmlTemplate, "text/html; charset=utf-8");
            emailSender.send(message);
        }
        catch (Exception ex){

        }
    }

    public String htmlTemplateRenderConvocatoriaProfesionalizacion() throws IOException {
        String Path = RUTA_PLANTILLAS + "templateNotificacionConvocatoria.html"; //"src\\main\\resources\\templateCorreo.html";
        String htmlTemplate = readFile(Path);
        return htmlTemplate;
    }

    public String htmlTemplateRenderAprobacionInscripcion() throws IOException {
        String Path = RUTA_PLANTILLAS + "templateAprobacionInscripcion.html"; //"src\\main\\resources\\templateCorreo.html";
        String htmlTemplate = readFile(Path);
        return htmlTemplate;
    }

    public String htmlTemplateRenderRechazoInscripcion() throws IOException {
        String Path = RUTA_PLANTILLAS + "templateRechazoInscripcion.html"; //"src\\main\\resources\\templateCorreo.html";
        String htmlTemplate = readFile(Path);
        return htmlTemplate;
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
        // si email es una lista, se envia a todos los correos de la lista con setTo(String[] to)
        // busca si se usa , o ; como separador
        if (email.contains(",") || email.contains(";")) {
            String[] emails = email.split(",|;");
            message.setTo(emails);
        } else {
            message.setTo(email);
        }
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
        String Path = RUTA_PLANTILLAS + "templateCorreo.html"; //"src\\main\\resources\\templateCorreo.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${usuario}", firstName);
        htmlTemplate = htmlTemplate.replace("${password}", password);
        message.setContent(htmlTemplate, "text/html; charset=utf-8");
        return message;

    }

    private String getHtmlGeneric(String template)
            throws IOException {
        String Path = RUTA_PLANTILLAS + template;
        //"templateCorreo.html";
        String htmlTemplate = readFile(Path);
        return htmlTemplate;
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
        // si email es una lista, se envia a todos los correos de la lista con setTo(String[] to)
        // busca si se usa , o ; como separador
        if (email.contains(",") || email.contains(";")) {
            String[] emails = email.split(",|;");
            message.setTo(emails);
        } else {
            message.setTo(email);
        }
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

        // si email es una lista, se envia a todos los correos de la lista con setTo(String[] to)
        // busca si se usa , o ; como separador
        if (email.contains(",") || email.contains(";")) {
            String[] emails = email.split(",|;");
            message.setTo(emails);
        } else {
            message.setTo(email);
        }

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


        // si email es una lista, se envia a todos los correos de la lista con setTo(String[] to)
        // busca si se usa , o ; como separador
        if (email.contains(",") || email.contains(";")) {
            String[] emails = email.split(",|;");
            message.setTo(emails);
        } else {
            message.setTo(email);
        }

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

    public MimeMessage sendEmailHtmlToList(String[] destinatarios, String subject, String descripcion, String fechaInicioConvocatoria, String fechaInicioCurso, String fechaFinCurso, String cupos, String requisitos, String link)
            throws MessagingException, IOException {
        JavaMailSender emailSender = this.getJavaMailSender();
        MimeMessage message = this.getJavaMailSender().createMimeMessage();
        InternetAddress fromAddress = new InternetAddress(USERNAME);
        message.setFrom(fromAddress);
        message.setSubject(subject);
        for (String destinatario : destinatarios) {
            message.setRecipients(MimeMessage.RecipientType.TO, destinatario);
        }
        message.setSubject(subject);
        String htmlTemplate = this.getHtmlGeneric("convocatoriaEsp.html");
        htmlTemplate = htmlTemplate.replace("${descripcionCurso}", descripcion);
        htmlTemplate = htmlTemplate.replace("${fechaInicioConvocatoria}", fechaInicioConvocatoria);
        htmlTemplate = htmlTemplate.replace("${fechaInicioCurso}", fechaInicioCurso);
        htmlTemplate = htmlTemplate.replace("${fechaFinCurso}", fechaFinCurso);
        htmlTemplate = htmlTemplate.replace("${cupos}", cupos);
        htmlTemplate = htmlTemplate.replace("${requisitos}", requisitos);
        htmlTemplate = htmlTemplate.replace("${link}", link);
        message.setContent(htmlTemplate, "text/html; charset=utf-8");
        emailSender.send(message);
        return message;
    }

    public MimeMessage sendEmailHtmlToListPro(String[] destinatarios, String subject, String descripcion, String fechaInicioConvocatoria, String fechaInicio, String fechaFin, String requisitos, String mensajes, String link)
            throws MessagingException, IOException {
        JavaMailSender emailSender = this.getJavaMailSender();
        MimeMessage message = this.getJavaMailSender().createMimeMessage();
        InternetAddress fromAddress = new InternetAddress(USERNAME);
        message.setFrom(fromAddress);
        message.setSubject(subject);
        for (String destinatario : destinatarios) {
            message.setRecipients(MimeMessage.RecipientType.TO, destinatario);
        }
        message.setSubject(subject);
        String htmlTemplate = this.getHtmlGeneric("convocatoriaPro.html");
        htmlTemplate = htmlTemplate.replace("${descripcion}", descripcion);
        htmlTemplate = htmlTemplate.replace("${fechaInicioConvocatoria}", fechaInicioConvocatoria);
        htmlTemplate = htmlTemplate.replace("${fechaInicio}", fechaInicio);
        htmlTemplate = htmlTemplate.replace("${fechaFin}", fechaFin);
        htmlTemplate = htmlTemplate.replace("${requisitos}", requisitos);
        htmlTemplate = htmlTemplate.replace("${mensajes}", mensajes);
        htmlTemplate = htmlTemplate.replace("${link}", link);
        message.setContent(htmlTemplate, "text/html; charset=utf-8");
        emailSender.send(message);
        return message;
    }

    public void enviarEmail(String[] destinatarios, String subject, String texto) throws MessagingException {
        JavaMailSender emailSender = this.getJavaMailSender();
        MimeMessage message = this.sendEmail(destinatarios, subject, texto, emailSender);
        emailSender.send(message);

    }

    private SimpleMailMessage /* Message */ notificacionAprobadoSendEmail(String nombrePrueba, DatoPersonal datoPersonal /*String email*/)
            throws MessagingException {

        String email = datoPersonal.getCorreoPersonal();
        String datoSaludos = datoPersonal.getNombre() + " " + datoPersonal.getApellido();
        //cadena de fecha actual en formato año mes día
        LocalDateTime fechaActual = LocalDateTime.now();
        String fecha = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USERNAME);

        // si email es una lista, se envia a todos los correos de la lista con setTo(String[] to)
        // busca si se usa , o ; como separador
        if (email.contains(",") || email.contains(";")) {
            String[] emails = email.split(",|;");
            message.setTo(emails);
        } else {
            message.setTo(email);
        }

        message.setSubject(EMAIL_SUBJECT2);
        message.setText("Saludos " + datoSaludos + " \n Usted ha aprobado la prueba " + nombrePrueba
                + "\n Fecha: " + fecha + " \n Plataforma educativa - CBDMQ");

        return message;
    }

    public String notificacionAprobadoEmail(String nombrePrueba, DatoPersonal datoPersonal/*String email*/) throws MessagingException {
        JavaMailSender emailSender = this.getJavaMailSender();
        SimpleMailMessage message = this.notificacionAprobadoSendEmail(nombrePrueba, datoPersonal);

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

