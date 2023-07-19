package epntech.cbdmq.pe.dominio.util;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ResultadosPruebasDatos {

	@Id
	private Integer codPostulante;
	private String idPostulante;
	private String cedula;
	private String nombre;
	private String apellido;
	private String correoPersonal;

	private Integer resultado;
	private String resultadoTiempo;
	private Boolean cumplePrueba;
	private BigDecimal notaPromedioFinal;

}
