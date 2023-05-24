package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_materia_documento")
@IdClass(MateriaDocumentoId.class)
public class MateriaDocumento {

	
	@Id
	private Integer cod_materia;
	@Id
	private Integer cod_documento;
	
	
}
