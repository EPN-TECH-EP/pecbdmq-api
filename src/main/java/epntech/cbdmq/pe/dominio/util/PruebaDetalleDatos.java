package epntech.cbdmq.pe.dominio.util;

import java.time.LocalTime;
import java.util.Date;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class PruebaDetalleDatos extends PruebaDetalle {

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
