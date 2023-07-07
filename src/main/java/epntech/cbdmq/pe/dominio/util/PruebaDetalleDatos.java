package epntech.cbdmq.pe.dominio.util;

import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "gen_prueba_detalle_datos")
@Table(name = "gen_prueba_detalle")
public class PruebaDetalleDatos{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_prueba_detalle")
	protected Integer codPruebaDetalle;

	@Column(name = "descripcion_prueba")
	protected String descripcionPrueba;

	@Column(name = "fecha_inicio")
	@JsonFormat(pattern = "yyyy-MM-dd")
	protected Date fechaInicio;

	@Column(name = "fecha_fin")
	@JsonFormat(pattern = "yyyy-MM-dd")
	protected Date fechaFin;

	@Column(name = "hora")
	@JsonFormat(pattern = "HH:mm")
	protected LocalTime hora;

	@Column(name = "estado")
	protected String estado;

	@Column(name = "cod_periodo_academico")
	protected Integer codPeriodoAcademico;

	@Column(name = "cod_curso_especializacion")
	protected Integer codCursoEspecializacion;

	@Column(name = "cod_subtipo_prueba")
	protected Integer codSubtipoPrueba;

	@Column(name = "orden_tipo_prueba")
	protected Integer ordenTipoPrueba;

	@Column(name = "puntaje_minimo")
	protected Double puntajeMinimo;

	@Column(name = "puntaje_maximo")
	protected Double puntajeMaximo;

	@Column(name = "tiene_puntaje")
	protected Boolean tienePuntaje;
	private String subTipoPruebaNombre;
	private String tipoPruebaNombre;

	public PruebaDetalleDatos() {
	};

	public PruebaDetalleDatos(
			Integer codPruebaDetalle,
			String descripcionPrueba,
			Date fechaInicio,
			Date fechaFin,
			LocalTime hora,
			String estado,
			Integer codPeriodoAcademico,
			Integer codCursoEspecializacion,
			Integer codSubtipoPrueba,
			Integer ordenTipoPrueba,
			Double puntajeMinimo,
			Double puntajeMaximo,
			Boolean tienePuntaje,
			String subTipoPruebaNombre,
			String tipoPruebaNombre) {
		this.codPruebaDetalle = codPruebaDetalle;
		this.descripcionPrueba = descripcionPrueba;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.hora = hora;
		this.estado = estado;
		this.codPeriodoAcademico = codPeriodoAcademico;
		this.codCursoEspecializacion = codCursoEspecializacion;
		this.codSubtipoPrueba = codSubtipoPrueba;
		this.ordenTipoPrueba = ordenTipoPrueba;
		this.puntajeMinimo = puntajeMinimo;
		this.puntajeMaximo = puntajeMaximo;
		this.tienePuntaje = tienePuntaje;
		this.subTipoPruebaNombre = subTipoPruebaNombre;
		this.tipoPruebaNombre = tipoPruebaNombre;
	}

}
