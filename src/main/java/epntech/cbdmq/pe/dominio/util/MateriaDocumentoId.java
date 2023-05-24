package epntech.cbdmq.pe.dominio.util;

import java.io.Serializable;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class MateriaDocumentoId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7100801367579540083L;

	private Integer cod_materia;
	private Integer cod_documento;
}
