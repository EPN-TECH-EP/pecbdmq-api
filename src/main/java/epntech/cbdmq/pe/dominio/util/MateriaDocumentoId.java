package epntech.cbdmq.pe.dominio.util;

import java.io.Serializable;

import lombok.Data;

@Data
public class MateriaDocumentoId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7100801367579540083L;

	private Integer codMateria;
	private Integer codDocumento;
}
