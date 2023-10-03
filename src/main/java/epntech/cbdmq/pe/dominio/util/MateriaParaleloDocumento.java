package epntech.cbdmq.pe.dominio.util;

import epntech.cbdmq.pe.dominio.admin.formacion.MateriaCursoDocumentoDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_materia_paralelo_documento")
@NamedNativeQuery(name = "MateriaDocumentoDto.findByCodMateriaParalelo",
		query = "select\n" +
				"\tgmpd.cod_materia_paralelo_documento,\n" +
				"\tgmpd.cod_documento,\n" +
				"\tgmpd.cod_materia_paralelo,\n" +
				"\tgmpd.es_tarea,\n" +
				"\tgd.nombre_documento,\n" +
				"\tgd.ruta,\n" +
				"gd.descripcion\n" +
				"from\n" +
				"\tcbdmq.gen_materia_paralelo_documento gmpd\n" +
				"left join cbdmq.gen_documento gd on\n" +
				"\tgmpd.cod_documento = gd.cod_documento\n" +
				"where\n" +
				"\tgmpd.cod_materia_paralelo =:codMateriaParalelo"
		,
		resultSetMapping = "MateriaDocumentoDto"
)

@SqlResultSetMapping(name = "MateriaDocumentoDto", classes = @ConstructorResult(targetClass = MateriaCursoDocumentoDto.class, columns = {
		@ColumnResult(name = "cod_materia_paralelo_documento", type = Integer.class),
		@ColumnResult(name = "cod_documento", type = Integer.class),
		@ColumnResult(name = "cod_materia_paralelo", type = Integer.class),
		@ColumnResult(name = "es_tarea", type = Boolean.class),
		@ColumnResult(name = "ruta", type = String.class),
		@ColumnResult(name = "nombre_documento", type = String.class),
		@ColumnResult(name = "descripcion", type = String.class),
}))

public class MateriaParaleloDocumento {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_materia_paralelo_documento")
	private Integer codMateriaParaleloDocumento;
	@Column(name = "cod_documento")
	private Integer codDocumento;
	@Column(name = "cod_materia_paralelo")
	private Integer codMateriaParalelo;
	@Column(name = "es_tarea")
	private Boolean esTarea;

	
	
}
