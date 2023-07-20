package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import epntech.cbdmq.pe.dominio.util.PruebaDetalleData;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "gen_prueba_detalle")
@Table(name = "gen_prueba_detalle")
@SQLDelete(sql = "UPDATE {h-schema}gen_prueba_detalle SET estado = 'ELIMINADO' WHERE cod_prueba_detalle = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")


@NamedNativeQuery(name = "PruebaDetalle.findDatosPrueba", 
query = "select p.cod_prueba_detalle as codPruebaDetalle, p.descripcion_prueba as descripcionPrueba, \r\n"
		+ "p.fecha_inicio as fechaInicio, p.fecha_fin as fechaFin, p.hora as hora,\r\n"
		+ "p.cod_subtipo_prueba as codSubTipoPrueba, p.orden_tipo_prueba as ordenTioPrueba, "
		+ "p.puntaje_minimo as puntajeMinimo, p.puntaje_maximo as puntajeMaximo, p.tiene_puntaje as tienePuntaje \r\n"
		+ "from cbdmq.gen_prueba_detalle p\r\n"
		+ "where p.cod_curso_especializacion = :codCursoEspecializacion\r\n"
		+ "and p.cod_subtipo_prueba = :codSubTipoPrueba ", 
		resultSetMapping = "findDatosPrueba")
@SqlResultSetMapping(name = "findDatosPrueba", classes = @ConstructorResult(targetClass = PruebaDetalleData.class, columns = {
		@ColumnResult(name = "codPruebaDetalle"), 
		@ColumnResult(name = "descripcionPrueba"), 
		@ColumnResult(name = "fechaInicio", type = LocalDate.class),
		@ColumnResult(name = "fechaFin", type = LocalDate.class), 
		@ColumnResult(name = "hora", type = LocalTime.class), 
		@ColumnResult(name = "codSubTipoPrueba"),
		@ColumnResult(name = "ordenTioPrueba"),
		@ColumnResult(name = "puntajeMinimo"),
		@ColumnResult(name = "puntajeMaximo"),
		@ColumnResult(name = "tienePuntaje"),}))

public class PruebaDetalle {

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

	public PruebaDetalle(
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
			Boolean tienePuntaje) {
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
	}
	
	public PruebaDetalle() {
	}

}
