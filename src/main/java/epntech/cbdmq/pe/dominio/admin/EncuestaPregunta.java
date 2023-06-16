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
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_encuesta_pregunta")
public class EncuestaPregunta {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_encuesta_pregunta")
	private Long codEncuestaPregunta;
	@Column(name = "cod_encuesta_resumen")
	private Long codEncuestaResumen;
	@Column(name = "cod_catalogo_pregunta")
	private Long codCatalogoPregunta;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_encuesta_pregunta_respuesta",
            joinColumns = @JoinColumn(name = "cod_encuesta_pregunta"),
            inverseJoinColumns = @JoinColumn(name = "cod_catalogo_respuesta")
    )
	private List<CatalogoRespuesta> respuestas = new ArrayList<>();
	
}
