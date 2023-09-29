package epntech.cbdmq.pe.dominio.admin;


import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosDatos;
import jakarta.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gen_requisito")
@SQLDelete(sql = "UPDATE {h-schema}gen_requisito SET estado = 'ELIMINADO' WHERE cod_requisito = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")

@NamedNativeQuery(name = "Requisito.findRequisitosPorInscripcion",
		query = "select vr.cod_validacion_requisito as codValidacionRequisito, vr.cod_requisito as codRequisito, "
				+ "vr.cod_inscripcion as codInscripcion, gr.nombre_requisito as nombreRequisito, vr.observacion, vr.estado "
				+ "from cbdmq.esp_validacion_requisito vr, cbdmq.gen_requisito gr "
				+ "where vr.cod_inscripcion = :codInscripcion "
				+ "and vr.cod_requisito = gr.cod_requisito",
		resultSetMapping = "findRequisitosPorInscripcion")
@SqlResultSetMapping(name = "findRequisitosPorInscripcion", classes = @ConstructorResult(targetClass = ValidacionRequisitosDatos.class, columns = {
		@ColumnResult(name = "codValidacionRequisito"),
		@ColumnResult(name = "codRequisito"),
		@ColumnResult(name = "codInscripcion"),
		@ColumnResult(name = "nombreRequisito"),
		@ColumnResult(name = "observacion"),
		@ColumnResult(name = "estado"), }))

public class Requisito {

	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_requisito")
	private Integer codigoRequisito;

	@Column(name = "nombre_requisito")
	private String nombre;
	
	@Column(name = "descripcion_requisito")
	private String descripcion;
	
	@Column(name = "es_documento")
	private Boolean esDocumento;
	
	@Column(name = "estado")
	private String estado;

}
