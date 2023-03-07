package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.ResultadoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ResultadoPruebaService {

	
	ResultadoPrueba save(ResultadoPrueba obj) throws DataException;
	
	List<ResultadoPrueba>getAll();
	
	Optional<ResultadoPrueba> getById(Integer codigo);
	
	ResultadoPrueba update(ResultadoPrueba objActualizado) throws DataException;
	
	void delete(Integer codigo);
	
	
	
}
