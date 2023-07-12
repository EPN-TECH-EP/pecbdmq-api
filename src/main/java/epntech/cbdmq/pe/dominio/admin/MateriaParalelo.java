package epntech.cbdmq.pe.dominio.admin;


import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaReadDto;
import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NamedNativeQuery;

@Data
@Entity
@Table(name ="gen_materia_paralelo")
@SqlResultSetMapping(name = "InstructorMateriaReadDto", classes =
		@ConstructorResult(
				targetClass = InstructorMateriaReadDto.class,
				columns = {
				@ColumnResult(name = "cod_materia_periodo", type= Integer.class),
				@ColumnResult(name = "nombre_materia", type = String.class),
				@ColumnResult(name = "nombre_eje_materia", type = String.class),
				@ColumnResult(name = "cod_aula", type = Integer.class),
				@ColumnResult(name = "nombre_aula", type = String.class),
				@ColumnResult(name = "cod_paralelo", type = Integer.class),
				@ColumnResult(name = "nombre_paralelo", type = String.class)
		}))
@NamedNativeQuery(name = "InstructorMateriaReadDto.getMateriaParaleloNombres",
		query = "select distinct m2_0.cod_materia_periodo,m3_0.nombre_materia,e1_0.nombre_eje_materia, a1_0.cod_aula,a1_0.nombre_aula,p.cod_paralelo,p.nombre_paralelo\n" +
				"from cbdmq.gen_materia_paralelo m1_0 \n" +
				"left join cbdmq.gen_materia_periodo m2_0 on m1_0.cod_materia_periodo=m2_0.cod_materia_periodo \n" +
				"left join cbdmq.gen_aula a1_0 on m2_0.cod_aula=a1_0.cod_aula \n" +
				"left join cbdmq.gen_materia m3_0 on m2_0.cod_materia=m3_0.cod_materia \n" +
				"left join cbdmq.gen_eje_materia e1_0 on m3_0.cod_eje_materia=e1_0.cod_eje_materia\n" +
				"left join cbdmq.gen_paralelo p on m1_0.cod_paralelo=p.cod_paralelo\n" +
				"where m2_0.cod_periodo_academico=:codPA",resultSetMapping = "InstructorMateriaReadDto"
)
public class MateriaParalelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_materia_paralelo")
	private Integer codMateriaParalelo;
	@Column(name = "cod_materia_periodo")
	private Integer codMateriaPeriodo;
	@Column(name = "cod_paralelo")
	private Integer codParalelo;
	@Column(name = "estado")
	private String estado;

}
