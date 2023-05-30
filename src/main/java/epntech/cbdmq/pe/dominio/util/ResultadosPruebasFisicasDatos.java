package epntech.cbdmq.pe.dominio.util;


import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ResultadosPruebasFisicasDatos {

	@Id
	private String idPostulante;
	private String cedula;
	private String nombre;
	private String apellido;
	private Integer resultado;
	private Time resultadoTiempo;
	private Double notaPromedioFinal;	
}
