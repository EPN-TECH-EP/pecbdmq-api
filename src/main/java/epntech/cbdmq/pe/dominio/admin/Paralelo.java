package epntech.cbdmq.pe.dominio.admin;

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
public class Paralelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_paralelo")
	private Integer codParalelo;
	
	@Column(name = "nombre_paralelo")
	private String nombreParalelo;
}
