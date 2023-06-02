

package epntech.cbdmq.pe.constante;

public class SeguridadConst {
	public static final long TIEMPO_EXPIRACION = 432_000_000; // 5 days expressed in milliseconds
    public static final String PREFIJO_TOKEN = "Bearer ";
    public static final String CABECERA_TOKEN_JWT = "Jwt-Token";
    public static final String TOKEN_NO_VERIFICADO = "Token no puede ser verificado";
    public static final String PLATAFORMA_EDUCATIVA = "Plataforma Educativa CBDMQ";
    public static final String PLATAFORMA_EDUCATIVA_ADMIN = "Administración de la plataforma";
    public static final String PERMISOS = "permisos";
    public static final String ACCESO_RESTRINGIDO = "Requiere identificarse para acceder a esta página";
    public static final String ACCESO_DENEGADO = "No tiene permisos para acceder a esta página";
    public static final String METOD_HTTP_OPTIONS = "OPTIONS";

    public static final String[] URLS_PUBLICAS = { "/usuario/login", "/usuario/registro", "/usuario/imagen/**", "/usuario/guardarArchivo", "/usuario/maxArchivo", "/apis/test/informes/**", "/link/**", "/usuario/resetPassword/**","/swagger-ui/**","/api-docs/**", "/provincia/**", "/canton/**", "/inscripcionfor/**" };

    //public static final String[] PUBLIC_URLS = { "**" };
}

