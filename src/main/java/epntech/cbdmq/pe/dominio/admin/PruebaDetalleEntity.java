package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.hibernate.annotations.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonFormat;

import epntech.cbdmq.pe.dominio.util.PruebaDetalleData;
//import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
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
@Entity
@Table(name = "gen_prueba_detalle")

@NamedNativeQuery(name = "PruebaDetalleEntity.findDatosxPrueba", 
query = "select p.cod_prueba_detalle as codPruebaDetalle, p.descripcion_prueba as descripcionPrueba, \r\n"
		+ "p.fecha_inicio as fechaInicio, p.fecha_fin as fechaFin, p.hora as hora,\r\n"
		+ "p.cod_subtipo_prueba as codSubTipoPrueba, p.orden_tipo_prueba as ordenTioPrueba, "
		+ "p.puntaje_minimo as puntajeMinimo, p.puntaje_maximo as puntajeMaximo, p.tiene_puntaje as tienePuntaje \r\n"
		+ "from cbdmq.gen_prueba_detalle p\r\n"
		+ "where p.cod_curso_especializacion = :codCursoEspecializacion\r\n"
		+ "and p.cod_subtipo_prueba = :codSubTipoPrueba ", 
		resultSetMapping = "findDatosxPrueba")
@SqlResultSetMapping(name = "findDatosxPrueba", classes = @ConstructorResult(targetClass = PruebaDetalleData.class, columns = {
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

public class PruebaDetalleEntity {

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

}
