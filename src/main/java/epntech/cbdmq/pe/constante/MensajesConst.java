package epntech.cbdmq.pe.constante;

import org.springframework.beans.factory.annotation.Value;

public class MensajesConst {
	
	@Value("${spring.jpa.properties.hibernate.default_schema}")
	public static String DEFAULT_SCHEMA;
	
	public static final String REGISTRO_YA_EXISTE = "El registro con esa información ya existe";
	public static final String REGISTRO_VACIO = "El registro no permite campos vacíos";
	public static final String REGISTRO_NO_EXISTE = "No existe información con el id ingresado";
	public static final String EXITO = "Información almacenada con éxito!";
	public static final String CEDULA_YA_EXISTE = "Cédula ya existe";
	public static final String CEDULA_NO_EXISTE = "No existe información con la Cédula";
	public static final String CEDULA_INCORRECTA = "La Cédula ingresada es Incorrecta";
	public static final String CORREO_YA_EXISTE = "Correo ya existe";
	public static final String REGISTRO_ELIMINADO_EXITO = "Registro eliminado con éxito";
	public static final String FECHAS_YA_EXISTE = "Ya se encuentran registradas esas fechas";
	public static final String DATOS_RELACIONADOS = "No se puede eliminar, existen datos relacionados";
    public static final String CURSO_APROBADO = "El curso se aprobo correctamente";
    public static final String CURSO_REPROBADO = "El curso ha sido reprobado";
    public static final String APELACION_CURSO = "Esta apelacion esta en curso";
    public static final String APELACION_NO_EXISTE = "Esta apelacion no exte";
	public static final String REGISTRO_ACTUALIZADO = "Registro actualizado con éxito";
	public static final String REGISTRO_ELIMINADO = "Registro eliminado con éxito";
	public static final String ESTADO_INCORRECTO = "Estado incorrecto";
	public static final String PA_ACTIVO = "Ya existe un Período Académico Activo";
	public static final String ASIGNACION_EXITO = "Se asignaron los valores con éxito";
	public static final String EDAD_NO_CUMPLE = "No cumple con la edad requerida";
	public static final String PIN_INCORRECTO = "El PIN no coincide";
	public static final String ERROR_REGISTRO = "Se presentó un problema al realizar la transacción";
	public static final String POSTULANTE_ASIGNADO = "El postulante ya está asignado a un usuario";
	public static final String ESTADO_INVALIDO = "El estado no corresponde o es inválido.";
	public static final String ORDEN_INCORRECTO = "No existen un orden correcto de los registros";
	public static final String DATOS_REGISTRADOS = "Datos registrados exitosamente";
	public static final String PROCESO_EXITO = "Proceso ejecutado con éxito!";
	public static final String NO_PERIODO_ACTIVO = "No existe Período Academico activo";
	public static final String CONVOCATORIA_NO_EXISTE = "No existe convocatoria ";

	public static final String FECHA_INSCRIPCION_INVALIDA = "La fecha de inscripción fuera del rango permitido ";
	public static final String HORA_INSCRIPCION_INVALIDA = "La hora de inscripción fuera del rango permitido ";
	public static final String PA_INACTIVO = "Período Académico Inactivo";
	
    public static final String ZIP_EXITO = "Carpeta comprimida con éxito!";
    public static final String FOLDER_MAX_SIZE = "La carptea supera el límite máximo permitido ";
    public static final String NO_ENCUENTRA = "No encuentra objetos relacionados";
	
    public static final String NO_PARAMETRO = "No se encuentra el parametro configurado";
    
    public static final String DOCUMENTO_NO_EXISTE = "El id del documento no existe";
    public static final String AULA_NO_EXISTE = "El aula no existe";
}
