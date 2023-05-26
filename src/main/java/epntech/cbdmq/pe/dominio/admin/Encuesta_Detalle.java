package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "gen_encuesta_detalle")
public class Encuesta_Detalle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_encuesta_detalle")
	private Integer cod_encuesta_detalle;
	@Column(name = "cod_encuesta_resumen")
	private Integer cod_encuesta_resumen;
	@Column(name = "cod_catalogo_pregunta")
	private Integer cod_catalogo_pregunta;
	@Column(name = "cod_catalogo_respuesta")
	private Integer cod_catalogo_respuesta; 
	
	
}
