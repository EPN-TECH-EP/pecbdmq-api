package epntech.cbdmq.pe.dominio.admin;

import java.io.Serializable;

import epntech.cbdmq.pe.dominio.util.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.util.FormacionEstudiante;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import epntech.cbdmq.pe.dominio.util.EstudianteDatos;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_estudiante")
@SQLDelete(sql = "UPDATE {h-schema}gen_estudiante SET estado = 'ELIMINADO' WHERE cod_estudiante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")

@NamedNativeQuery(name = "FormacionEstudiante.findHistorico",
				query = "select gdp.cedula as \"Cedula\"," +
						"gdp.nombre as \"Nombres\"," +
						"gdp.apellido as \"Apellidos\"\n" +
						",ge.codigo_unico_estudiante" +
						",cg.nombre_cargo as \"Cargo\"\n" +
						",rg.nombre_rango as \"Rango\"\n" +
						",gd.nombre_grado as \"Grado\"\n" +
						",gm.nombre_materia" +
						",gdp2.nombre || gdp2.apellido as \"instructor\" \n" +
						",gpon.porcentaje_final_ponderacion " +
						",gnf.nota_minima" +
						",gnf.peso_materia" +
						",gnf.numero_horas" +
						",gnf.nota_materia" +
						",gnf.nota_ponderacion" +
						",gnf.nota_disciplina" +
						" from {h-schema}gen_dato_personal gdp \n" +
						"right join {h-schema}gen_estudiante ge on ge.cod_datos_personales=gdp.cod_datos_personales  \n" +
						"left join {h-schema}gen_cargo cg on cg.cod_cargo=gdp.cod_cargo \n" +
						"left join {h-schema}gen_rango rg on rg.cod_rango=gdp.cod_rango \n" +
						"left join {h-schema}gen_grado gd on gd.cod_grado=gdp.cod_grado \n" +
						"left join {h-schema}gen_materia_estudiante gme on gme.cod_estudiante=ge.cod_estudiante \n" +
						"left join {h-schema}gen_materia gm on gm.cod_materia =gme.cod_materia\n" +
						"left join {h-schema}gen_periodo_academico gpa on gpa.cod_periodo_academico=gme.cod_periodo_academico \n" +
						"left join {h-schema}gen_nota_formacion gnf on gnf.cod_materia=gme.cod_materia and gnf.cod_estudiante=ge.cod_estudiante \n" +
						"left join {h-schema}gen_ponderacion gpon on gpon.cod_periodo_academico =gnf.cod_periodo_academico \n" +
						"left join {h-schema}gen_componente_nota gcnota on gpon.cod_componente_nota=gcnota.cod_componente_nota\n" +
						"LEFT JOIN {h-schema}gen_instructor gins ON gins.cod_instructor=gnf.cod_instructor \n" +
						"left join {h-schema}gen_dato_personal gdp2 on gdp2.cod_datos_personales =gins.cod_datos_personales \n" +
						"where ge.codigo_unico_estudiante = :codUnico"
						,
					resultSetMapping = "FormacionEstudiante"
					)	
					
					@SqlResultSetMapping(name = "FormacionEstudiante", classes = @ConstructorResult(targetClass = FormacionEstudiante.class, columns = {
					@ColumnResult(name = "Cedula"),
					@ColumnResult(name = "Nombres"),
					@ColumnResult(name = "Apellidos"),
					@ColumnResult(name = "codigo_unico_estudiante"),
					@ColumnResult(name = "Cargo"),
					@ColumnResult(name = "Rango"),
					@ColumnResult(name = "Grado"),
					@ColumnResult(name = "nombre_materia"),
					@ColumnResult(name = "instructor"),
					@ColumnResult(name = "porcentaje_final_ponderacion"),
					@ColumnResult(name = "nota_minima"),
					@ColumnResult(name = "peso_materia"),
					@ColumnResult(name = "numero_horas"),
					@ColumnResult(name = "nota_materia"),
					@ColumnResult(name = "nota_ponderacion"),
					@ColumnResult(name = "nota_disciplina"),
					}))

