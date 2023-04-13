package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_convocatoria_documento")
public class ConvocatoriaDocumentoFor {
	
	@Id
	private Integer cod_convocatoria;
	@Id
	private Integer cod_documento;

}
