package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity(name = "epo_periodo_evaluacion")
@Table(name = "epo_periodo_evaluacion")
public class PeriodoEvaluacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_periodo_evaluacion")
	private Integer codPeriodoEvaluacion;
	
	@Column(name = "cod_modulo")
	private Integer codModulo;
	
	@Column(name = "fecha_inicio_periodo_eval")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime fechaInicio;
	
	@Column(name = "fecha_fin_periodo_eval")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime fechaFin;
}
