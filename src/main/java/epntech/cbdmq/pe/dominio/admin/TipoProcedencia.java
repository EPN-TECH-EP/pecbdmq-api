package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "gen_tipo_procedencia")
@Table(name = "gen_tipo_procedencia")
public class TipoProcedencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_procedencia")
	public int codigo;
	
	@Column(name = "tipo_procedencia")
	public String nombre;
}
