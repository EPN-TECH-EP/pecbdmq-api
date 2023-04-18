package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_documento_dato_personal")
public class DatoPersonalDocumentoFor {
	
	@Id
	private Integer cod_datos_personales;
	@Id
	private Integer cod_documento;

}
