package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_curso")
public class CursoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;
	
	@Column(name = "cod_aula")
	private Integer codAula;
	
	@Column(name = "numero_cupo")
	private Integer numeroCupo;
	
	@Column(name = "fecha_inicio_curso")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicioCurso;
	
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
	
	@Column(name = "tiene_modulos")
	private Boolean tieneModulos;
}
