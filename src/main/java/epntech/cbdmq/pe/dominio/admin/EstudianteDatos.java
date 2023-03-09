package epntech.cbdmq.pe.dominio.admin;

import java.util.Date;

import lombok.Data;

@Data
public class EstudianteDatos extends Estudiante {

	private static final long serialVersionUID = -4289535118198584986L;
	
	private String nombre;
	private String apellido;
	private String cedula; 
	private String ciudad;
	private String correo_personal;
	private Date fecha_nacimiento;
	private String num_telef;
	private String tipo_sangre;
	private String estacion;
	private String provincia;
	private String unidad_gestion;
	
	public EstudianteDatos(Integer codEstudiante, String grado, String resultadoEstudiante, String idEstudiante, 
			String nombre, String apellido, String cedula, String ciudad, String correo_personal, Date fecha_nacimiento,
			String num_telef, String tipo_sangre, String estacion,  String provincia, String unidad_gestion, String estado) {
		super();
		
		this.codEstudiante = codEstudiante; 
		this.grado = grado; 
		this.resultadoEstudiante = resultadoEstudiante; 
		this.idEstudiante = idEstudiante;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cedula = cedula;
		this.ciudad = ciudad;
		this.correo_personal = correo_personal;
		this.fecha_nacimiento = fecha_nacimiento;
		this.num_telef = num_telef;
		this.tipo_sangre = tipo_sangre;
		this.estacion = estacion;
		this.provincia = provincia;
		this.unidad_gestion = unidad_gestion;
		this.estado = estado;
	}
	
	
}
