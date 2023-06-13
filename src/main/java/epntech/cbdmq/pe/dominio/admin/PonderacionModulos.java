package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class PonderacionModulos {
	
	@Id
	private Integer codPonderacion;
	private Integer codModulo;
	private Integer codPeriodoAcademico;
	private Integer codComponenteNota;
	//private Integer codTipoNota;
	private BigDecimal porcentajeFinalPonderacion;
	/*private BigDecimal porcentajeNotaMateria;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicioVigencia;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaFinVigencia;*/
	private String estado;
	private String moduloDesc; 
	private String periodoAcademicoDesc;
	private String componenteNotaDesc;
	//private String tipoNotaDesc;

/*	@Id
	/*private Integer cod_ponderacion;
	private Integer cod_modulo;
	private Integer cod_periodo_academico;
	private Integer cod_componente_nota;
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
	//private String periodo_academico_desc;*/
	
	

}
