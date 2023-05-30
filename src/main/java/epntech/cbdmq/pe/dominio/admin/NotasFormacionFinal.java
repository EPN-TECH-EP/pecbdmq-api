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
@Table(name = "gen_nota_formacion_final")
public class NotasFormacionFinal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_nota_formacion_final")
	private Integer codNotaFormacionFinal;

	@Column(name = "cod_estudiante")
	private Integer codEstudiante;
	
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	
	@Column(name = "promedio_disciplina_instructor")
	private Double promedioDisciplinaInstructor;
	
	@Column(name = "promedio_disciplina_oficial_semana")
	private Double promedioDisciplinaOficialSemana;
	
	@Column(name = "promedio_academico")
	private Double promedioAcademico;
	
	@Column(name = "nota_final")
	private Double notaFinal;
	
	@Column(name = "realizo_encuesta")
	private Boolean realizoEncuesta;
	
	@Column(name = "promedio_disciplina_final")
	private Double promedioDisciplinaFinal;
	
	@Column(name = "ponderacion_academica")
	private Double ponderacionAcademica;
	
	@Column(name = "ponderacion_disciplina")
	private Double ponderacionDisciplina;
}

