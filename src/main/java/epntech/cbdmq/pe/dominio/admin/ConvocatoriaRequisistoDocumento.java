package epntech.cbdmq.pe.dominio.admin;


import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class ConvocatoriaRequisistoDocumento {
		
	@Id	
	private String codigo_unico_convocatoria;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fecha_inicio_convocatoria;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fecha_fin_convocatoria;
	
	@JsonFormat(pattern = "HH:mm")
	private LocalTime hora_inicio_convocatoria;
	
	
	@JsonFormat(pattern = "HH:mm")
	private	LocalTime hora_fin_convocatoria;
	
	private Integer	cupo_hombres;
	
	private Integer cupo_mujeres;
	
	private String nombre_documento;
	
	private String ruta;
	
	private String nombre_requisito;
	
	private String descripcion_requisito; 
	
}
