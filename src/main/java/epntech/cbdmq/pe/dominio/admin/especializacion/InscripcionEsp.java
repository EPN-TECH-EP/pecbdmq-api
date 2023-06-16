package epntech.cbdmq.pe.dominio.admin.especializacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_inscripcion")
public class InscripcionEsp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_inscripcion")
	private Long codInscripcion;
	
	@Column(name = "cod_estudiante")
	private Long codEstudiante;
	
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;

}
