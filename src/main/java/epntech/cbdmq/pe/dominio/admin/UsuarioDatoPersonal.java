package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UsuarioDatoPersonal {
	
	@Id
	private Integer codInscripcion;
	private Integer codUsuario;
	private String nombreUsuario;
	private Integer codDatosPersonales;
	private String apellido;
	private String nombre;
	private String cedula;
	private String correoPersonal;
	private Integer codPeriodo;

}
