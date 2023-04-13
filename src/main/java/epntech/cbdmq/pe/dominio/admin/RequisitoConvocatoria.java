package epntech.cbdmq.pe.dominio.admin;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import lombok.*;

@Data

@Entity
@Table(name = "gen_requisito")
@SQLDelete(sql = "UPDATE {h-schema}gen_requisito SET estado = 'ELIMINADO' WHERE cod_requisito = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class RequisitoConvocatoria {

	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_requisito")
	private Integer codigo;
	
	//@Column(name = "cod_convocatoria", insertable = true, updatable=false)
	//private Integer codConvocatoria;
	
	@Column(name = "cod_funcionario")
	private Integer codFuncionario;
	
	@Column(name = "nombre_requisito")
	private String nombre;
	
	@Column(name = "descripcion_requisito")
	private String descripcion;
	
	@Column(name = "estado")
	private String estado;
	
	@ManyToOne
	@JoinColumn(name = "cod_convocatoria")
	private ConvocatoriaFor convocatoriaFor;

}
