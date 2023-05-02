package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ValidacionRequisitosLista {

	@Id
	private Integer cod_validacion_requisitos;
	
	private Integer cod_requisitos;
	
	private String nombre_requisito;
	
	private Boolean estado;
	
	private String observaciones;
}
