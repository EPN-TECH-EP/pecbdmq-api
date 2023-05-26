package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.ParametrizaPruebaResumen;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ParametrizaPruebaResumenService {

	
	ParametrizaPruebaResumen save(ParametrizaPruebaResumen obj) throws DataException;
	
	List<ParametrizaPruebaResumen> getAll();
	
	Optional<ParametrizaPruebaResumen> getById(int id);
	
	ParametrizaPruebaResumen update(ParametrizaPruebaResumen objActualizado) throws DataException;

	void delete(int id) throws DataException;
	
}
