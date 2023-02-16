package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "gen_tipo_documento")
@Table(name = "gen_tipo_documento")
public class TipoDocumento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_documento")
	private Integer codigoDocumento;
	
	@Column(name = "tipo_documento")
	private String tipoDocumento;
}
