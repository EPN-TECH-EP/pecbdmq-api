<<<<<<< HEAD
package epntech.cbdmq.pe.constante;

import org.springframework.beans.factory.annotation.Value;

public class EmailConst {
    public static final String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtps";
    
    @Value("${pecb.email.username}")
    public static String USERNAME; // = "dmoreno@tech.epn.edu.ec";
    
    @Value("${pecb.email.password}")
    public static String PASSWORD; // = "0998211494Demr";
    
    
    public static final String FROM_EMAIL = USERNAME;
    public static final String CC_EMAIL = "";
    public static final String EMAIL_SUBJECT = "Plataforma Educativa CBDMQ - Nuevo password";
    public static final String EMAIL_SUBJECT1 = "Plataforma Educativa CBDMQ - Código confirmación";
    public static final String EMAIL_SMTP_SERVER = "smtp.office365.com";
    public static final int DEFAULT_PORT = 587;
    // propiedades del API
    public static final String PROP_SMTP_HOST = "mail.smtp.host";
    public static final String PROP_SMTP_AUTH = "mail.smtp.auth";
    public static final String PROP_SMTP_PORT = "mail.smtp.port";
    public static final String PROP_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String PROP_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
}
=======
package epntech.cbdmq.pe.constante;

public class EmailConst {
    
	public static final String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtps";    
    
    public static final String CC_EMAIL = "";
    public static final String EMAIL_SUBJECT = "Plataforma Educativa CBDMQ - Nuevo password";
    public static final String EMAIL_SMTP_SERVER = "smtp.office365.com";
    public static final int DEFAULT_PORT = 587;
    // propiedades del API
    public static final String PROP_SMTP_HOST = "mail.smtp.host";
    public static final String PROP_SMTP_AUTH = "mail.smtp.auth";
    public static final String PROP_SMTP_PORT = "mail.smtp.port";
    public static final String PROP_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String PROP_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
}
>>>>>>> a01b61b22eabe634e2310a4d40756d58879099f8
