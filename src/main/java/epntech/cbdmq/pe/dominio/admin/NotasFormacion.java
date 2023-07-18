package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteDatos;
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
				@ColumnResult(name = "nota_ponderacion", type = Double.class),
				@ColumnResult(name = "nota_disciplina", type = Double.class),
				@ColumnResult(name = "nota_supletorio", type = Double.class),
				@ColumnResult(name = "cod_paralelo", type = Integer.class),
				@ColumnResult(name = "nombre_paralelo", type = String.class),
		}))
@NamedNativeQuery(name = "EstudianteDatos.getNotasEstudiantesMateria",
		query = "select gnf.cod_nota_formacion,ge.codigo_unico_estudiante, gdp.cedula ,gdp.nombre || gdp.apellido as \"nombre\", gnf.nota_ponderacion , gnf.nota_disciplina , gnf.nota_supletorio,mp.cod_paralelo, p.nombre_paralelo from {h-schema}gen_nota_formacion gnf\n" +
				"        left join {h-schema}gen_estudiante_materia_paralelo emp on gnf.cod_estudiante_materia_paralelo = emp.cod_estudiante_materia_paralelo\n" +
				"        left join {h-schema}gen_estudiante ge on emp.cod_estudiante = ge.cod_estudiante\n" +
				"        left join {h-schema}gen_dato_personal gdp on ge.cod_datos_personales = gdp.cod_datos_personales\n" +
				"        left join {h-schema}gen_materia_paralelo mp on emp.cod_materia_paralelo = mp.cod_materia_paralelo\n" +
				"        left join {h-schema}gen_paralelo p on mp.cod_paralelo = p.cod_paralelo\n" +
				"        left join {h-schema}gen_materia_periodo mpe on mp.cod_materia_periodo = mpe.cod_materia_periodo\n" +
				"        left join {h-schema}gen_materia m on mpe.cod_materia = m.cod_materia\n" +
				"        where m.cod_materia = :codMateria\n" +
				"        and mpe.cod_periodo_academico = :codPA",resultSetMapping = "EstudianteDatos"
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
	

	

	

