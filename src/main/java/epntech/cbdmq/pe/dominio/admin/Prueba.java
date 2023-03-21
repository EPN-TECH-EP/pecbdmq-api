package epntech.cbdmq.pe.dominio.admin;



import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "gen_prueba")
@Table(name = "gen_prueba")

public class Prueba {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_prueba")
	private Integer cod_prueba;
	
	@Column(name = "cod_tipo_prueba")
	private Integer cod_tipo_prueba;
	
	@Column(name = "nombre_prueba")
	private String prueba;
	
	@Column(name = "descripcion_prueba")
	private String descripcion_prueba;
	
	@Column(name = "fecha_inicio")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fecha_inicio;
	
	@Column(name = "fecha_fin")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fecha_fin;
	
	@Column(name = "hora")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime hora;
	
	
}
