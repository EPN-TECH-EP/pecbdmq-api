package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import lombok.*;

@Data

@Entity(name = "gen_requisito")
@Table(name = "gen_requisito")
@SQLDelete(sql = "UPDATE {h-schema}gen_requisito SET estado = 'ELIMINADO' WHERE cod_requisito = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Requisito {

	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	@Column(name = "cod_requisito")
	private Integer codigo;
	
	
	
	@Column(name = "cod_funcionario")
	private Integer codFuncionario;
	
	
	
	@Column(name = "nombre_requisito")
	private String nombre;
	
	@Column(name = "descripcion_requisito")
	private String descripcion;
	
	@Column(name = "es_documento")
	private Boolean esDocumento;
	
	@Column(name = "estado")
	private String estado;
}
