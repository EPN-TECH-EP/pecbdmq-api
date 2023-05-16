package epntech.cbdmq.pe.dominio.admin;

import java.io.Serializable;

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
				query ="select gm.nombre_materia as Materia,gdp2.nombre || gdp2.apellido as instructor\n" +
						",gpon.porcentaje_final_ponderacion\n" +
						",gpon.porcentaje_nota_materia\n" +
						",gcnota.porcentaje_componente_nota \n" +
						",gtn.tipo_nota \n" +
						",gnf.aporte_academico \n" +
						",gnf.nota_final_formacion\n" +
						",gnf.resultado \n" +
						"from {h-schema}gen_estudiante ge \n" +
						"left join {h-schema}gen_modulo gmod on gmod.cod_modulo =ge.cod_modulo \n" +
						"left join {h-schema}gen_materia_estudiante gme on gme.cod_estudiante=ge.cod_estudiante \n" +
						"left join {h-schema}gen_materia gm on gm.cod_materia =gme.cod_materia\n" +
						"left join {h-schema}gen_periodo_academico gpa on gpa.cod_periodo_academico=gme.cod_periodo_academico \n" +
						"left join {h-schema}gen_nota_formacion gnf on gnf.cod_materia=gme.cod_materia and gnf.cod_estudiante=ge.cod_estudiante \n" +
						"left join {h-schema}gen_ponderacion gpon on gpon.cod_ponderacion =gnf.cod_ponderacion \n" +
						"left join {h-schema}gen_componente_nota gcnota on gcnota.cod_componente_nota =gpon.cod_componente_nota \n" +
						"left join {h-schema}gen_tipo_nota gtn on gtn.cod_tipo_nota =gpon.cod_tipo_nota \n" +
						"LEFT JOIN {h-schema}gen_instructor gins ON gins.cod_instructor=gnf.cod_instructor \n" +
						"left join {h-schema}gen_dato_personal gdp2 on gdp2.cod_datos_personales =gins.cod_datos_personales\n" +
						"where ge.codigo_unico_estudiante = :codUnico"
						,
					resultSetMapping = "FormacionEstudiante"
					)	
					
					@SqlResultSetMapping(name = "FormacionEstudiante", classes = @ConstructorResult(targetClass = FormacionEstudiante.class, columns = {
					@ColumnResult(name = "Materia"),
					@ColumnResult(name = "instructor"),
					@ColumnResult(name = "porcentaje_final_ponderacion"),
					@ColumnResult(name = "porcentaje_nota_materia"),
					@ColumnResult(name = "porcentaje_componente_nota"),
					@ColumnResult(name = "tipo_nota"),
					@ColumnResult(name = "aporte_academico"),
					@ColumnResult(name = "nota_final_formacion"),
					@ColumnResult(name = "resultado")
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
