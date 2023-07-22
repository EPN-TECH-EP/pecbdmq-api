package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteDatos;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudiantesNotaDisciplina;
import jakarta.persistence.*;
import lombok.Data;
@SqlResultSetMapping(name = "EstudiantesNotaDisciplina", classes =
@ConstructorResult(
		targetClass = EstudiantesNotaDisciplina.class,
		columns = {
				@ColumnResult(name = "cod_estudiante", type= Integer.class),
				@ColumnResult(name = "codigo_unico_estudiante", type= String.class),
				@ColumnResult(name = "nombre", type= String.class),
				@ColumnResult(name = "cedula", type= String.class),
				@ColumnResult(name = "cod_paralelo", type = Integer.class),
		}))
@NamedNativeQuery(name = "EstudiantesNotaDisciplina.getEstudiantes",
		query = "select e.cod_estudiante , e.codigo_unico_estudiante, gdp.nombre ||' '|| gdp.apellido as \"nombre\", gdp.cedula , mpa.cod_paralelo \n" +
				"from {h-schema}gen_nota_formacion gnf\n" +
				"left join {h-schema}gen_estudiante_materia_paralelo gemp on gnf.cod_estudiante_materia_paralelo = gemp.cod_estudiante_materia_paralelo  \n" +
				"left join {h-schema}gen_estudiante e on gemp.cod_estudiante = e.cod_estudiante \n" +
				"left join {h-schema}gen_dato_personal gdp on e.cod_datos_personales= gdp.cod_datos_personales \n" +
				"left join {h-schema}gen_materia_paralelo mpa on gemp.cod_materia_paralelo = mpa.cod_materia_paralelo \n" +
				"left join {h-schema}gen_materia_periodo mpe on mpa.cod_materia_periodo = mpe.cod_materia_periodo \n" +
				"where mpe.cod_periodo_academico = :codPA\n" +
				"and upper(e.estado) = 'ACTIVO'\n" +
				"and upper(gnf.estado) = 'ACTIVO'\n" +
				"GROUP BY e.cod_estudiante , e.codigo_unico_estudiante, gdp.nombre, gdp.apellido, gdp.cedula , mpa.cod_paralelo ",resultSetMapping = "EstudiantesNotaDisciplina"
)
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
	
	@Column(name = "puntaje_sancion")
	private Double puntajeSancion;
}
