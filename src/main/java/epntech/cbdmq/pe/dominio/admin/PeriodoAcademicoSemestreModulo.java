package epntech.cbdmq.pe.dominio.admin;

import java.util.Date;


import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Data
@Entity
public class PeriodoAcademicoSemestreModulo {
	
	@Id
	private Integer codigo;
	
	private String descripcion;
	
	private String estado;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	private String estadoProceso;

}
