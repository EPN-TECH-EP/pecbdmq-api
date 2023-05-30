package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_falta_periodo")
public class FaltaPeriodo {
	@Id
	
	@Column(name = "cod_falta_periodo")
	private Integer cod_falta_periodo;
	
	@Column(name = "cod_falta")
	private Integer cod_falta;
	
	@Column(name = "cod_periodo_academico")
	private Integer cod_periodo_academico;
	
	@Column(name = "puntaje")
	private BigDecimal puntaje;
	
	
}
