
package epntech.cbdmq.pe.servicio;

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
import java.util.Set;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.dominio.admin.RequisitoFor;
import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoria;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProConvocatoriaRequisitoDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import static epntech.cbdmq.pe.constante.EmailConst.*;

@Service
public class EmailService {

    @Value("${pecb.email.username}")
    private String USERNAME;

    @Value("${pecb.email.password}")
    private String PASSWORD;

    @Value("${pecb.email.ruta-plantillas}")
    private String RUTA_PLANTILLAS;
    @Value("${url.descarga.archivos}")
    public String URLDESCARGA;

    private String FROM_EMAIL = USERNAME;


    public void validateCodeEmail(String firstName, String codigo, String email) throws MessagingException, IOException {
        String[] destinatarios = {email};
        if (email.contains(",") || email.contains(";")) {
            destinatarios = email.split(",|;");
        }
        String Path = RUTA_PLANTILLAS + "template-pecbdmq-validacion.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${firstName}", firstName);
        htmlTemplate = htmlTemplate.replace("${codigo}", codigo);
        MimeMessage message = this.createEmailHtml(destinatarios, EMAIL_SUBJECT, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);

    }

    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException, IOException {
        String destinatarios[] = {email};
        String Path = RUTA_PLANTILLAS + "template-pecbdmq.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${usuario}", firstName);
        htmlTemplate = htmlTemplate.replace("${password}", password);
        MimeMessage message = this.createEmailHtml(destinatarios, EMAIL_SUBJECT, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);

    }

    public void sendConvocatoriaFormacionEmail(String toEmail, ConvocatoriaFor convocatoria, Integer codigoDocumento) throws IOException, MessagingException {
        String[] emails = {toEmail};
        if (toEmail.contains(",") || toEmail.contains(";")) {
            emails = toEmail.split(",|;");
        }
        String Path = RUTA_PLANTILLAS + "templates-formacion/template-convocatoria.html";
        String linkRespaldo = URLDESCARGA + "/link/" + codigoDocumento;
        String link = "192.168.0.164:8084/inscripcion";

        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${nombre}", convocatoria.getNombre());
        htmlTemplate = htmlTemplate.replace("${fechaInicio}", convocatoria.getFechaInicioConvocatoria().toString());
        htmlTemplate = htmlTemplate.replace("${fechaFin}", convocatoria.getFechaFinConvocatoria().toString());
        htmlTemplate = htmlTemplate.replace("${linkRespaldo}", linkRespaldo);
        htmlTemplate = htmlTemplate.replace("${linkInscripcion}", link);

        Set<RequisitoFor> requisitosList = convocatoria.getRequisitos();
        StringBuilder requisitosHtml = new StringBuilder("<ul>");
        for (RequisitoFor requisito : requisitosList) {
            requisitosHtml.append("<li>").append(requisito.getNombre()).append("</li>");
        }
        requisitosHtml.append("</ul>");
        htmlTemplate = htmlTemplate.replace("${requisitos}", requisitosHtml.toString());

        MimeMessage message = this.createEmailHtml(emails, EMAIL_SUBJECT_CONVOCATORIA, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);

    }

    public void sendInscripcionForEmail(String email, String codPostulante) {
        try {
            String[] emails = {email};
            if (email.contains(",") || email.contains(";")) {
                emails = email.split(",|;");
            }

            String Path = RUTA_PLANTILLAS + "templates-formacion/template-inscripcion.html";
            String htmlTemplate = readFile(Path);
            htmlTemplate = htmlTemplate.replace("${idPostulante}", codPostulante);
            MimeMessage message = this.createEmailHtml(emails, EMAIL_SUBJECT, htmlTemplate);
            JavaMailSender emailSender = this.getJavaMailSender();
            emailSender.send(message);
        } catch (Exception ex) {

        }
    }

    public String notificacionAprobadoPruebaSendEmail(String nombrePrueba, DatoPersonal datoPersonal)
            throws MessagingException, IOException {

        String email = datoPersonal.getCorreoPersonal();
        String datoSaludos = datoPersonal.getNombre() + " " + datoPersonal.getApellido();
        //cadena de fecha actual en formato año mes día
        LocalDateTime fechaActual = LocalDateTime.now();
        String fecha = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] emails = {email};
        if (email.contains(",") || email.contains(";")) {
            emails = email.split(",|;");
        }
        String Path = RUTA_PLANTILLAS + "templates-formacion/template-aprobados.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${datosSaludos}", datoSaludos);
        htmlTemplate = htmlTemplate.replace("${nombrePrueba}", nombrePrueba);
        htmlTemplate = htmlTemplate.replace("${fechaFin}", fecha);
        MimeMessage message = this.createEmailHtml(emails, EMAIL_SUBJECT2, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);
        return htmlTemplate;

    }

    public String notificacionNoAprobadosPruebaSendEmail(String nombrePrueba, DatoPersonal datoPersonal)
            throws MessagingException, IOException {

        String email = datoPersonal.getCorreoPersonal();
        String datoSaludos = datoPersonal.getNombre() + " " + datoPersonal.getApellido();
        //cadena de fecha actual en formato año mes día
        LocalDateTime fechaActual = LocalDateTime.now();
        String fecha = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] emails = {email};
        if (email.contains(",") || email.contains(";")) {
            emails = email.split(",|;");
        }
        String Path = RUTA_PLANTILLAS + "templates-formacion/template-no-aprobados.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${datosSaludos}", datoSaludos);
        htmlTemplate = htmlTemplate.replace("${nombrePrueba}", nombrePrueba);
        htmlTemplate = htmlTemplate.replace("${fechaFin}", fecha);
        MimeMessage message = this.createEmailHtml(emails, EMAIL_SUBJECT2, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);
        return htmlTemplate;

    }

    public String notificacionAprobadoPruebaFinalSendEmail(String nombrePrueba, DatoPersonal datoPersonal)
            throws MessagingException, IOException {

        String email = datoPersonal.getCorreoPersonal();
        String datoSaludos = datoPersonal.getNombre() + " " + datoPersonal.getApellido();
        //cadena de fecha actual en formato año mes día
        LocalDateTime fechaActual = LocalDateTime.now();
        String fecha = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] emails = {email};
        if (email.contains(",") || email.contains(";")) {
            emails = email.split(",|;");
        }
        String Path = RUTA_PLANTILLAS + "templates-formacion/template-aprobados-final.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${datosSaludos}", datoSaludos);
        htmlTemplate = htmlTemplate.replace("${nombrePrueba}", nombrePrueba);
        htmlTemplate = htmlTemplate.replace("${fechaFin}", fecha);
        MimeMessage message = this.createEmailHtml(emails, EMAIL_SUBJECT2, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);
        return htmlTemplate;

    }

    public String notificacionNoAprobadoPruebaFinalSendEmail(String nombrePrueba, DatoPersonal datoPersonal)
            throws MessagingException, IOException {

        String email = datoPersonal.getCorreoPersonal();
        String datoSaludos = datoPersonal.getNombre() + " " + datoPersonal.getApellido();
        //cadena de fecha actual en formato año mes día
        LocalDateTime fechaActual = LocalDateTime.now();
        String fecha = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] emails = {email};
        if (email.contains(",") || email.contains(";")) {
            emails = email.split(",|;");
        }
        String Path = RUTA_PLANTILLAS + "templates-formacion/template-no-aprobados-final.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${datosSaludos}", datoSaludos);
        htmlTemplate = htmlTemplate.replace("${nombrePrueba}", nombrePrueba);
        htmlTemplate = htmlTemplate.replace("${fechaFin}", fecha);
        MimeMessage message = this.createEmailHtml(emails, EMAIL_SUBJECT2, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);
        return htmlTemplate;

    }

    public void sendMensajeTextGenerico(String email, String subject, String mensaje) {
        try {
            String[] emails = {email};
            if (email.contains(",") || email.contains(";")) {
                emails = email.split(",|;");
            }

            String Path = RUTA_PLANTILLAS + "template-pecbdmq-general.html";
            String htmlTemplate = readFile(Path);
            htmlTemplate = htmlTemplate.replace("${mensaje}", mensaje);
            MimeMessage message = this.createEmailHtml(emails, subject, htmlTemplate);
            JavaMailSender emailSender = this.getJavaMailSender();
            emailSender.send(message);
        } catch (Exception ex) {

        }
    }

    public void sendMensajeHtmlGenerico(String[] email, String subject, String mensaje) {
        try {

            String Path = RUTA_PLANTILLAS + "template-pecbdmq-general-html.html";
            String htmlTemplate = readFile(Path);
            htmlTemplate = htmlTemplate.replace("${mensaje}", mensaje);
            MimeMessage message = this.createEmailHtml(email, subject, htmlTemplate);
            JavaMailSender emailSender = this.getJavaMailSender();
            emailSender.send(message);
        } catch (Exception ex) {

        }
    }
    //ESPECIALIZACION

    public void sendConvocatoriaEspEmail(String[] destinatarios, String subject, String descripcion, String fechaInicioConvocatoria, String fechaInicioCurso, String fechaFinCurso, String cupos, Set<Requisito> requisitos, String link)
            throws MessagingException, IOException {
        String Path = RUTA_PLANTILLAS + "templates-especializacion/template-convocatoria.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${descripcionCurso}", descripcion);
        htmlTemplate = htmlTemplate.replace("${fechaInicioConvocatoria}", fechaInicioConvocatoria);
        htmlTemplate = htmlTemplate.replace("${fechaInicioCurso}", fechaInicioCurso);
        htmlTemplate = htmlTemplate.replace("${fechaFinCurso}", fechaFinCurso);
        htmlTemplate = htmlTemplate.replace("${cupos}", cupos);
        htmlTemplate = htmlTemplate.replace("${link}", link);
        StringBuilder requisitosHtml = new StringBuilder("<ul>");
        for (Requisito requisito : requisitos) {
            requisitosHtml.append("<li>").append(requisito.getNombre()).append("</li>");
        }
        requisitosHtml.append("</ul>");
        htmlTemplate = htmlTemplate.replace("${requisitos}", requisitosHtml.toString());

        MimeMessage message = this.createEmailHtml(destinatarios, subject, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);
    }


    //PROFESIONALIZACIÓN
    public void sendConvocatoriaProEmail(String[] destinatarios, String subject, String descripcion, String fechaInicioConvocatoria, String fechaInicio, String fechaFin, List<ProConvocatoriaRequisitoDto> requisitos, String mensajes, String link)
            throws MessagingException, IOException {
        String Path = RUTA_PLANTILLAS + "templates-profesionalizacion/template-convocatoria.html";
        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${descripcion}", descripcion);
        htmlTemplate = htmlTemplate.replace("${fechaInicioConvocatoria}", fechaInicioConvocatoria);
        htmlTemplate = htmlTemplate.replace("${fechaInicio}", fechaInicio);
        htmlTemplate = htmlTemplate.replace("${fechaFin}", fechaFin);
        htmlTemplate = htmlTemplate.replace("${link}", link);
        htmlTemplate = htmlTemplate.replace("${linkInscripcion}", link);

        StringBuilder requisitosHtml = new StringBuilder("<ul>");
        for (ProConvocatoriaRequisitoDto requisito : requisitos) {
            requisitosHtml.append("<li>").append(requisito.getNombreRequisito()).append("</li>");
        }
        requisitosHtml.append("</ul>");
        htmlTemplate = htmlTemplate.replace("${requisitos}", requisitosHtml.toString());

        MimeMessage message = this.createEmailHtml(destinatarios, subject, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);
    }


    public void sendInscripcionProEmail(String toEmail, ProConvocatoria convocatoria, ProInscripcionDto proInscripcion) throws IOException, MessagingException {
        String[] emails = {toEmail};
        if (toEmail.contains(",") || toEmail.contains(";")) {
            emails = toEmail.split(",|;");
        }
        String Path = RUTA_PLANTILLAS + "templates-profesionalizacion/template-inscripcion.html";

        String htmlTemplate = readFile(Path);
        htmlTemplate = htmlTemplate.replace("${numeroConvocatoria}", convocatoria.getCodigoUnicoConvocatoria());
        htmlTemplate = htmlTemplate.replace("${usuario}", proInscripcion.getApellido() + " " + proInscripcion.getNombre());
        MimeMessage message = this.createEmailHtml(emails, EMAIL_SUBJECT_INSCRIPCION, htmlTemplate);
        JavaMailSender emailSender = this.getJavaMailSender();
        emailSender.send(message);


    }

    public void sendAprobadoValidacionProEmail(String toEmail) {
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
        } catch (Exception ex) {

        }
    }

    public String htmlTemplateRenderAprobacionInscripcion() throws IOException {
        String Path = RUTA_PLANTILLAS + "/templates-profesionalizacion/template-aprobados.html";
        String htmlTemplate = readFile(Path);
        return htmlTemplate;
    }


    public void sendRechazadoValidacionProEmail(String toEmail) {
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
        } catch (Exception ex) {

        }
    }

    public String htmlTemplateRenderRechazoInscripcion() throws IOException {
        String Path = RUTA_PLANTILLAS + "templates-profesionalizacion/template-reprobados.html";
        String htmlTemplate = readFile(Path);
        return htmlTemplate;
    }


    private String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }

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


    private MimeMessage createEmailHtml(String[] destinatarios, String subject, String textoHtml)
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

        message.setContent(textoHtml, "text/html; charset=utf-8");
        return message;

    }
}

