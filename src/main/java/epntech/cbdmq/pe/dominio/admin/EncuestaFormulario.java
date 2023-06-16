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
@Table(name = "gen_encuesta_formulario")
public class EncuestaFormulario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@EqualsAndHashCode.Include()
	@Column(name = "cod_encuesta_formulario")
	private Integer codEncuestaFormulario;
	@Column(name = "cod_encuesta_resumen")
	private Integer codEncuestaResumen;
	@Column(name = "cod_catalogo_pregunta")
	private Integer codCatalogoPregunta;
	/*@Column(name = "cod_catalogo_respuesta")
	private Integer cod_catalogo_respuesta;
	
	
	/*@OneToMany(mappedBy="cod_catalogo_respuesta")
    private List<CatalogoRespuesta> respuesta;*/
	
	/*@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_encuesta_formulario",
            joinColumns = @JoinColumn(name = "cod_encuesta_formulario"),
            inverseJoinColumns = @JoinColumn(name = "cod_catalogo_respuesta")
    )
	private List<CatalogoRespuesta> respuesta = new ArrayList<>();*/
	
	
	
	
}
