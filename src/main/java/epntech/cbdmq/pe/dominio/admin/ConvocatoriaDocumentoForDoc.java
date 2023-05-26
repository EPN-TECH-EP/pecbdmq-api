package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.util.ConvocatoriaDocumentoID;
import epntech.cbdmq.pe.dominio.util.MateriaPeriodoDataId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_convocatoria_documento")
@IdClass(ConvocatoriaDocumentoID.class)
public class ConvocatoriaDocumentoForDoc {
	
	@Id
	@Column (name="cod_convocatoria")
	private Integer codConvocatoria;
	
	@Id
	@Column (name="cod_documento")
	private Integer codDocumento;

}
