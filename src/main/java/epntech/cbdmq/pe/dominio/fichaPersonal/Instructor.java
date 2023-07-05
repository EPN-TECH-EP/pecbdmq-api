package epntech.cbdmq.pe.dominio.fichaPersonal;

import java.util.ArrayList;
import java.util.List;

import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionInstructor;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionInstructor;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionInstructor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NamedNativeQuery;

@Data

@Entity(name = "gen_instructor")
@Table(name = "gen_instructor")

@NamedNativeQuery(name = "FormacionInstructor.findHistorico",
		query = "select distinct\n" +
				"gdp2.nombre || gdp2.apellido as \"instructor\" \n" +
				",cg.nombre_cargo as Cargo\n" +
				",rg.nombre_rango as Rango\n" +
				",gd.nombre_grado as Grado\n" +
				",gm.nombre_materia\n" +
				",gnf.cod_periodo_academico\n" +
				"from cbdmq.gen_nota_formacion gnf  \n" +
				"left join cbdmq.gen_materia gm on gm.cod_materia =gnf.cod_materia\n" +
				"LEFT JOIN cbdmq.gen_instructor gins ON gins.cod_instructor=gnf.cod_instructor\n" +
				"left join cbdmq.gen_periodo_academico gpa on gpa.cod_periodo_academico=gnf.cod_periodo_academico\n" +
				"left join cbdmq.gen_dato_personal gdp2 on gdp2.cod_datos_personales =gins.cod_datos_personales\n" +
				"left join cbdmq.gen_cargo cg on cg.cod_cargo=gdp2.cod_cargo \n" +
				"left join cbdmq.gen_rango rg on rg.cod_rango=gdp2.cod_rango \n" +
				"left join cbdmq.gen_grado gd on gd.cod_grado=gdp2.cod_grado \n" +
				"where gins.cod_instructor =:codInstructor"
		,
		resultSetMapping = "FormacionInstructor"
)

@SqlResultSetMapping(name = "FormacionInstructor", classes = @ConstructorResult(targetClass = FormacionInstructor.class, columns = {
		@ColumnResult(name = "instructor"),
		@ColumnResult(name = "cargo"),
		@ColumnResult(name = "rango"),
		@ColumnResult(name = "grado"),
		@ColumnResult(name = "nombre_materia"),
		@ColumnResult(name = "cod_periodo_academico"),

}))

@NamedNativeQuery(name = "EspecializacionInstructor.findHistorico",
		query = "select \n" +
				"cg.nombre_cargo \n" +
				",rg.nombre_rango \n" +
				",gd.nombre_grado \n" +
				",gti.nombre_tipo_instructor \n" +
				",gdp2.apellido || gdp2.nombre as \"instructor\"\n" +
				",ga.nombre_aula \n" +
				",etc.nombre_tipo_curso \n" +
				",ecc.nombre_catalogo_curso \n" +
				",cur.fecha_inicio_curso  \n" +
				",cur.fecha_fin_curso  \n" +
				",cur.fecha_inicio_carga_nota  \n" +
				",cur.fecha_fin_carga_nota  \n" +
				"from cbdmq.esp_curso cur\n" +
				"left join cbdmq.esp_curso_instructor esi on esi.cod_curso_especializacion=cur.cod_curso_especializacion\n" +
				"left join cbdmq.gen_instructor gi on gi.cod_instructor =esi.cod_instructor\n" +
				"left join cbdmq.gen_aula ga on ga.cod_aula =cur.cod_aula \n" +
				"left join cbdmq.esp_catalogo_cursos ecc on ecc.cod_catalogo_cursos =cur.cod_catalogo_cursos \n" +
				"left join cbdmq.esp_tipo_curso etc  on etc.cod_tipo_curso =cur.cod_tipo_curso \n" +
				"left join cbdmq.esp_curso_instructor eci on eci.cod_curso_especializacion =cur.cod_curso_especializacion and eci.cod_instructor =gi.cod_instructor \n" +
				"left join cbdmq.gen_tipo_instructor gti on gti.cod_tipo_instructor =eci.cod_tipo_instructor  \n" +
				"left join cbdmq.gen_dato_personal gdp2 on gdp2.cod_datos_personales =gi.cod_datos_personales\n" +
				"left join cbdmq.gen_cargo cg on cg.cod_cargo=gdp2.cod_cargo \n" +
				"left join cbdmq.gen_rango rg on rg.cod_rango=gdp2.cod_rango \n" +
				"left join cbdmq.gen_grado gd on gd.cod_grado=gdp2.cod_grado " +
				"where gi.cod_instructor =:codInstructor"
		,
		resultSetMapping = "EspecializacionInstructor"
)

@SqlResultSetMapping(name = "EspecializacionInstructor", classes = @ConstructorResult(targetClass = EspecializacionInstructor.class, columns = {
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
}))

