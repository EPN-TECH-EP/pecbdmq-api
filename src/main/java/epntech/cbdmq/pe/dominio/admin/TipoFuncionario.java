package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "gen_tipo_funcionario")
@Table(name = "gen_tipo_funcionario")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_funcionario SET estado = 'ELIMINADO' WHERE cod_tipo_funcionario = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoFuncionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_funcionario")
	private Integer codigo;
	
	@Column(name = "nombre_tipo_funcionario")
	private String nombre;
	
	@Column(name = "estado")
	private String estado;
}
