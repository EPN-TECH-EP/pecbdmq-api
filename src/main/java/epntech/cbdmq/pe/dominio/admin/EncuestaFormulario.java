package epntech.cbdmq.pe.dominio.admin;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_encuesta_formulario")
public class EncuestaFormulario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_encuesta_formulario")
	private Integer cod_encuesta_formulario;
	@Column(name = "cod_encuesta_resumen")
	private Integer cod_encuesta_resumen;
	@Column(name = "cod_catalogo_pregunta")
	private Integer cod_catalogo_pregunta;
	/*@Column(name = "cod_catalogo_respuesta")
	private Integer cod_catalogo_respuesta; */
	
	
	@OneToMany(mappedBy="cod_catalogo_respuesta")
    private List<CatalogoRespuesta> respuesta;
	
	
	
	
}
