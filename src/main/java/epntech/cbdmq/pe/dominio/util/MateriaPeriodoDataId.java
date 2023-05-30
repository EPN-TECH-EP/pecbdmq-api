package epntech.cbdmq.pe.dominio.util;

import java.io.Serializable;

import lombok.Data;

@Data
public class MateriaPeriodoDataId implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6037040083577280885L;

	private Integer codPeriodoAcademico;
    private Integer codMateria;
}
