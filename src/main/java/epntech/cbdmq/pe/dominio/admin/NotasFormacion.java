package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteDatos;
import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import jakarta.persistence.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;
@SqlResultSetMapping(name = "EstudianteDatos", classes =
@ConstructorResult(
		targetClass = EstudianteDatos.class,
		columns = {
				@ColumnResult(name = "cod_nota_formacion", type= Integer.class),
				@ColumnResult(name = "codigo_unico_estudiante", type= String.class),
				@ColumnResult(name = "cedula", type= String.class),
				@ColumnResult(name = "nombre", type= String.class),
				@ColumnResult(name = "nota_materia", type = Double.class),
				@ColumnResult(name = "nota_disciplina", type = Double.class),
				@ColumnResult(name = "nota_supletorio", type = Double.class),
				@ColumnResult(name = "cod_paralelo", type = Integer.class),
				@ColumnResult(name = "nombre_paralelo", type = String.class),
		}))
@NamedNativeQuery(name = "EstudianteDatos.getNotasEstudiantesMateria",
		query = "select gnf.cod_nota_formacion,ge.codigo_unico_estudiante, gdp.cedula ,gdp.nombre || '  ' ||  gdp.apellido as \"nombre\", gnf.nota_materia , gnf.nota_disciplina , gnf.nota_supletorio,mp.cod_paralelo, p.nombre_paralelo from {h-schema}gen_nota_formacion gnf\n" +
				"        left join {h-schema}gen_estudiante_materia_paralelo emp on gnf.cod_estudiante_materia_paralelo = emp.cod_estudiante_materia_paralelo\n" +
				"        left join {h-schema}gen_estudiante ge on emp.cod_estudiante = ge.cod_estudiante\n" +
				"        left join {h-schema}gen_dato_personal gdp on ge.cod_datos_personales = gdp.cod_datos_personales\n" +
				"        left join {h-schema}gen_materia_paralelo mp on emp.cod_materia_paralelo = mp.cod_materia_paralelo\n" +
				"        left join {h-schema}gen_paralelo p on mp.cod_paralelo = p.cod_paralelo\n" +
				"        left join {h-schema}gen_materia_periodo mpe on mp.cod_materia_periodo = mpe.cod_materia_periodo\n" +
				"        left join {h-schema}gen_materia m on mpe.cod_materia = m.cod_materia\n" +
				"        where m.cod_materia = :codMateria\n" +
				"		 and ge.estado <> 'BAJA'\n"+
				"        and mpe.cod_periodo_academico = :codPA",resultSetMapping = "EstudianteDatos"
)

@SqlResultSetMapping(name = "NotaMateriaByEstudiante", classes =
@ConstructorResult(
		targetClass = NotaMateriaByEstudiante.class,
		columns = {
				@ColumnResult(name = "cod_nota_formacion", type= Integer.class),
				@ColumnResult(name = "nombre_materia", type= String.class),
				@ColumnResult(name = "nota_materia", type = Double.class),
				@ColumnResult(name = "nota_disciplina", type = Double.class),
				@ColumnResult(name = "nota_supletorio", type = Double.class),
				@ColumnResult(name = "cod_instructor", type = Integer.class),
				@ColumnResult(name = "nombre_completo_instructor", type = String.class),
		}))
@NamedNativeQuery(name = "NotaMateriaByEstudiante.get",
		query = "select gnf.cod_nota_formacion, gm.nombre_materia, gnf.nota_materia , gnf.nota_disciplina , gnf.nota_supletorio, gimp.cod_instructor, gdp.nombre || ' ' || gdp.apellido as nombre_completo_instructor from cbdmq.gen_nota_formacion gnf \n" +
				"left join cbdmq.gen_estudiante_materia_paralelo gemp on gnf.cod_estudiante_materia_paralelo = gemp.cod_estudiante_materia_paralelo  \n" +
				"left join cbdmq.gen_estudiante ge on gemp.cod_estudiante = ge.cod_estudiante \n" +
				"left join cbdmq.gen_nota_formacion_final gnff on ge.cod_estudiante = gnff.cod_estudiante \n" +
				"left join cbdmq.gen_materia_paralelo gmp on gemp.cod_materia_paralelo = gmp.cod_materia_paralelo\n" +
				"left join cbdmq.gen_instructor_materia_paralelo gimp on gmp.cod_materia_paralelo = gimp.cod_materia_paralelo\n" +
				"left join cbdmq.gen_tipo_instructor gti on gimp.cod_tipo_instructor = gti.cod_tipo_instructor \n" +
				"left join cbdmq.gen_instructor gi on gimp.cod_instructor = gi.cod_instructor \n" +
				"left join cbdmq.gen_dato_personal gdp on gi.cod_datos_personales = gdp.cod_datos_personales \n" +
				"left join cbdmq.gen_materia_periodo mp on gmp.cod_materia_periodo = mp.cod_materia_periodo \n" +
				"left join cbdmq.gen_materia gm on mp.cod_materia = gm.cod_materia \n" +
				"where ge.cod_estudiante = :codEstudiante \n" +
				"and gti.nombre_tipo_instructor = :tipoInstructor \n" +
				"and ge.estado <> 'BAJA' \n" +
				"and mp.cod_periodo_academico =  :codPA",resultSetMapping = "NotaMateriaByEstudiante"
)

@Data
@Entity
@Table(name = "gen_nota_formacion")
@SQLDelete(sql = "UPDATE {h-schema}gen_nota_formacion SET estado = 'ELIMINADO' WHERE cod_nota_formacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class NotasFormacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_nota_formacion")
	private Integer codNotaFormacion;
	@Column(name = "nota_minima")
	private Double notaMinima;
	
	@Column(name = "peso_materia")
	private Double pesoMateria;
	
	@Column(name = "numero_horas")
	private Integer numeroHoras;
	
	@Column(name = "nota_materia")
	private Double notaMateria;
	
	@Column(name = "nota_ponderacion")
	private Double notaPonderacion;
	
	@Column(name = "nota_disciplina")
	private Double notaDisciplina;
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "nota_supletorio")
	private Double notaSupletorio;
	
	@Column(name = "fecha_ingreso")
	private LocalDateTime fechaIngreso;
	@Column(name="cod_estudiante_materia_paralelo")
	private Integer codEstudianteMateriaParalelo;
}
