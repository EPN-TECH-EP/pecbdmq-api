package epntech.cbdmq.pe.dominio.fichaPersonal;

import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionEstudiante;
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
				query = "\tselect gdp.cedula as \"Cedula\"\n" +
						"\t,gdp.nombre as \"Nombres\"\n" +
						"\t,gdp.apellido as \"Apellidos\"\n" +
						"\t,ge.codigo_unico_estudiante\n" +
						"\t,cg.nombre_cargo as Cargo\n" +
						"\t,rg.nombre_rango as Rango\n" +
						"\t,gd.nombre_grado as Grado\n" +
						"\t,gm.nombre_materia\n" +
						"\t,gdp2.nombre || gdp2.apellido as \"instructor\" \n" +
						"\t,gnf.nota_minima \n" +
						"\t,gnf.peso_materia \n" +
						"\t,gnf.numero_horas \n" +
						"\t,gnf.nota_materia \n" +
						"\t,gnf.nota_ponderacion \n" +
						"\t,gnf.nota_disciplina \n" +
						"\tfrom cbdmq.gen_nota_formacion gnf  \n" +
						"\tleft join cbdmq.gen_estudiante ge on ge.cod_estudiante =gnf.cod_estudiante  \n" +
						"\tleft join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales =ge.cod_datos_personales\n" +
						"\tleft join cbdmq.gen_materia gm on gm.cod_materia =gnf.cod_materia\n" +
						"\tLEFT JOIN cbdmq.gen_instructor gins ON gins.cod_instructor=gnf.cod_instructor\n" +
						"\tleft join cbdmq.gen_periodo_academico gpa on gpa.cod_periodo_academico=gnf.cod_periodo_academico\n" +
						"\tleft join cbdmq.gen_cargo cg on cg.cod_cargo=gdp.cod_cargo \n" +
						"\tleft join cbdmq.gen_rango rg on rg.cod_rango=gdp.cod_rango \n" +
						"\tleft join cbdmq.gen_grado gd on gd.cod_grado=gdp.cod_grado \n" +
						"\tleft join cbdmq.gen_materia_estudiante gme on gme.cod_estudiante=ge.cod_estudiante and gme.cod_materia =gm.cod_materia \n" +
						"\tleft join cbdmq.gen_dato_personal gdp2 on gdp2.cod_datos_personales =gins.cod_datos_personales \n" +
						"\twhere ge.codigo_unico_estudiante = :codUnico"
						,
					resultSetMapping = "FormacionEstudiante"
					)	
					
					@SqlResultSetMapping(name = "FormacionEstudiante", classes = @ConstructorResult(targetClass = FormacionEstudiante.class, columns = {
					@ColumnResult(name = "Cedula"),
					@ColumnResult(name = "Nombres"),
					@ColumnResult(name = "Apellidos"),
					@ColumnResult(name = "codigo_unico_estudiante"),
					@ColumnResult(name = "cargo"),
					@ColumnResult(name = "rango"),
					@ColumnResult(name = "grado"),
					@ColumnResult(name = "nombre_materia"),
					@ColumnResult(name = "instructor"),
					@ColumnResult(name = "nota_minima"),
					@ColumnResult(name = "peso_materia"),
					@ColumnResult(name = "numero_horas"),
					@ColumnResult(name = "nota_materia"),
					@ColumnResult(name = "nota_ponderacion"),
					@ColumnResult(name = "nota_disciplina"),
					}))

@NamedNativeQuery(name = "EspecializacionEstudiante.findHistorico",
		query =	"select gdp.cedula,gdp.nombre\n" +
				",gdp.apellido \n" +
				",ge.codigo_unico_estudiante\n" +
				",cg.nombre_cargo \n" +
				",rg.nombre_rango \n" +
				",gd.nombre_grado \n" +
				",gti.nombre_tipo_instructor \n" +
				",gdp2.apellido || gdp2.nombre as \"instructor\" \n" +
				",ga.nombre_aula \n" +
				",etc.nombre_tipo_curso \n" +
				",ecc.nombre_catalogo_curso \n" +
				",ec.fecha_inicio_curso  \n" +
				",ec.fecha_fin_curso  \n" +
				",ec.fecha_inicio_carga_nota  \n" +
				",ec.fecha_fin_carga_nota  \n" +
				",ene.fecha_crea_nota \n" +
				",ene.hora_crea_nota \n" +
				",ene.usuario_mod_nota " +
				",ene.fecha_mod_nota \n" +
				",ene.hora_mod_nota  \n" +
				",ene.nota_final_especializacion \n" +
				",ene.resultado \n" +
				"from cbdmq.esp_nota_especializacion ene \n" +
				"left join cbdmq.esp_curso ec on ene.cod_curso_especializacion=ec.cod_curso_especializacion\n" +
				"left join cbdmq.gen_estudiante ge on ge.cod_estudiante =ene.cod_estudiante\n" +
				"left join cbdmq.gen_unidad_gestion gug on gug.cod_unidad_gestion =gug.cod_unidad_gestion  \n" +
				"left join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales =ge.cod_datos_personales \n" +
				"left join cbdmq.gen_aula ga on ga.cod_aula =ec.cod_aula \n" +
				"left join cbdmq.esp_catalogo_cursos ecc on ecc.cod_catalogo_cursos =ec.cod_catalogo_cursos \n" +
				"left join cbdmq.esp_tipo_curso etc  on etc.cod_tipo_curso =ec.cod_tipo_curso \n" +
				"left join cbdmq.esp_curso_instructor eci on eci.cod_curso_especializacion =ec.cod_curso_especializacion \n" +
				"left join cbdmq.gen_instructor gi on gi.cod_instructor = eci.cod_instructor \n" +
				"left join cbdmq.gen_tipo_instructor gti on gti.cod_tipo_instructor =eci.cod_tipo_instructor  \n" +
				"left join cbdmq.gen_cargo cg on cg.cod_cargo=gdp.cod_cargo \n" +
				"left join cbdmq.gen_rango rg on rg.cod_rango=gdp.cod_rango \n" +
				"left join cbdmq.gen_grado gd on gd.cod_grado=gdp.cod_grado  \n" +
				"left join cbdmq.gen_dato_personal gdp2 on gdp2.cod_datos_personales =gi.cod_datos_personales "+
				"where ge.codigo_unico_estudiante = :codUnico"
		,
		resultSetMapping = "EspecializacionEstudiante"
)

