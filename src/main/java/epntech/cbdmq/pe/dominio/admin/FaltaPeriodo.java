package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import epntech.cbdmq.pe.dominio.util.TipoFaltaPeriodoUtil;
import jakarta.persistence.*;
import lombok.Data;
@SqlResultSetMapping(name = "TipoFaltaPeriodoUtil", classes =
@ConstructorResult(
		targetClass = TipoFaltaPeriodoUtil.class,
		columns = {
				@ColumnResult(name = "cod_falta_periodo", type= Integer.class),
				@ColumnResult(name = "cod_tipo_falta", type= Integer.class),
				@ColumnResult(name = "nombre_falta", type= String.class),
				@ColumnResult(name = "puntaje", type = BigDecimal.class),
		}))
@NamedNativeQuery(name = "TipoFaltaPeriodoUtil.get",
		query = "select f1_0.cod_falta_periodo,f1_0.cod_tipo_falta,t1_0.nombre_falta,f1_0.puntaje \n" +
				"from cbdmq.gen_falta_periodo f1_0 \n" +
				"left join cbdmq.gen_tipo_falta t1_0 \n" +
				"on f1_0.cod_tipo_falta=t1_0.cod_tipo_falta \n" +
				"where t1_0.estado <> 'ELIMINADO'\n",resultSetMapping = "TipoFaltaPeriodoUtil"
)
@Data
@Entity
@Table(name = "gen_falta_periodo")
public class FaltaPeriodo {
	@Id
	
	@Column(name = "cod_falta_periodo")
	private Integer codFaltaPeriodo;

	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	
	@Column(name = "puntaje")
	private BigDecimal puntaje;
	@Column(name = "cod_tipo_falta")
	private Integer codTipoFalta;
	
}
