package epntech.cbdmq.pe.dominio.util;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "for_aprobados_validacion")
public class forAprobadosValidacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_aprobados_validacion")
	private Integer codAprobadosValidacion;
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	@Column(name = "cod_documento")
	private Integer codDocumento;
	
}
