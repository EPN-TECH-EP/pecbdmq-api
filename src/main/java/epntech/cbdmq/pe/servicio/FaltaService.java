package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Falta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface FaltaService {

	Falta save(Falta obj) throws DataException;
	
	List<Falta> getAll();
	
	Optional<Falta> getById(int id);
	
	Falta update(Falta objActualizado) throws DataException;

	void delete(int id) throws DataException;
	
}
