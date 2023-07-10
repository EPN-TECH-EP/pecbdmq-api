
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Estados;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EstadosService {

	Estados save(Estados obj) throws DataException;
	
	List<Estados> getAll();
	
	Optional<Estados> getById(int id)  throws DataException;
	
	Estados update(Estados objActualizado)throws DataException;
	
	void delete(int id) throws DataException;
	
}

