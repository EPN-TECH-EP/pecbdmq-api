package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "gen_tipo_procedencia")
@Table(name = "gen_tipo_procedencia")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_procedencia SET estado = 'ELIMINADO' WHERE cod_tipo_procedencia = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoProcedencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_procedencia")
	public int codigo;
	
	@Column(name = "tipo_procedencia")
	public String nombre;
	
	@Column(name = "estado")
	private String estado;
}
