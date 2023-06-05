package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;
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
@Table(name = "gen_encuesta_resumen")
public class EncuestaResumen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_encuesta_resumen")
	private Integer cod_encuesta_resumen;
	
	/*@Column(name = "cod_modulo")
	private Integer cod_modulo;*/
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "fecha_inicio")
	private LocalDateTime fecha_inicio;
	
	@Column(name = "fecha_fin")
	private LocalDateTime fecha_fin;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cod_periodo_academico")
	private Long cod_periodo_academico;
	
	@Column(name = "cod_curso_especializacion")
	private Long cod_curso_especializacion;
	
	@Column(name = "cod_semestre")
	private Long cod_semestre;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_encuesta_pregunta",
            joinColumns = @JoinColumn(name = "cod_encuesta_resumen"),
            inverseJoinColumns = @JoinColumn(name = "cod_catalogo_pregunta")
    )
	private List<CatalogoPreguntas> preguntas = new ArrayList<>();
}
