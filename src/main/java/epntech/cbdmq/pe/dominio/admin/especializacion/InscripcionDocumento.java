package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_inscripcion_documento")
public class InscripcionDocumento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_inscripcion_documento")
	private Long codInscripcionDocumento;
	
	@Column(name = "cod_inscripcion")
	private Long codInscripcion;
	
	@Column(name = "cod_documento")
	private Long codDocumento;
	
}
