package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "gen_tipo_instruccion")
@Table(name = "gen_tipo_instruccion")
public class TipoInstruccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_instruccion")
	private Integer codigoTipoInstruccion;
	
	@Column(name = "tipo_instruccion")
	private String tipoInstruccion;
}
