package epntech.cbdmq.pe.dominio.admin.especializacion;


import epntech.cbdmq.pe.dominio.admin.formacion.MateriaCursoDocumentoDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "esp_curso_documento")
@Data@NamedNativeQuery(name = "CursoDocumentoDto.findByCodCurso",
		query = "select\n" +
				"\tgmpd.cod_curso_documento ,\n" +
				"\tgmpd.cod_documento,\n" +
				"\tgmpd.cod_curso_especializacion ,\n" +
				"\tgmpd.es_tarea ,\n" +
				"\tgd.nombre_documento,\n" +
				"\tgd.ruta,\n" +
				"\tgd.descripcion\n" +
				"from\n" +
				"\tcbdmq.esp_curso_documento gmpd\n" +
				"left join cbdmq.gen_documento gd on\n" +
				"\tgmpd.cod_documento = gd.cod_documento\n" +
				"where\n" +
				"\tgmpd.cod_curso_especializacion  =:codCurso"
		,
		resultSetMapping = "CursoDocumentoDto"
)

@SqlResultSetMapping(name = "CursoDocumentoDto", classes = @ConstructorResult(targetClass = MateriaCursoDocumentoDto.class, columns = {
		@ColumnResult(name = "cod_curso_documento", type = Integer.class),
		@ColumnResult(name = "cod_documento", type = Integer.class),
		@ColumnResult(name = "cod_curso_especializacion", type = Integer.class),
		@ColumnResult(name = "es_tarea", type = Boolean.class),
		@ColumnResult(name = "ruta", type = String.class),
		@ColumnResult(name = "nombre_documento", type = String.class),
		@ColumnResult(name = "descripcion", type = String.class),
}))
public class CursoDocumento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_documento")
	private Long codCursoDocumento;
	
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;
	
	@Column(name = "cod_documento")
	private Long codDocumento;
	
	@Column(name = "aprobado")
	private Boolean aprobado;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "es_tarea")
	private Boolean esTarea=false;

}
