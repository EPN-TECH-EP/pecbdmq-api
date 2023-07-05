package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalTime;
import java.util.Date;

import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.Query;

import com.fasterxml.jackson.annotation.JsonFormat;

import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
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

/*@NamedNativeQuery(name = "PruebaDetalle.listarTodosConDatosSubTipoPrueba", query = "select\r\n"
		+ "	gpd.cod_prueba_detalle,\r\n"
		+ "	gpd.descripcion_prueba,\r\n"
		+ "	gpd.fecha_inicio,\r\n"
		+ "	gpd.fecha_fin,\r\n"
		+ "	gpd.hora,\r\n"
		+ "	gpd.estado,\r\n"
		+ "	gpd.cod_periodo_academico,\r\n"
		+ "	gpd.cod_curso_especializacion,\r\n"
		+ "	gpd.cod_subtipo_prueba,\r\n"
		+ "	gpd.orden_tipo_prueba,\r\n"
		+ "	gpd.puntaje_minimo,\r\n"
		+ "	gpd.puntaje_maximo,\r\n"
		+ "	gpd.tiene_puntaje,\r\n"
		+ "	gsp.nombre as subTipoPruebaNombre,\r\n"
		+ "	gtp.tipo_prueba as tipoPruebaNombre \r\n"
		+ "from\r\n"
		+ "	cbdmq.gen_prueba_detalle gpd,\r\n"
		+ "	cbdmq.gen_subtipo_prueba gsp,\r\n"
		+ "	cbdmq.gen_tipo_prueba gtp\r\n"
		+ "where\r\n"
		+ "	gpd.cod_subtipo_prueba = gsp.cod_subtipo_prueba"
		+ " and gsp.cod_tipo_prueba = gtp.cod_tipo_prueba", resultSetMapping = "PruebaDetalleDatos")

@SqlResultSetMapping(name = "PruebaDetalleDatos", classes = @ConstructorResult(targetClass = PruebaDetalle.class, columns = {
		@ColumnResult(name = "cod_prueba_detalle"),
		@ColumnResult(name = "descripcion_prueba"),
		@ColumnResult(name = "fecha_inicio"),
		@ColumnResult(name = "fecha_fin"),
		@ColumnResult(name = "hora"),
		@ColumnResult(name = "estado"),
		@ColumnResult(name = "cod_periodo_academico"),
		@ColumnResult(name = "cod_curso_especializacion"),
		@ColumnResult(name = "cod_subtipo_prueba"),
		@ColumnResult(name = "orden_tipo_prueba"),
		@ColumnResult(name = "puntaje_minimo"),
		@ColumnResult(name = "puntaje_maximo"),
		@ColumnResult(name = "tiene_puntaje"),
		@ColumnResult(name = "subTipoPruebaNombre"),
		@ColumnResult(name = "tipoPruebaNombre")
}))*/

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
