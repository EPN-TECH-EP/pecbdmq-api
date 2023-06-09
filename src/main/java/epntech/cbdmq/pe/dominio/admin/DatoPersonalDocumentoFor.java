package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_documento_dato_personal")
public class DatoPersonalDocumentoFor {
	
	@Id
	@Column(name = "cod_datos_personales")
	private Integer codDatosPersonales;
	@Id
	@Column(name = "cod_documento")
	private Integer codDocumento;

}
