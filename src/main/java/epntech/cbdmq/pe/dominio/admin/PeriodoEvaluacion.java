 package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@SQLDelete(sql = "UPDATE {h-schema}epo_periodo_evaluacion SET estado = 'ELIMINADO' WHERE cod_periodo_evaluacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class PeriodoEvaluacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_periodo_evaluacion")
	private Integer codPeriodoEvaluacion;
	
	@Column(name = "cod_modulo")
	private Integer codModulo;
	
	@Column(name = "fecha_inicio_periodo_eval")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin_periodo_eval")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;

	@Column(name = "estado")
	private String estado;
	
	@Column(name = "descripcion")
	private String descripcion;
	
}
