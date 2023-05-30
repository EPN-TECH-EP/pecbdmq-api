package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AspirantesDatos {

	@Id
	private Integer codPostulante;
	private String idPostulante;
	private Integer codDatosPersonales;
	private String cedula;
	private String nombre;
	private String apellido;
	private Double notaPromedioFinal; 
}

