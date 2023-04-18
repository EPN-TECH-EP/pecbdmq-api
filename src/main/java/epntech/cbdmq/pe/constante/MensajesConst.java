package epntech.cbdmq.pe.constante;

import org.springframework.beans.factory.annotation.Value;

public class MensajesConst {
	
	@Value("${spring.jpa.properties.hibernate.default_schema}")
	public static String DEFAULT_SCHEMA;
	
	public static final String REGISTRO_YA_EXISTE = "El registro con esa información ya existe";
	public static final String REGISTRO_VACIO = "El registro no permite campos vacíos";
	public static final String REGISTRO_NO_EXISTE = "No existe información con ese id";
	public static final String EXITO = "Información almacenada con éxito!";
	public static final String CEDULA_YA_EXISTE = "Cédula ya existe";
	public static final String CORREO_YA_EXISTE = "Correo ya existe";
	public static final String REGISTRO_ELIMINADO_EXITO = "Registro eliminado con éxito";
	public static final String FECHAS_YA_EXISTE = "Ya se encuentran registradas esas fechas";
	public static final String DATOS_RELACIONADOS = "No se puede eliminar, existen datos relacionados";
	public static final String CURSO_APROBADO="El curso se aprobo correctamente";
	public static final String CURSO_REPROBADO="El curso ha sido reprobado";
	public static final String APELACION_CURSO="Esta apelacion esta en curso";
	public static final String APELACION_NO_EXISTE="Esta apelacion no exte";
	public static final String REGISTRO_ACRUALIZADO = "Registro actualizado con éxito";
	public static final String ESTADO_INCORRECTO = "Estado incorrecto";
	public static final String PA_ACTIVO = "Ya existe un Período Académico Activo";
	
	public static final String ZIP_EXITO="Carpeta comprimida con éxito!";
	public static final String FOLDER_MAX_SIZE="La carptea supera el límite máximo permitido ";
	
	
}
