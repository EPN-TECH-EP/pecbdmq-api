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

@Entity(name = "gen_tipo_documento")
@Table(name = "gen_tipo_documento")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_documento SET estado = 'ELIMINADO' WHERE cod_tipo_documento = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoDocumento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_documento")
	private Integer codigoDocumento;
	
	@Column(name = "tipo_documento")
	private String tipoDocumento;
	
	@Column(name = "estado")
	private String estado;
}
