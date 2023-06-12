package epntech.cbdmq.pe.dominio.admin;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class PonderacionModulos {

	@Id
	/*private Integer cod_ponderacion;
	private Integer cod_modulo;
	private Integer cod_periodo_academico;
	private Integer cod_componente_nota;*/
	private Integer porcentaje;
	private String tipoNota;
	private String modulo;
	//private String etiqueta;
	private String periodo;
	
	private Date fechaInicio;
	private Date fechaFin;
	//private BigDecimal porcentaje_final_ponderacion;
	
	
	private String estado;
	//private String modulo_desc; 
	//private String periodo_academico_desc;
	
	

}
