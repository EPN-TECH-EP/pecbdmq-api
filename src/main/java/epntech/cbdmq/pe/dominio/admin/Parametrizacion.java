package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "gen_parametriza_fecha")
@Table(name = "gen_parametriza_fecha")
@SQLDelete(sql = "UPDATE {h-schema}gen_parametriza_fecha SET estado = 'ELIMINADO' WHERE cod_parametriza = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Parametrizacion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_parametriza")
	private Integer codparametriza;
	/*@Column(name = "cod_tipo_prueba")
	private Integer codtipoprueba;
	@Column(name = "cod_periodo_academico")
	private Integer codperiodoacademico;
	@Column(name = "cod_modulo")
	private Integer codmodulo;
	@Column(name = "cod_periodo_evaluacion")
	private Integer codperiodoevaluacion;
	@Column(name = "cod_curso_especializacion")
	private Integer codcursoespecializacion;
	@Column(name = "cod_tipo_fecha")
	private Integer codtipofecha;*/
	@Column(name = "fecha_inicio_param")
	private Date fechainicioparam;
	@Column(name = "fecha_fin_param")
	private Date fechafinparam;
	@Column(name = "hora_inicio_param")
	private LocalTime horainicioparam;
	@Column(name = "hora_fin_param")
	private LocalTime horafinparam;
	@Column(name = "observacion_parametriza")
	private String observacionparametriza;
	@Column(name = "estado")
	private String estado;
		
}
