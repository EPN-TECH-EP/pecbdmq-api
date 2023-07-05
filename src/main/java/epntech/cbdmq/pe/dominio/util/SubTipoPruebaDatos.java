package epntech.cbdmq.pe.dominio.util;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class SubTipoPruebaDatos extends SubTipoPrueba {
	
	private String tipoPrueba;
	private Boolean esFisica;
	
	public SubTipoPruebaDatos() {
	}
	
	

}
