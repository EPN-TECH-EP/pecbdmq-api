package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class NotasDatosFormacion {

	@Id
	private String cedula;
	private String nombre;
	private String apellido;
	private String nombreMateria;
	private Double notaMateria;
	private Double notaDisciplina;
	private Double notaSupletorio;
	
}
