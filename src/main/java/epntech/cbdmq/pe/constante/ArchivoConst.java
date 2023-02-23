package epntech.cbdmq.pe.constante;

import org.springframework.beans.factory.annotation.Value;

public class ArchivoConst {
	
	@Value("${spring.servlet.multipart.max-file-size}")
    public static String TAMAÑO_MÁXIMO;
    
    public static final String EXTENSION_JPG = "jpg";
    public static final String CARPETA_USUARIO = System.getProperty("user.home") + "/supportportal/user/";
    
    public static final String PATH_DEFECTO_IMAGEN_USUARIO = "/user/image/profile/";
    public static final String PATH_IMAGEN_USUARIO = "/user/image/";
    
    public static final String URL_IMAGEN_TEMPORAL = "https://robohash.org/";
    
    public static final String ARCHIVO_GUARDADO = "Archivo guardado: ";
    public static final String DIRECTORIO_CREADO = "Directorio creado para: ";    
    public static final String NO_ES_ARCHIVO_IMAGEN = " No es un archivo de imagen! Seleccione un archivo de imagen";
    
    public static final String ARCHIVO_MUY_GRANDE = " Archivo excede el tamaño máximo permitido.";
        
    public static final String PUNTO = ".";
    public static final String FORWARD_SLASH = "/";
}