@NamedNativeQuery(name = "EspecializacionEstudiante.findHistorico",
		query ="select \n" +
				"etc.nombre_tipo_curso as \"Tipo Curso\"\n" +
				",ecc.nombre_catalogo_curso as \"NombredelCurso\"\n" +
				",ec.nota_minima as \"NotaMinima\"\n" +
				",ec.fecha_inicio_curso as \"inicio\"\n" +
				",ec.fecha_fin_curso as \"fin\"\n" +
				",ec.estado_proceso as \"estado\"\n" +
				",ene.usuario_mod_nota as \"calificador\" \n" +
				",ene.nota_final_especializacion as \"NotaFinal\"\n" +
				",ene.resultado as \"NotaResultado\"\n" +
				",gti.nombre_tipo_instructor as \"TipoInstructor\"\n" +
				",gdp2.apellido || gdp2.nombre  as \"Instructor\" \n" +
				",ga.nombre_aula as \"Aula\"\n" +
				"from {h-schema}esp_nota_especializacion ene \n" +
				"left join {h-schema}esp_curso ec on ene.cod_curso_especializacion=ec.cod_curso_especializacion\n" +
				"left join {h-schema}gen_estudiante ge on ge.cod_estudiante =ene.cod_estudiante\n" +
				"left join {h-schema}gen_instructor gi on gi.cod_instructor =ec.cod_instructor\n" +
				"left join {h-schema}gen_unidad_gestion gug on gug.cod_unidad_gestion =ec.cod_unidad_gestion  \n" +
				"left join {h-schema}gen_aula ga on ga.cod_aula =ec.cod_aula \n" +
				"left join {h-schema}esp_catalogo_cursos ecc on ecc.cod_catalogo_cursos =ec.cod_catalogo_cursos \n" +
				"left join {h-schema}esp_tipo_curso etc  on etc.cod_tipo_curso =ec.cod_tipo_curso \n" +
				"left join {h-schema}esp_curso_instructor eci on eci.cod_curso_especializacion =ec.cod_curso_especializacion and eci.cod_instructor =gi.cod_instructor \n" +
				"left join {h-schema}gen_tipo_instructor gti on gti.cod_tipo_instructor =eci.cod_tipo_instructor  \n" +
				"left join {h-schema}gen_dato_personal gdp2 on gdp2.cod_datos_personales =gi.cod_datos_personales \n" +
				"where ge.codigo_unico_estudiante = :codUnico\n"
		,
		resultSetMapping = "EspecializacionEstudiante."
)

@SqlResultSetMapping(name = "EspecializacionEstudiante", classes = @ConstructorResult(targetClass = EspecializacionEstudiante.class, columns = {
		@ColumnResult(name = "Tipo Curso"),
		@ColumnResult(name = "NombredelCurso"),
		@ColumnResult(name = "NotaMinima"),
		@ColumnResult(name = "inicio"),
		@ColumnResult(name = "fin"),
		@ColumnResult(name = "estado"),
		@ColumnResult(name = "calificador"),
		@ColumnResult(name = "NotaFinal"),
		@ColumnResult(name = "NotaResultado"),
		@ColumnResult(name = "TipoInstructor"),
		@ColumnResult(name = "Instructor"),
		@ColumnResult(name = "Aula"),
}))





@NamedNativeQuery(name = "EstudianteDatos.findAllEstudiante",
		query = "select e.cod_estudiante, e.grado, e.resultado_estudiante, e.id_estudiante, "
				+ "d.nombre, d.apellido, d.cedula, d.ciudad, d.correo_personal, d.fecha_nacimiento, d.num_telef, d.tipo_sangre, "
				+ "(select s.nombre_zona from {h-schema}gen_estacion_trabajo s where d.cod_estacion = s.cod_estacion and UPPER(s.estado) != 'ELIMINADO') as estacion, "
				+ "(select p.nombre from {h-schema}gen_provincia p where d.cod_provincia = p.cod_provincia and UPPER(p.estado) != 'ELIMINADO') as provincia, "
				+ "(select u.unidad_gestion from {h-schema}gen_unidad_gestion u where d.cod_unidad_gestion = u.cod_unidad_gestion and UPPER(u.estado) != 'ELIMINADO') as unidad_gestion, "
				+ "e.estado "
				+ "from {h-schema}gen_estudiante e, {h-schema}gen_dato_personal d, {h-schema}gen_modulo m "
				+ "where e.cod_datos_personales = d.cod_datos_personales "
				+ "and e.cod_modulo = m.cod_modulo "
				+ "and UPPER(e.estado) != 'ELIMINADO' "
				+ "and UPPER(d.estado) != 'ELIMINADO' "
				+ "and UPPER(m.estado) != 'ELIMINADO' ",
		resultSetMapping = "EstudianteDatos"
)

@SqlResultSetMapping(name = "EstudianteDatos", classes = @ConstructorResult(targetClass = EstudianteDatos.class, columns = {
		@ColumnResult(name = "cod_estudiante"),
		@ColumnResult(name = "grado"),
		@ColumnResult(name = "resultado_estudiante"),
		@ColumnResult(name = "id_estudiante"),
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"),
		@ColumnResult(name = "cedula"),
		@ColumnResult(name = "ciudad"),
		@ColumnResult(name = "correo_personal"),
		@ColumnResult(name = "fecha_nacimiento"),
		@ColumnResult(name = "num_telef"),
		@ColumnResult(name = "tipo_sangre"),
		@ColumnResult(name = "estacion"),
		@ColumnResult(name = "provincia"),
		@ColumnResult(name = "unidad_gestion"),
		@ColumnResult(name = "estado")
}))

public class Estudiante implements Serializable  {

	

	private static final long serialVersionUID = 1935713547872014437L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer codEstudiante;
	
	protected Integer codDatosPersonales;
	
	protected Integer codModulo;
	
	protected String grado;
	
	protected String resultadoEstudiante;
	
	protected String idEstudiante;
	
	protected String estado;
	
	public Estudiante() {
	}
	
	public Estudiante(Integer codEstudiante, Integer codDatosPersonales, Integer codModulo, String grado,
			String resultadoEstudiante, String idEstudiante, String estado) {
		super();
		this.codEstudiante = codEstudiante;
		this.codDatosPersonales = codDatosPersonales;
		this.codModulo = codModulo;
		this.grado = grado;
		this.resultadoEstudiante = resultadoEstudiante;
		this.idEstudiante = idEstudiante;
		this.estado = estado;
	}
	
}
