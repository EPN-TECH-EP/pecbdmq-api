
package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ModuloEstadosData {

	@Id
	private Integer codigo;
	
	private String modulo;
	
	private String estadoCatalogo;
	
	private Integer orden; 
	
	private String estado;
	
}

