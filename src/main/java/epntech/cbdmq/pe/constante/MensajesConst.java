package epntech.cbdmq.pe.constante;

import org.springframework.beans.factory.annotation.Value;

public class MensajesConst {
	
	@Value("${spring.jpa.properties.hibernate.default_schema}")
	public static String DEFAULT_SCHEMA;
	
	public static final String REGISTRO_YA_EXISTE = "El registro con esa información ya existe";
	public static final String REGISTRO_VACIO = "El registro no permite campos vacíos";
	public static final String REGISTRO_NO_EXISTE = "No existe información con ese id";
	public static final String CEDULA_YA_EXISTE = "Cédula ya existe";
	public static final String CORREO_YA_EXISTE = "Correo ya existe";
	public static final String REGISTRO_ELIMINADO_EXITO = "Registro eliminado con éxito";
	public static final String FECHAS_YA_EXISTE = "Ya se encuentran registradas esas fechas";
	public static final String DATOS_RELACIONADOS = "No se puede eliminar, existen datos relacionados";
	public static final String CURSO_APROBADO="El curso se aprobo correctamente";
	public static final String CURSO_REPROBADO="El curso ha sido reprobado";
}
