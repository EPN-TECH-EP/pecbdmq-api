package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_instructor_periodo")
public class InstructorPeriodo {

	
	@Id
	@Column(name = "cod_instructor")
	private Integer codInstructor;
	
	
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	
}
