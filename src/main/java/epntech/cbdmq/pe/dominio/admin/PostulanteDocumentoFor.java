package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_postulante_documento")
public class PostulanteDocumentoFor {
	
	@Id
	private Integer cod_postulante;
	@Id
	private Integer cod_documento;

}
