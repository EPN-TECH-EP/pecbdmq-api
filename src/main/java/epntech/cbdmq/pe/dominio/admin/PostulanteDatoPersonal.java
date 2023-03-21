package epntech.cbdmq.pe.dominio.admin;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PostulanteDatoPersonal {

	@Id
	private Integer cod_postulante;	
	private String id_postulante;
	private String nombre;
	private String apellido;
	private String cedula;
	private String correo_personal;
	
}
