package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_curso_instructor")
@SQLDelete(sql = "UPDATE {h-schema}esp_curso_instructor SET estado = 'ELIMINADO' WHERE cod_instructor_curso = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class CursoInstructor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_instructor_curso")
	private Long codInstructorCurso;

	@NotNull(message = "El atributo 'codInstructor' es obligatorio")
	@Column(name = "cod_instructor")
	private Integer codInstructor;

	@NotNull(message = "El atributo 'codCursoEspecializacion' es obligatorio")
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;

	@NotNull(message = "El atributo 'codTipoInstructor' es obligatorio")
	@Column(name = "cod_tipo_instructor")
	private Integer codTipoInstructor;
	
	@Column(name = "fecha_actual")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaActual;

	@Column(name = "estado")
	private String estado;

	@Column(name = "descripcion")
	private String descripcion;
}