@NamedNativeQuery(name = "ProfesionalizacionInstructor.findHistorico",
		query = "select distinct\n" +
				"gdp2.apellido || gdp2.nombre as \"instructor\"\n" +
				",cg.nombre_cargo \n" +
				",rg.nombre_rango \n" +
				",gd.nombre_grado \n" +
				",gm.nombre_materia\n" +
				",ps.semestre\n" +
				"from cbdmq.pro_nota_profesionalizacion gnp \n" +
				"left join cbdmq.gen_materia gm on gm.cod_materia=gnp.cod_materia \n" +
				"LEFT JOIN cbdmq.gen_instructor gins ON gins.cod_instructor=gnp.cod_instructor\n" +
				"left join cbdmq.pro_semestre ps on ps.cod_semestre=gnp.cod_semestre \n" +
				"left join cbdmq.gen_dato_personal gdp2 on gdp2.cod_datos_personales =gins.cod_datos_personales\n" +
				"left join cbdmq.gen_cargo cg on cg.cod_cargo=gdp2.cod_cargo \n" +
				"left join cbdmq.gen_rango rg on rg.cod_rango=gdp2.cod_rango \n" +
				"left join cbdmq.gen_grado gd on gd.cod_grado=gdp2.cod_grado \n" +
				"where gins.cod_instructor =:codInstructor"
		,
		resultSetMapping = "ProfesionalizacionInstructor"
)

@SqlResultSetMapping(name = "ProfesionalizacionInstructor", classes = @ConstructorResult(targetClass = ProfesionalizacionInstructor.class, columns = {
		@ColumnResult(name = "instructor"),
		@ColumnResult(name = "nombre_cargo"),
		@ColumnResult(name = "nombre_rango"),
		@ColumnResult(name = "nombre_grado"),
		@ColumnResult(name = "nombre_materia"),
		@ColumnResult(name = "semestre"),

}))

@NamedNativeQuery(name = "Instructor.findInstructoresCurso",
query = "select ci.cod_instructor as codInstructor, dp.cedula, dp.nombre, dp.apellido, \r\n"
		+ "c.cod_curso_especializacion codCursoEspecializacion, cc.nombre_catalogo_curso as nombreCatalogoCurso,\r\n"
		+ "ti.cod_tipo_instructor as codTipoInstructor, ti.nombre_tipo_instructor as nombreTipoInstructor \r\n"
		+ "from cbdmq.esp_curso_instructor ci, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc, \r\n"
		+ "cbdmq.gen_instructor i, cbdmq.gen_dato_personal dp, cbdmq.gen_tipo_instructor ti \r\n"
		+ "where ci.cod_curso_especializacion = c.cod_curso_especializacion \r\n"
		+ "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos \r\n"
		+ "and ci.cod_instructor = i.cod_instructor \r\n"
		+ "and i.cod_datos_personales = dp.cod_datos_personales \r\n"
		+ "and ci.cod_tipo_instructor = ti.cod_tipo_instructor\r\n"
		+ "and upper(ci.estado) = 'ACTIVO'\r\n"
		+ "and upper(c.estado) = 'ACTIVO'\r\n"
		+ "and upper(cc.estado) = 'ACTIVO'\r\n"
		+ "and upper(dp.estado) = 'ACTIVO' \r\n"
		+ "and ci.cod_curso_especializacion = :codCurso",
		resultSetMapping = "findInstructoresCurso")
@SqlResultSetMapping(name = "findInstructoresCurso", classes = @ConstructorResult(targetClass = InstructoresCurso.class, columns = {
@ColumnResult(name = "codInstructor"),
@ColumnResult(name = "cedula"),
@ColumnResult(name = "nombre"),
@ColumnResult(name = "apellido"),
@ColumnResult(name = "codCursoEspecializacion"),
@ColumnResult(name = "nombreCatalogoCurso"),
@ColumnResult(name = "codTipoInstructor"),
@ColumnResult(name = "nombreTipoInstructor"),
}))

public class Instructor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_instructor")
	private Integer codInstructor;
	@Column(name = "cod_datos_personales")
	private Integer codDatosPersonales;
	@Column(name = "cod_tipo_procedencia")
	private Integer codTipoProcedencia;
	@Column(name = "cod_estacion")
	private Integer codEstacion;
	@Column(name = "cod_unidad_gestion")
	private Integer codUnidadGestion;
	@Column(name = "cod_tipo_contrato")
	private Integer codTipoContrato;
	
	
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_instructor_periodo",
            joinColumns = @JoinColumn(name = "cod_instructor"),
            inverseJoinColumns = @JoinColumn(name = "cod_periodo_academico")
    )
	private List<epntech.cbdmq.pe.dominio.admin.PeriodoAcademico> PeriodoAcademico = new ArrayList<>();
}

