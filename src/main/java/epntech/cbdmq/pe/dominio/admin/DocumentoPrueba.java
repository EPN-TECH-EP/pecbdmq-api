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
@Table(name = "gen_documento_prueba")
public class DocumentoPrueba {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_documento_prueba")
	private Integer codDocumentoPrueba;
	@Column(name = "cod_prueba_detalle")
	private Integer codPruebaDetalle;
	@Column(name = "cod_documento")
	private Integer codDocumento;
	

}
