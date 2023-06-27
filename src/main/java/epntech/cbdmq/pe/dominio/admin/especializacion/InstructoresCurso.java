package epntech.cbdmq.pe.dominio.admin.especializacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class InstructoresCurso {

	@Id
	private Long codInstructor;
	private String cedula;
	private String nombre;
	private String apellido;
	private Long codCursoEspecializacion;
	private String nombreCatalogoCurso;
	private Long codTipoInstructor;
	private String nombreTipoInstructor;
	
}
