package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ValidacionRequisitosLista {

	@Id
	private Integer codValidacion;
	
	private Integer codRequisitos;
	
	private String nombreRequisito;
	
	private Boolean estado;
	
	private String observaciones;
	
	private Integer codPostulante;
	
	private Boolean estadoMuestra;
	
	private String observacionMuestra;
}
