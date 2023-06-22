package epntech.cbdmq.pe.dominio.util;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
