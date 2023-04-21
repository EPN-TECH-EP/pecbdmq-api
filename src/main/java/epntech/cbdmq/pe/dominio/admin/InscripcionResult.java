package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
public class InscripcionResult {
	
	@Id
	private Integer cod_datos_personales;
	@Id
	private Integer cod_postulante;

}
