package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_curso_instructor")
@SQLDelete(sql = "UPDATE {h-schema}esp_curso_instructor SET estado = 'ELIMINADO' WHERE cod_instructor_curso = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class EspCursoInstructor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_instructor_curso")
	private Long cod_instructor_curso;
	
	@Column(name = "cod_instructor")
	private Integer cod_instructor;
	
	@Column(name = "cod_curso_especializacion")
	private Integer cod_curso_especializacion;
	
	@Column(name = "cod_tipo_instructor")
	private Integer cod_tipo_instructor;
	
	@Column(name = "fecha_actual")
	private LocalDateTime fecha_actual;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "descripcion")
	private String descripcion;
}
