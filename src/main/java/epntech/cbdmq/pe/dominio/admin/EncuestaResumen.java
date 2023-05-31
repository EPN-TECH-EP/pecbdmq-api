package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_encuesta_resumen")
public class EncuestaResumen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_encuesta_resumen")
	private Integer cod_encuesta_resumen;
	
	@Column(name = "cod_modulo")
	private Integer cod_modulo;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "fecha_inicio")
	private LocalDateTime fecha_inicio;
	
	@Column(name = "fecha_fin")
	private LocalDateTime fecha_fin;
	
	@Column(name = "estado")
	private String estado;
}
