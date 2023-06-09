package epntech.cbdmq.pe.dominio.util;

import java.io.Serializable;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaDocumentoForDoc;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;



public class FormacionDocumento  implements Serializable{

	private static final long serialVersionUID = -1975629549974077833L;	
	

	private Integer codPeriodo_academico;

	private Integer codDocumento;
	
}
