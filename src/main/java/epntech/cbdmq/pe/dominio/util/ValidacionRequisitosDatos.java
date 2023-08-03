package epntech.cbdmq.pe.dominio.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidacionRequisitosDatos {

	private Long codValidacionRequisito;
	private Long codRequisito;
	private Long codInscripcion;
	private String nombreRequisito;
	private String observacion;
	private Boolean estado;
	
}
