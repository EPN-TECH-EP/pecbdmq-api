package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;

import epntech.cbdmq.pe.dominio.admin.Requisito;
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
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_curso")
@SQLDelete(sql = "UPDATE {h-schema}esp_curso SET estado = 'ELIMINADO' WHERE cod_curso_especializacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
@Validated
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;

	@NotNull(message = "El atributo 'codAula' es obligatorio")
	@Column(name = "cod_aula")
	private Integer codAula;

	@NotNull(message = "El atributo 'numeroCupo' es obligatorio")
	@Column(name = "numero_cupo")
	private Integer numeroCupo;

	@NotNull(message = "El atributo 'fechaInicioCurso' es obligatorio")
	@Column(name = "fecha_inicio_curso")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicioCurso;

	@NotNull(message = "El atributo 'fechaFinCurso' es obligatorio")
	@Column(name = "fecha_fin_curso")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaFinCurso;
	
	@Column(name = "fecha_inicio_carga_nota")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicioCargaNota;
	
	@Column(name = "fecha_fin_carga_nota")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaFinCargaNota;
	
	@Column(name = "nota_minima")
	private Double notaMinima;
	
	@Column(name = "aprueba_creacion_curso")
	private Boolean apruebaCreacionCurso;
	
	@Column(name = "cod_catalogo_cursos")
	private Long codCatalogoCursos;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cod_tipo_curso")
	private Integer codTipoCurso;
	
	@Column(name = "email_notificacion")
	private String emailNotificacion;

	@NotNull(message = "El atributo 'tieneModulos' es obligatorio")
	@Column(name = "tiene_modulos")
	private Boolean tieneModulos;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "esp_curso_documento", joinColumns = @JoinColumn(name = "cod_curso_especializacion"), inverseJoinColumns = @JoinColumn(name = "cod_documento"))
	private Set<DocumentoCurso> documentos = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "esp_curso_requisito", joinColumns = @JoinColumn(name = "cod_curso_especializacion"), inverseJoinColumns = @JoinColumn(name = "cod_requisito"))
	private Set<Requisito> requisitos = new HashSet<>();
}