package epntech.cbdmq.pe.dominio.admin;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_encuesta_resultado")
@SQLDelete(sql = "UPDATE {h-schema}gen_encuesta_resultado SET estado = 'ELIMINADO' WHERE cod_encuesta_pregunta_respuesta = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class EncuestaResultado {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_encuesta_pregunta_respuesta")
	private Long cod_encuesta_pregunta_respuesta;
	
	@Column(name = "resultado")
	private Boolean resultado;
	
	@Column(name = "cod_encuesta_resultado")
	private Long cod_encuesta_resultado;
	
	@Column(name = "estado")
	private String estado;
	
	  @OneToMany(mappedBy = "cod_encuesta_pregunta_respuesta", cascade = CascadeType.ALL)
	    private List<EncuestaPreguntaRespuesta> encuestapreguntarespuesta = new ArrayList<>();
	
	/*@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_encuesta_pregunta_respuesta",
            joinColumns = @JoinColumn(name = "cod_encuesta_pregunta_respuesta"),
            inverseJoinColumns = @JoinColumn(name = "cod_encuesta_pregunta")
    )
	private List<EncuestaPreguntaRespuesta> preguntas = new ArrayList<>();**/
	
	
	
}
