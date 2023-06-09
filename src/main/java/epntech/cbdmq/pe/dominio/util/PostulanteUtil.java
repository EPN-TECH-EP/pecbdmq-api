package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class PostulanteUtil {

	@Id

	private Integer codPostulante;

	private Integer codDatosPersonales;

	private String idPostulante;

	private String estado;

	private Integer codUsuario;

	private Integer codPeriodoAcademico;
	
	private String nombre;
	
	private String apellido;
	
	private String cedula;
	
	private String nombreUsuario;
	
	private String correoUsuario;
}
