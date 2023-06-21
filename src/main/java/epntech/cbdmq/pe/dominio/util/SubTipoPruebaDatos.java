package epntech.cbdmq.pe.dominio.util;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import lombok.Data;

@Data
public class SubTipoPruebaDatos extends SubTipoPrueba {
	
	private String tipoPrueba;
	private Boolean esFisica;

}
