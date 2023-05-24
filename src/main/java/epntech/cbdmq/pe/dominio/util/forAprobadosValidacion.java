package epntech.cbdmq.pe.dominio.util;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "for_aprobados_validacion")
public class forAprobadosValidacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cod_aprobados_validacion;
	
	private Integer cod_periodo_academico;
	
	private Integer cod_documento;
	
}