@SqlResultSetMapping(name = "EspecializacionEstudiante", classes = @ConstructorResult(targetClass = EspecializacionEstudiante.class, columns = {
		@ColumnResult(name = "cedula"),
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"),
		@ColumnResult(name = "codigo_unico_estudiante"),
		@ColumnResult(name = "nombre_cargo"),
		@ColumnResult(name = "nombre_rango"),
		@ColumnResult(name = "nombre_grado"),
		@ColumnResult(name = "nombre_tipo_instructor"),
		@ColumnResult(name = "instructor"),
		@ColumnResult(name = "nombre_aula"),
		@ColumnResult(name = "nombre_tipo_curso"),
		@ColumnResult(name = "nombre_catalogo_curso"),
		@ColumnResult(name = "fecha_inicio_curso"),
		@ColumnResult(name = "fecha_fin_curso"),
		@ColumnResult(name = "fecha_inicio_carga_nota"),
		@ColumnResult(name = "fecha_fin_carga_nota"),
		@ColumnResult(name = "fecha_crea_nota"),
		@ColumnResult(name = "hora_crea_nota"),
		@ColumnResult(name = "usuario_mod_nota"),
		@ColumnResult(name = "fecha_mod_nota"),
		@ColumnResult(name = "hora_mod_nota"),
		@ColumnResult(name = "nota_final_especializacion"),
		@ColumnResult(name = "resultado"),

}))

@NamedNativeQuery(name = "ProfesionalizacionEstudiante.findHistorico",
		query = "\n" +
				"select gdp.cedula,\n" +
				"gdp.nombre,\n" +
				"gdp.apellido\n" +
				",ge.codigo_unico_estudiante\n" +
				",cg.nombre_cargo \n" +
				",rg.nombre_rango \n" +
				",gd.nombre_grado \n" +
				",gm.nombre_materia\n" +
				",gdp2.nombre || gdp2.apellido as \"Coordinador\" \n" +
				",gnp.nota_minima \n" +
				",gnp.peso_materia \n" +
				",gnp.numero_horas\n"+
				",gnp.nota_materia \n" +
				",gnp.nota_disciplina\n" +
				"from cbdmq.pro_nota_profesionalizacion gnp\n" +
				"left join cbdmq.gen_estudiante ge on ge.cod_estudiante=gnp.cod_estudiante \n" +
				"left join cbdmq.gen_materia gm on gm.cod_materia=gnp.cod_materia \n" +
				"LEFT JOIN cbdmq.gen_instructor gins ON gins.cod_instructor=gnp.cod_instructor\n" +
				"left join cbdmq.pro_semestre ps on ps.cod_semestre=gnp.cod_semestre \n" +
				"left join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales=ge.cod_datos_personales \n" +
				"left join cbdmq.gen_cargo cg on cg.cod_cargo=gdp.cod_cargo \n" +
				"left join cbdmq.gen_rango rg on rg.cod_rango=gdp.cod_rango \n" +
				"left join cbdmq.gen_grado gd on gd.cod_grado=gdp.cod_grado  \n" +
				"left join cbdmq.gen_dato_personal gdp2 on gdp2.cod_datos_personales =gins.cod_datos_personales \n"+
				"where ge.codigo_unico_estudiante = :codUnico"
		,
		resultSetMapping = "ProfesionalizacionEstudiante"
)

@SqlResultSetMapping(name = "ProfesionalizacionEstudiante", classes = @ConstructorResult(targetClass = ProfesionalizacionEstudiante.class, columns = {
		@ColumnResult(name = "cedula"),
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"),
		@ColumnResult(name = "codigo_unico_estudiante"),
		@ColumnResult(name = "nombre_cargo"),
		@ColumnResult(name = "nombre_rango"),
		@ColumnResult(name = "nombre_grado"),
		@ColumnResult(name = "nombre_materia"),
		@ColumnResult(name = "Coordinador"),
		@ColumnResult(name = "nota_minima"),
		@ColumnResult(name = "peso_materia"),
		@ColumnResult(name = "numero_horas"),
		@ColumnResult(name = "nota_materia"),
		@ColumnResult(name = "nota_disciplina"),

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
public class Estudiante{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_estudiante")
	private Integer codEstudiante;

	@Column(name = "cod_datos_personales")
	private Integer codDatosPersonales;
	
	@Column(name = "codigo_unico_estudiante")
	private String idEstudiante;
	@Column(name = "estado")

	private String estado;
}