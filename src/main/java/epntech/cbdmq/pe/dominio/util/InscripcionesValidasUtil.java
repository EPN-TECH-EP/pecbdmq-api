package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class InscripcionesValidasUtil {

	@Id
	private String id_postulante;
	
	
}
