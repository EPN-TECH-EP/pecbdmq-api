package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class InscritosValidos {
 
	@Id
	private Integer codInscripcion;
	private String cedula;
	private String nombre;
	private String apellido;
	private String correoPersonal;
}
