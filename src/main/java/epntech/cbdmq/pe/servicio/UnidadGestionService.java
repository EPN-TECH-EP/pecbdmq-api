package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.UnidadGestion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;



public interface UnidadGestionService {
	
	UnidadGestion saveUnidadGestion(UnidadGestion obj) throws DataException;
	
	List<UnidadGestion> getAllUnidadGestion();
	
	Optional<UnidadGestion> getUnidadGestionById(int codigo);
	
	UnidadGestion updateUnidadGestion(UnidadGestion objActualizado) throws DataException;

}
