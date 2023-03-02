package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity(name = "gen_paralelo")
@Table(name = "gen_paralelo")
@SQLDelete(sql = "UPDATE {h-schema}gen_paralelo SET estado = 'ELIMINADO' WHERE cod_paralelo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Paralelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_paralelo")
	private Integer codParalelo;
	
	@Column(name = "nombre_paralelo")
	private String nombreParalelo;
	
	@Column(name = "estado")
	private String estado;
}
