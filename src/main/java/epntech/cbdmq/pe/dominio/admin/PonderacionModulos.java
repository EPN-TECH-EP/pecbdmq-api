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
	private Integer cod_modulo;
	private Integer cod_periodo_academico;
	private Integer cod_componente_nota;
	private Integer cod_tipo_nota;
	private Integer porcentajefinalponderacion;
	private Integer porcentajenotamateria;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechainiciovigencia;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechafinvigencia;
	private String estado;
	private String modulo_desc; 
	private String periodo_academico_desc;
	private String componente_nota_desc;
	private String tipo_nota_desc;
	
	

}
