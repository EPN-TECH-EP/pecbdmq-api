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

@Entity(name = "gen_tipo_instruccion")
@Table(name = "gen_tipo_instruccion")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_instruccion SET estado = 'ELIMINADO' WHERE cod_tipo_instruccion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoInstruccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_instruccion")
	private Integer codigoTipoInstruccion;
	
	@Column(name = "tipo_instruccion")
	private String tipoInstruccion;
	
	@Column(name = "estado")
	private String estado;
}
