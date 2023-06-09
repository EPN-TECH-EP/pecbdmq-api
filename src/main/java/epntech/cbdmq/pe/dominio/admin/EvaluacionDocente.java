package epntech.cbdmq.pe.dominio.admin;

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

@Entity(name = "esp_evaluacion_docente")
@Table(name = "esp_evaluacion_docente")
@SQLDelete(sql = "UPDATE {h-schema}esp_evaluacion_docente SET estado = 'ELIMINADO' WHERE cod_evaluacion_docente = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class EvaluacionDocente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_evaluacion_docente")
	private Integer codEvaluacionDocente;
	@Column(name = "cod_instructor")
	private Integer codInstructor;
	@Column(name = "pregunta")
	private String nombre;
	@Column(name = "estado")
	private String estado;
	
	
}
