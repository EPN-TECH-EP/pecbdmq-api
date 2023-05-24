package epntech.cbdmq.pe.dominio.admin;


import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


import java.time.LocalTime;
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
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "gen_prueba_detalle")
@Table(name = "gen_prueba_detalle")
@SQLDelete(sql = "UPDATE {h-schema}gen_prueba_detalle SET estado = 'ELIMINADO' WHERE cod_prueba_detalle = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class PruebaDetalle {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_prueba_detalle")
	private Integer codPruebaDetalle;
	
	@Column(name = "descripcion_prueba")
	private String descripcionPrueba;	
	
	@Column(name = "fecha_inicio")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	@Column(name = "hora")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime hora;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	
	@Column(name = "cod_curso_especializacion")
	private Integer codCursoEspecializacion;
	
	@Column(name = "cod_subtipo_prueba")
	private Integer codSubtipoPrueba;
	
	@Column(name = "orden_tipo_prueba")
	private Integer ordenTipoPrueba;
}
