package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_validacion_requisitos")
public class ValidacionRequisitos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_validacion_requisitos")
	private Integer codValidacion;
	
	@Column(name = "cod_postulante")
	private Integer codPostulante;
	
	@Column(name = "cod_requisitos")
	private Integer codRequisitos;
	
	@Column(name = "estado")
	private Boolean estado;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "estado_muestra")
	private Boolean estadoMuestra;
	
	@Column(name = "observacion_muestra")
	private String observacionMuestra;
}
