package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_materia_paralelo_documento")
public class MateriaParaleloDocumento {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_materia_paralelo_documento")
	private Integer codMateriaParaleloDocumento;
	@Column(name = "cod_documento")
	private Integer codDocumento;
	@Column(name = "cod_materia_paralelo")
	private Integer codMateriaParalelo;
	@Column(name = "es_tarea")
	private Boolean esTarea;

	
	
}
