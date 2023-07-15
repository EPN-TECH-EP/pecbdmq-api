package epntech.cbdmq.pe.dominio.util;

import java.util.Date;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import lombok.Data;

@Data
public class EstudianteDatos{
	
	private Integer codEstudiante;
	private String nombre;
	private String apellido;
	private String cedula; 
	private String correoPersonal;
	
}
