package epntech.cbdmq.pe.dominio.admin.especializacion;

import epntech.cbdmq.pe.dominio.admin.UsuarioEstudiante;
import epntech.cbdmq.pe.dominio.admin.llamamiento.RequisitosVerificados;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NamedNativeQuery;

import java.time.LocalDate;

@NamedNativeQuery(name = "RequisitosVerificados.findForEspByDp",
		query = "select\n" +
				"\tevr.cod_requisito,\n" +
				"\tgr.nombre_requisito,\n" +
				"\tgr.descripcion_requisito,\n" +
				"\tevr.estado,\n" +
				"\t'ESPECIALIZACIÓN' as fuente\n" +
				"from\n" +
				"\tcbdmq.esp_validacion_requisito evr\n" +
				"left join cbdmq.esp_inscripcion ei on\n" +
				"\tevr.cod_inscripcion = ei.cod_inscripcion\n" +
				"left join cbdmq.gen_estudiante ge on\n" +
				"\tei.cod_estudiante = ge.cod_estudiante\n" +
				"left join cbdmq.gen_dato_personal gdp on\n" +
				"\tge.cod_datos_personales = gdp.cod_datos_personales\n" +
				"left join cbdmq.gen_requisito gr on\n" +
				"\tevr.cod_requisito = gr.cod_requisito\n" +
				"where\n" +
				"\tge.cod_datos_personales = :codDP\n" +
				"union all \n" +
				"select\n" +
				"\tgvr.cod_requisitos,\n" +
				"\tgr2.nombre_requisito,\n" +
				"\tgr2.descripcion_requisito,\n" +
				"\tgvr.estado,\n" +
				"\t'FORMACIÓN' as fuente\n" +
				"from\n" +
				"\tcbdmq.gen_validacion_requisitos gvr\n" +
				"left join cbdmq.gen_postulante gp on\n" +
				"\tgvr.cod_postulante = gp.cod_postulante\n" +
				"left join cbdmq.gen_dato_personal gdp2 on\n" +
				"\tgp.cod_datos_personales = gdp2.cod_datos_personales\n" +
				"left join cbdmq.gen_requisito gr2 on\n" +
				"\tgvr.cod_requisitos = gr2.cod_requisito\n" +
				"where\n" +
				"\tgp.cod_datos_personales = :codDP\n" +
				"union all\n" +
				"select\n" +
				"\tpcr.cod_requisito,\n" +
				"\tgr3.nombre_requisito,\n" +
				"\tgr3.descripcion_requisito,\n" +
				"\tpcr.cumple,\n" +
				"\t'PROFESIONALIZACIÓN' as fuente\n" +
				"from\n" +
				"\tcbdmq.pro_cumple_requisitos pcr\n" +
				"left join cbdmq.pro_inscripcion pi2 on\n" +
				"\tpcr.cod_inscripcion = pi2.cod_inscripcion\n" +
				"left join cbdmq.gen_estudiante ge2 on\n" +
				"\tpi2.cod_estudiante = ge2.cod_estudiante\n" +
				"left join cbdmq.gen_dato_personal gdp3 on\n" +
				"\tge2.cod_datos_personales = gdp3.cod_datos_personales\n" +
				"left join cbdmq.gen_requisito gr3 on\n" +
				"\tpcr.cod_requisito = gr3.cod_requisito\n" +
				"where\n" +
				"\tge2.cod_datos_personales = :codDP\n"
		,
		resultSetMapping = "RequisitosVerificados"
)

@SqlResultSetMapping(name = "RequisitosVerificados", classes = @ConstructorResult(targetClass = RequisitosVerificados.class, columns = {
		@ColumnResult(name = "cod_requisito", type = Integer.class),
		@ColumnResult(name = "nombre_requisito", type = String.class),
		@ColumnResult(name = "descripcion_requisito", type = String.class),
		@ColumnResult(name = "estado", type = Boolean.class),
		@ColumnResult(name = "fuente", type = String.class),

}))

@Data
@Entity
@Table(name = "esp_validacion_requisito")
public class ValidaRequisitos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_validacion_requisito")
	private Long codValidacionRequisito;
	
	@Column(name = "cod_requisito")
	private Long codRequisito;

	@Column(name = "cod_inscripcion")
	private Long codInscripcion;
	
	@Column(name = "observacion")
	private String observacion;
	
	@Column(name = "estado")
	private Boolean estado;

}
