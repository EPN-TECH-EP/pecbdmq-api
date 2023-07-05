package epntech.cbdmq.pe.dominio.admin.especializacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_convocatoria_documento")
public class ConvocatoriaDocumentoEsp {

	@Id
	@Column(name = "cod_convocatoria")
	private Integer codConvocatoria;
	
	@Id
	@Column(name = "cod_documento")
	private Integer codDocumento;
}

