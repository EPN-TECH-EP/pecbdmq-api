package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "gen_tipo_funcionario")
@Table(name = "gen_tipo_funcionario")
public class TipoFuncionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_funcionario")
	private Integer codigo;
	
	@Column(name = "nombre_tipo_funcionario")
	private String nombre;
}
