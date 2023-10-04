package epntech.cbdmq.pe.dominio;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProfesionalizacionEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "gen_parametro")
@SQLDelete(sql = "UPDATE {h-schema}gen_parametro SET estado = 'ELIMINADO' WHERE cod_parametro = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Parametro extends ProfesionalizacionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_parametro")
	private Long codParametro;
	@Column(name = "nombre_parametro")
	private String nombreParametro;
	@Column(name = "valor")
	private String valor;

}
