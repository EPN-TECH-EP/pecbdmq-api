package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class UsuarioEstudiante {
	
	@Id
	private Integer codUsuario;
	private String nombreUsuario;
	private Integer codDatosPersonales;
	private String nombre;
	private String apellido;
	private String correoPersonal;
	private String cedula;
	private LocalDate fechaNacimiento;
	private Integer codEstudiante;
	private String codigoUnicoEstudiante;

}
