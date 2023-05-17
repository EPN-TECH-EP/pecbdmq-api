package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Canton;
import epntech.cbdmq.pe.dominio.admin.CantonProjection;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CantonService {
	
	//Canton save(Canton obj) throws DataException;
	
	List<Canton> getAll();
	
	Optional<Canton> getById(int id);
	
	//Canton update(Canton objActualizado) throws DataException;

	//void delete(int id) throws DataException;
	
	List<CantonProjection> getAllByCodProvinciaId(int id);
}
