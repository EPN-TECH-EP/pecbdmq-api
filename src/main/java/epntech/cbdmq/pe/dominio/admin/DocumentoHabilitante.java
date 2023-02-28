package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity
@Table(name = "gen_documento_habilitante")
public class DocumentoHabilitante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_documento_habilitante")
	private Integer codDocumentoHabilitante;
	
	@Column(name = "lista_documentos")
	private String nombre;
}
