package epntech.cbdmq.pe.dominio.util;

import java.util.Date;

import epntech.cbdmq.pe.dominio.admin.PonderacionModulos;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ComponenteTipo {
	
	@Id
	private String componente;
	private String estado;
	private String tiponota;
	
	
}
