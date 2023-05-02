package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "for_aprobados_validacion")
public class InscripcionesValidas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_aprobados_validacion")
	private Integer cod_aprobados_validacion;
	@Column(name = "cod_periodo_academico")
	private Long cod_periodo_academico;
	@Column(name = "cod_documento")
	private Long cod_documento;
	
	
	
}
