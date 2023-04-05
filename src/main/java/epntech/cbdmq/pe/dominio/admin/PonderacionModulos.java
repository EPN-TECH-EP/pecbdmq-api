package epntech.cbdmq.pe.dominio.admin;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class PonderacionModulos {

	@Id
	private Integer cod_ponderacion;
	
	private String cod_modulo;
	
	private String cod_periodo_academico;
	
	private String cod_componente_nota;
	
	private String cod_tipo_nota;
	
	private Integer porcentajefinalponderacion;
	
	private Integer porcentajenotamateria;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechainiciovigencia;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechafinvigencia;
	
	private String estado;
	
	
	
	
	
}
