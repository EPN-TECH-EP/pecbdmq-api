package epntech.cbdmq.pe.dominio.util;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PostulanteDatoPersonal {

	@Id
	private Integer codPostulante;
	private String idPostulante;
	private String nombre;
	private String apellido;
	private String cedula;
	private String correoPersonal;
	
}
