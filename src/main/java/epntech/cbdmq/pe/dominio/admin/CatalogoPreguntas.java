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
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_catalogo_pregunta")
public class CatalogoPreguntas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_catalogo_pregunta")
	private Integer cod_catalogo_pregunta;
	
	@Column(name = "pregunta")
	private String pregunta;
	
	@Column(name = "estado")
	private String estado;
	
	

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_encuesta_pregunta",
            joinColumns = @JoinColumn(name = "cod_catalogo_pregunta"),
            inverseJoinColumns = @JoinColumn(name = "cod_encuesta_pregunta")
    )
	private List<EncuestaPregunta> encuesta = new ArrayList<>();

	/*@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_encuesta_formulario",
            joinColumns = @JoinColumn(name = "cod_catalogo_pregunta"),
            inverseJoinColumns = @JoinColumn(name = "cod_catalogo_respuesta")
    )
	private List<CatalogoRespuesta> respuesta = new ArrayList<>();*/
	
}
