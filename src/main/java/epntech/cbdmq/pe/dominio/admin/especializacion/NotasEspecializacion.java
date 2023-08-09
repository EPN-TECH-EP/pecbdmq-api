package epntech.cbdmq.pe.dominio.admin.especializacion;

import epntech.cbdmq.pe.dto.especializacion.NotasEspecializacionDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "esp_nota_especializacion")

@NamedNativeQuery(name = "NotasEspecializacion.findNotasCurso",
		query = "select eap.cod_estudiante as codEstudiante, eap.codigo_unico_estudiante as codigoUnicoEstudiante, " +
				"eap.cedula, eap.nombre, eap.apellido, eap.correo_personal as correoPersonal, " +
				"eap.correo_institucional as correoInstitucional, ei.cod_curso_especializacion as codCursoEspecializacion, " +
				"ene.nota_final_especializacion as notaFinalEspecializacion, ene.nota_supletorio as notaSupletorio, " +
				"ene.cod_nota_especializacion as codNotaEspecializacion, ei.cod_inscripcion codInscripcion, " +
				"ene.cod_instructor as codInstructor " +
				"from cbdmq.get_estudiantes_aprobados_pruebas_curso(:codCurso) eap " +
				"left join cbdmq.esp_inscripcion ei on eap.cod_estudiante = ei.cod_estudiante " +
				"left join cbdmq.esp_nota_especializacion ene on ei.cod_inscripcion = ene.cod_inscripcion",
		resultSetMapping = "findNotasCurso")
@SqlResultSetMapping(name = "findNotasCurso", classes = @ConstructorResult(targetClass = NotasEspecializacionDTO.class, columns = {
		@ColumnResult(name = "codEstudiante", type = Integer.class),
		@ColumnResult(name = "codCursoEspecializacion", type = Integer.class),
		@ColumnResult(name = "codNotaEspecializacion", type = Integer.class),
		@ColumnResult(name = "codigoUnicoEstudiante", type = String.class),
		@ColumnResult(name = "cedula", type = String.class),
		@ColumnResult(name = "nombre", type = String.class),
		@ColumnResult(name = "apellido", type = String.class),
		@ColumnResult(name = "correoPersonal", type = String.class),
		@ColumnResult(name = "correoInstitucional", type = String.class),
		@ColumnResult(name = "notaFinalEspecializacion", type = Double.class),
		@ColumnResult(name = "notaSupletorio", type = Double.class),
		@ColumnResult(name = "codInscripcion", type = Integer.class),
		@ColumnResult(name = "codInstructor", type = Integer.class),}))

@NamedNativeQuery(name = "NotasEspecializacion.findAprobadosCurso",
		query = "select ge.cod_estudiante as codEstudiante, ge.codigo_unico_estudiante as codigoUnicoEstudiante, " +
				"gdp.cedula, gdp.nombre, gdp.apellido, gdp.correo_personal as correoPersonal, " +
				"gdp.correo_institucional as correoInstitucional, ei.cod_curso_especializacion as codCursoEspecializacion, " +
				"ene.nota_final_especializacion as notaFinalEspecializacion, ene.nota_supletorio as notaSupletorio, " +
				"ene.cod_nota_especializacion as codNotaEspecializacion, ei.cod_inscripcion codInscripcion, " +
				"ene.cod_instructor as codInstructor, ec.nota_minima as notaMinima " +
				"from cbdmq.esp_nota_especializacion ene, cbdmq.esp_inscripcion ei, " +
				"cbdmq.esp_curso ec, cbdmq.gen_estudiante ge, cbdmq.gen_dato_personal gdp " +
				"where ene.cod_inscripcion = ei.cod_inscripcion " +
				"and ei.cod_curso_especializacion = ec.cod_curso_especializacion " +
				"and ei.cod_estudiante = ge.cod_estudiante " +
				"and ge.cod_datos_personales = gdp.cod_datos_personales " +
				"and ec.cod_curso_especializacion = :codCurso " +
				"and (ene.nota_final_especializacion >= ec.nota_minima or ene.nota_supletorio >= ec.nota_minima)",
		resultSetMapping = "findAprobadosCurso")
@SqlResultSetMapping(name = "findAprobadosCurso", classes = @ConstructorResult(targetClass = NotasEspecializacionDTO.class, columns = {
		@ColumnResult(name = "codEstudiante", type = Integer.class),
		@ColumnResult(name = "codCursoEspecializacion", type = Integer.class),
		@ColumnResult(name = "codNotaEspecializacion", type = Integer.class),
		@ColumnResult(name = "codigoUnicoEstudiante", type = String.class),
		@ColumnResult(name = "cedula", type = String.class),
		@ColumnResult(name = "nombre", type = String.class),
		@ColumnResult(name = "apellido", type = String.class),
		@ColumnResult(name = "correoPersonal", type = String.class),
		@ColumnResult(name = "correoInstitucional", type = String.class),
		@ColumnResult(name = "notaFinalEspecializacion", type = Double.class),
		@ColumnResult(name = "notaSupletorio", type = Double.class),
		@ColumnResult(name = "codInscripcion", type = Integer.class),
		@ColumnResult(name = "codInstructor", type = Integer.class),
		@ColumnResult(name = "notaMinima", type = Double.class),}))

public class NotasEspecializacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_nota_especializacion")
	private Integer codNotaEspecializacion;

	@Column(name = "cod_instructor")
	private Integer codInstructor;

	@Column(name = "fecha_crea_nota")
	private LocalDate fechaCreaNota;

	@Column(name = "hora_crea_nota")
	private LocalTime horaCreaNota;

	@Column(name = "usuario_mod_nota")
	private String usuarioModNota;

	@Column(name = "fecha_mod_nota")
	private LocalDate fechaModNota;

	@Column(name = "hora_mod_nota")
	private LocalTime horaModNota;

	@Column(name = "resultado")
	private Boolean resultado;

	@Column(name = "nota_final_especializacion")
	private Double notaFinalEspecializacion;

	@Column(name = "realizo_encuesta")
	private Boolean realizoEncuesta;

	@Column(name = "nota_supletorio")
	private Double notaSupletorio;

	@Column(name = "cod_inscripcion")
	private Integer codInscripcion;

}
	

	

	

