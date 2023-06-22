package epntech.cbdmq.pe.dominio.admin;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gen_requisito")
@SQLDelete(sql = "UPDATE {h-schema}gen_requisito SET estado = 'ELIMINADO' WHERE cod_requisito = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
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
