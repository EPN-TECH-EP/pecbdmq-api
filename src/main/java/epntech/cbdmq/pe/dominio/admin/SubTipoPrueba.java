package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import epntech.cbdmq.pe.dominio.util.SubTipoPruebaDatos;
import jakarta.persistence.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;


@SqlResultSetMapping(name = "SubTipoPruebaDatos", classes =
@ConstructorResult(
		targetClass = SubTipoPruebaDatos.class,
		columns = {
				@ColumnResult(name = "cod_subtipo_prueba", type= Integer.class),
				@ColumnResult(name = "cod_tipo_prueba", type= Integer.class),
				@ColumnResult(name = "nombre", type = String.class),
				@ColumnResult(name = "estado", type = String.class),
				@ColumnResult(name = "tipo_prueba", type = String.class),
				@ColumnResult(name = "es_fisica", type = Boolean.class),
		}))
@NamedNativeQuery(name = "SubTipoPruebaDatos.get",
		query = "select\r\n"
				+ "	gsp.cod_subtipo_prueba, gsp.cod_tipo_prueba, gsp.nombre, gsp.estado,\r\n"
				+ "	gtp.tipo_prueba ,\r\n"
				+ "	gtp.es_fisica \r\n"
				+ "from\r\n"
				+ "	cbdmq.gen_subtipo_prueba gsp ,\r\n"
				+ "	cbdmq.gen_tipo_prueba gtp\r\n"
				+ "where\r\n"
				+ "	gtp.cod_tipo_prueba = gsp.cod_tipo_prueba\r\n"
				+ "order by \r\n"
				+ "	gtp.tipo_prueba,\r\n"
				+ "	gsp.nombre ",resultSetMapping = "SubTipoPruebaDatos"
)


@Data
@Entity(name = "SubTipoPrueba")
@Table(name = "gen_subtipo_prueba")
@SQLDelete(sql = "UPDATE {h-schema}gen_subtipo_prueba SET estado = 'ELIMINADO' WHERE cod_subtipo_prueba = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class SubTipoPrueba {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_subtipo_prueba")
	private Integer codSubtipoPrueba;	

	@Column(name = "cod_tipo_prueba")
	private Integer codTipoPrueba;	

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "estado")
	private String estado;
	
}

