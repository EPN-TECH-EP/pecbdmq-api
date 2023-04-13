package epntech.cbdmq.pe.dominio.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gen_requisito")
@SQLDelete(sql = "UPDATE {h-schema}gen_requisito SET estado = 'ELIMINADO' WHERE cod_requisito = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Requisitolistar {
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	@Column(name = "cod_requisito")
	private Integer codigo;
	
	
	
	//@Column(name = "cod_funcionario")
	//private Integer codFuncionario;
	
	
	
	@Column(name = "nombre_requisito")
	private String nombre;
	
	@Column(name = "descripcion_requisito")
	private String descripcion;
	
	@Column(name = "es_documento")
	private Boolean esDocumento;
	
	@Column(name = "estado")
	private String estado;
	
	
	
}
