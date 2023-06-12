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
	private Integer codCursoEspecializacion;
	
	@Column(name = "cod_instructor")
	private Integer codInstructor;
	
	@Column(name = "cod_unidad_gestion")
	private Integer codUnidadGestion;
	
	/*@Column(name = "cod_estudiante")
	private Integer cod_estudiante;*/
	
	@Column(name = "cod_aula")
	private Integer codAula;
	
	@Column(name = "numero_cupo")
	private Integer numeroCupo;
	
	@Column(name = "adjunto_planificacion")
	private String adjuntoPlanificacion;
	
	@Column(name = "fecha_inicio_curso")
	private LocalDateTime fechaInicioCurso;
	@Column(name = "fecha_fin_curso")
	private LocalDateTime fechaFinCurso;
	@Column(name = "fecha_inicio_carga_nota")
	private LocalDateTime fechaInicioCargaNota;
	@Column(name = "fecha_fin_carga_nota")
	private LocalDateTime fechaFinCargaNota;
	@Column(name = "nota_minima")
	private BigDecimal notaMinima;
	
	@Column(name = "aprueba_creacion_curso")
	private Boolean aprueba;
	
	@Column(name = "estado_proceso")
	private String estadoProceso;
	
	@Column(name = "cod_catalogo_cursos")
	private Integer codCatalogoCurso;
	
	@Column(name = "estado")
	private String estado;

	
	@Column(name = "cod_tipo_curso")
	private Integer codTipoCurso;


	@Column(name = "resultado")
	private boolean resultado;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "gen_paralelo_curso", joinColumns = @JoinColumn(name = "cod_curso_especializacion"), 
	inverseJoinColumns = @JoinColumn(name = "cod_paralelo"))
	private List<Paralelo> paralelos = new ArrayList<>();
	
	@OneToMany(mappedBy="codCatalogoCursos")
    private List<CatalogoCurso> nombreCurso;
}
