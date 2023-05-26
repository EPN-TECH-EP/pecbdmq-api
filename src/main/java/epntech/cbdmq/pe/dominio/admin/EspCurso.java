package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

@Data

@Entity(name = "esp_curso")
@Table(name = "esp_curso")
@SQLDelete(sql = "UPDATE {h-schema}esp_curso SET estado = 'ELIMINADO' WHERE cod_curso_especializacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class EspCurso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_especializacion")
	private Integer cod_curso_especializacion;
	
	@Column(name = "cod_instructor")
	private Integer cod_instructor;
	
	@Column(name = "cod_unidad_gestion")
	private Integer cod_unidad_gestion;
	
	/*@Column(name = "cod_estudiante")
	private Integer cod_estudiante;*/
	
	@Column(name = "cod_aula")
	private Integer cod_aula;
	
	@Column(name = "numero_cupo")
	private Integer numero_cupo;
	
	@Column(name = "adjunto_planificacion")
	private String adjunto_planificacion;
	
	@Column(name = "fecha_inicio_curso")
	private LocalDateTime fechainiciocurso;
	@Column(name = "fecha_fin_curso")
	private LocalDateTime fechafincurso;
	@Column(name = "fecha_inicio_carga_nota")
	private LocalDateTime fechainiciocarganota;
	@Column(name = "fecha_fin_carga_nota")
	private LocalDateTime fechafincarganota;
	@Column(name = "nota_minima")
	private BigDecimal nota_minima;
	
	@Column(name = "aprueba_creacion_curso")
	private Boolean aprueba;
	
	@Column(name = "estado_proceso")
	private String estado_proceso;
	
	@Column(name = "cod_catalogo_cursos")
	private Integer cod_catalogo_curso;
	
	@Column(name = "estado")
	private String estado;

	
	@Column(name = "cod_tipo_curso")
	private Integer cod_tipo_curso;


	@Column(name = "resultado")
	private boolean resultado;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "gen_paralelo_curso", joinColumns = @JoinColumn(name = "cod_curso_especializacion"), 
	inverseJoinColumns = @JoinColumn(name = "cod_paralelo"))
	private List<Paralelo> paralelos = new ArrayList<>();
	
	@OneToMany(mappedBy="cod_catalogo_cursos")
    private List<CatalogoCurso> NombreCurso;
}
