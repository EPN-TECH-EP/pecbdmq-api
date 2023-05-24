package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private String tiponota;
	private String modulo;
	//private String etiqueta;
	private String periodo;
	
	private Date  fechainicio;
	private Date fechafin;
	//private BigDecimal porcentaje_final_ponderacion;
	
	
	private String estado;
	//private String modulo_desc; 
	//private String periodo_academico_desc;
	
	

}
