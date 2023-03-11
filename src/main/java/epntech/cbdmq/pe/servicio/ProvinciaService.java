package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;


import epntech.cbdmq.pe.dominio.admin.Provincia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ProvinciaService {
	
	Provincia save(Provincia obj) throws DataException;
	
	List<Provincia> getAll();
	
	Optional<Provincia> getById(int id);
	
	Provincia update(Provincia objActualizado) throws DataException;

	void delete(int id) throws DataException;
}
