package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_instructor_materia")
public class InstructorMateria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_instructor_materia")
	private Integer codInstructorMateria;
	
	@Column(name = "cod_instructor")
	private Integer codInstructor;
	
	@Column(name = "cod_materia")
	private Integer codMateria;
	
	@Column(name = "es_coordinador")
	private Boolean esCoordinador;
	
	@Column(name = "es_asistente")
	private Boolean esAsistente;
	
}
