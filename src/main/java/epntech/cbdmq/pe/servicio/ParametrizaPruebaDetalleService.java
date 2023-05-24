package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.ParametrizaPruebaDetalle;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ParametrizaPruebaDetalleService {
	ParametrizaPruebaDetalle save(ParametrizaPruebaDetalle obj)throws DataException;
	
	List<ParametrizaPruebaDetalle>getAll();
	
	Optional<ParametrizaPruebaDetalle> getById(int id);
	
	ParametrizaPruebaDetalle update(ParametrizaPruebaDetalle objActualizado) throws DataException;

	void delete(int id) throws DataException;
	
}
