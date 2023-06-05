package epntech.cbdmq.pe.servicio;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.EncuestaPreguntaRespuesta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EncuestaPreguntaRespuestaService {

	EncuestaPreguntaRespuesta save(EncuestaPreguntaRespuesta obj) throws DataException;

    List<EncuestaPreguntaRespuesta> getAll();
	
    
    
}
