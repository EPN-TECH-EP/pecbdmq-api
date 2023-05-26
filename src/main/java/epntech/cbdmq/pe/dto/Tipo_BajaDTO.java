package epntech.cbdmq.pe.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Tipo_BajaDTO implements Serializable{

	private Integer codigo;
	private String baja;
	private String estado;
	
}
