package epntech.cbdmq.pe.dominio.admin;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
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

@NamedNativeQuery(name = "Requisito.findRequisitosPorEstudiante", 
query = "select vr.cod_requisito as codRequisito, gr.nombre_requisito as nombreRequisito,\r\n"
		+ "vr.observacion, vr.estado\r\n"
		+ "from cbdmq.esp_validacion_requisito vr, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal gdp,\r\n"
		+ "cbdmq.gen_requisito gr, cbdmq.esp_curso c\r\n"
		+ "where vr.cod_estudiante = e.cod_estudiante\r\n"
		+ "and e.cod_datos_personales = gdp.cod_datos_personales \r\n"
		+ "and vr.cod_requisito = gr.cod_requisito \r\n"
		+ "and vr.cod_curso_especializacion = c.cod_curso_especializacion \r\n"
		+ "and current_date between c.fecha_inicio_curso and c.fecha_fin_curso \r\n"
		+ "and UPPER(e.estado) = 'ACTIVO'\r\n"
		+ "and e.cod_estudiante = :codEstudiante\r\n"
		+ "and vr.cod_curso_especializacion = :codCursoEspecializacion", 
		resultSetMapping = "findRequisitosPorEstudiante")
@SqlResultSetMapping(name = "findRequisitosPorEstudiante", classes = @ConstructorResult(targetClass = ValidacionRequisitosDatos.class, columns = {
		@ColumnResult(name = "codRequisito"),
		@ColumnResult(name = "nombreRequisito"),
		@ColumnResult(name = "observacion"),
		@ColumnResult(name = "estado"), }))

public class Requisito {

	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_requisito")
	private Integer codigoRequisito;
	
	/*@Column(name = "cod_funcionario")
	private Integer codFuncionario;*/
	

	@Column(name = "nombre_requisito")
	private String nombre;
	
	@Column(name = "descripcion_requisito")
	private String descripcion;
	
	@Column(name = "es_documento")
	private Boolean esDocumento;
	
	@Column(name = "estado")
	private String estado;
	
	
	/*@ManyToMany(mappedBy = "requisitos", fetch = FetchType.LAZY)
    private Set<ConvocatoriaFor> convocatorias = new HashSet<>();*/
	
	/*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gen_requisito_documento",
            joinColumns = @JoinColumn(name = "cod_requisito"),
            inverseJoinColumns = @JoinColumn(name = "cod_documento")
    )
	private Set<DocumentoRequisitoFor> documentosRequisito = new HashSet<>();*/

}
