package epntech.cbdmq.pe.dominio.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PruebaDetalleData {

	@Id
	private Long codPruebaDetalle;
	private String descripcionPrueba;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private LocalTime hora;
	private Long codSubTipoPrueba;
	private Integer ordenTioPrueba;
	private BigDecimal puntajeMinimo;
	private BigDecimal puntajeMaximo;
	private Boolean tienePuntaje;
}
