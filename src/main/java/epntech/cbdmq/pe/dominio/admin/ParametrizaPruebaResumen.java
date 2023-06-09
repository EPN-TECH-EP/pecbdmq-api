package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_parametriza_prueba_resumen")

public class ParametrizaPruebaResumen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_parametriza_prueba_resumen")
	private Integer codParametrizaPruebaResumen;
	
	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;
	
	@Column(name = "fecha_inicio")
	private LocalDateTime fechaInicio;
	
	@Column(name = "fecha_fin")
	private LocalDateTime fechaFin;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado")
	private String estado;
	
	
	
	
	
	
	
	
	

}
