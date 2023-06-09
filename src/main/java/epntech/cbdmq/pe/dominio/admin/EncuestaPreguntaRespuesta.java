package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_encuesta_pregunta_respuesta")
public class EncuestaPreguntaRespuesta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@EqualsAndHashCode.Include()
	@Column(name = "cod_encuesta_pregunta_respuesta")
	private Long codEncuestaPreguntaRespuesta;
	@Column(name = "cod_encuesta_pregunta")
    private Long codEncuestaPregunta;
	@Column(name = "cod_catalogo_respuesta")
	private Long codCatalogoRespuesta;
		
	
}
