package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
import epntech.cbdmq.pe.dominio.util.EstacionTrabajoDto;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@Table(name = "gen_estacion_trabajo")
@SQLDelete(sql = "UPDATE {h-schema}gen_estacion_trabajo SET estado = 'ELIMINADO' WHERE cod_estacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")

@org.hibernate.annotations.NamedNativeQuery(name = "EstacionTrabajo.findEstaciones",
		query = "select get2.cod_estacion as codigo, get2.nombre_zona as nombre, get2.cod_canton as canton, get2.estado, " +
				"gc.nombre as nombreCanton, gp.cod_provincia as provincia, gp.nombre as nombreProvincia " +
				"from cbdmq.gen_estacion_trabajo get2 " +
				"inner join cbdmq.gen_canton gc on get2.cod_canton = gc.cod_canton " +
				"inner join cbdmq.gen_provincia gp on gc.cod_provincia = gp.cod_provincia " +
				"where get2.estado = 'ACTIVO'",
		resultSetMapping = "findEstaciones")
@SqlResultSetMapping(name = "findEstaciones", classes = @ConstructorResult(targetClass = EstacionTrabajoDto.class, columns = {
		@ColumnResult(name = "codigo", type = Integer.class),
		@ColumnResult(name = "nombre", type = String.class),
		@ColumnResult(name = "canton", type = Integer.class),
		@ColumnResult(name = "estado", type = String.class),
		@ColumnResult(name = "nombreCanton", type = String.class),
		@ColumnResult(name = "provincia", type = Integer.class),
		@ColumnResult(name = "nombreProvincia", type = String.class),
}))

@org.hibernate.annotations.NamedNativeQuery(name = "EstacionTrabajo.findEstacion",
		query = "select get2.cod_estacion as codigo, get2.nombre_zona as nombre, get2.cod_canton as canton, get2.estado, " +
				"gc.nombre as nombreCanton, gp.cod_provincia as provincia, gp.nombre as nombreProvincia " +
				"from cbdmq.gen_estacion_trabajo get2 " +
				"inner join cbdmq.gen_canton gc on get2.cod_canton = gc.cod_canton " +
				"inner join cbdmq.gen_provincia gp on gc.cod_provincia = gp.cod_provincia " +
				"where get2.estado = 'ACTIVO' " +
				"and get2.cod_estacion = :codigo",
		resultSetMapping = "findEstacion")
@SqlResultSetMapping(name = "findEstacion", classes = @ConstructorResult(targetClass = EstacionTrabajoDto.class, columns = {
		@ColumnResult(name = "codigo", type = Integer.class),
		@ColumnResult(name = "nombre", type = String.class),
		@ColumnResult(name = "canton", type = Integer.class),
		@ColumnResult(name = "estado", type = String.class),
		@ColumnResult(name = "nombreCanton", type = String.class),
		@ColumnResult(name = "provincia", type = Integer.class),
		@ColumnResult(name = "nombreProvincia", type = String.class),
}))
public class EstacionTrabajo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_estacion")
	private Integer codigo;
	
	@Column(name = "nombre_zona")
	private String nombre;
	
	@Column(name = "cod_canton")
	private Integer canton;
    
	@Column(name = "estado")
	private String estado;
		
}
