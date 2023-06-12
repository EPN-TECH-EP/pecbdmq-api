package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_postulante_documento")
public class PostulanteDocumentoFor {
	
	@Id
	@Column(name = "cod_postulante")
	private Integer codPostulante;
	@Id
	@Column(name = "cod_documento")
	private Integer codDocumento;

}
