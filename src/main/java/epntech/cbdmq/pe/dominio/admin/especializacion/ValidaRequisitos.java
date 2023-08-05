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
@Table(name = "esp_validacion_requisito")
public class ValidaRequisitos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_validacion_requisito")
	private Long codValidacionRequisito;
	
	@Column(name = "cod_requisito")
	private Long codRequisito;

	@Column(name = "cod_inscripcion")
	private Long codInscripcion;
	
	@Column(name = "observacion")
	private String observacion;
	
	@Column(name = "estado")
	private Boolean estado;

}
