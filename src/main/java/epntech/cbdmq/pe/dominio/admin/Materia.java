package epntech.cbdmq.pe.dominio.admin;

import java.util.ArrayList;
import java.util.List;

import epntech.cbdmq.pe.dominio.admin.formacion.InformacionMateriaDto;
import epntech.cbdmq.pe.dominio.admin.formacion.MateriaParaleloDto;
import jakarta.persistence.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

@Data

@NamedNativeQuery(name = "MateriaParaleloDto.getByInstructor",
		query = "select\n" +
				"\tm3_0.cod_materia,\n" +
				"\tm1_0.cod_materia_paralelo,\n" +
				"\tm3_0.cod_eje_materia,\n" +
				"\tm3_0.estado,\n" +
				"\tm3_0.nombre_materia\n" +
				"from\n" +
				"\tcbdmq.gen_instructor_materia_paralelo i1_0\n" +
				"left join cbdmq.gen_tipo_instructor t1_0 on\n" +
				"\ti1_0.cod_tipo_instructor = t1_0.cod_tipo_instructor\n" +
				"left join cbdmq.gen_instructor i2_0 on\n" +
				"\ti1_0.cod_instructor = i2_0.cod_instructor\n" +
				"left join cbdmq.gen_materia_paralelo m1_0 on\n" +
				"\tm1_0.cod_materia_paralelo = i1_0.cod_materia_paralelo\n" +
				"left join cbdmq.gen_materia_periodo m2_0 on\n" +
				"\tm1_0.cod_materia_periodo = m2_0.cod_materia_periodo\n" +
				"left join cbdmq.gen_materia m3_0 on\n" +
				"\tm2_0.cod_materia = m3_0.cod_materia\n" +
				"where\n" +
				"\ti2_0.cod_instructor =:codInstructor\n" +
				"\tand t1_0.nombre_tipo_instructor =:nombreTipoInstructor\n" +
				"\tand m2_0.cod_periodo_academico =:periodoAcademico\n"
		,
		resultSetMapping = "MateriaParaleloDto"
)

@SqlResultSetMapping(name = "MateriaParaleloDto", classes = @ConstructorResult(targetClass = MateriaParaleloDto.class, columns = {
		@ColumnResult(name = "cod_materia",type = Integer.class),
		@ColumnResult(name = "cod_materia_paralelo",type = Integer.class),
		@ColumnResult(name = "cod_eje_materia",type = Integer.class),
		@ColumnResult(name = "estado",type = String.class),
		@ColumnResult(name = "nombre_materia",type = String.class),
}))

@Entity(name = "gen_materia")
@Table(name ="gen_materia")
@SQLDelete(sql = "UPDATE {h-schema}gen_materia SET estado = 'ELIMINADO' WHERE cod_materia = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Materia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_materia")
	private Integer codMateria;
	@Column(name = "nombre_materia")
	private String nombre;
	@Column(name = "cod_eje_materia")
	private Integer codEjeMateria;
	@Column(name = "estado")
	private String estado;

}
