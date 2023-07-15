package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_documento_prueba")
@IdClass(DocumentoPruebaId.class)
public class DocumentoPrueba {
	
	@Id
	@Column(name = "cod_prueba_detalle")
	private Integer codPruebaDetalle;
	@Id
	@Column(name = "cod_documento")
	private Integer codDocumento;
	

}

