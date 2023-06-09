package epntech.cbdmq.pe.dominio.util;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class DelegadoUtil {

	
	@Id
	private Integer cod_usuario;
	private Integer cod_datos_personales;
	private String estado; 
	private Integer cedula;
	private String nombre; 
	private String apellido; 
		
	
	
	
}
