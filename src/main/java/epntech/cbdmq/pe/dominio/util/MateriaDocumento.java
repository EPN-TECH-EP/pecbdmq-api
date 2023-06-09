package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_materia_documento")
@IdClass(MateriaDocumentoId.class)
public class MateriaDocumento {

	
	@Id
	@Column(name = "cod_materia")
	private Integer codMateria;
	@Id
	@Column(name = "cod_documento")
	private Integer codDocumento;
	
	
}
