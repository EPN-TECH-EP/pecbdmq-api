package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class InstructorDatos {

	@Id
	private Long codInstructor;
	private Integer codTipoProcedencia;
	private String tipoProcedencia;
	private Integer codEstacion;
	private String nombreZona;
	private Integer codUnidadGestion;
	private String unidadGestion;
	private Integer codTipoContrato;
	private String nombreTipoContrato;
	private String cedula;
	private String nombre;
	private String apellido;
	private String correoPersonal;
	
}
