package epntech.cbdmq.pe.dominio.admin;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_encuesta_pregunta_respuesta")
public class EncuestaPreguntaRespuesta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@EqualsAndHashCode.Include()
	@Column(name = "cod_encuesta_pregunta_respuesta")
	private Long cod_encuesta_pregunta_respuesta;
	@Column(name = "cod_encuesta_pregunta")
    private Long cod_encuesta_pregunta;
	@Column(name = "cod_catalogo_respuesta")
	private Long cod_catalogo_respuesta;
		
	
}
