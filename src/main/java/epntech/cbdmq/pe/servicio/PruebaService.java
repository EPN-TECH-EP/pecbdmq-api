package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Prueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PruebaService {

	Prueba save(Prueba obj) throws DataException;

    List<Prueba> getAll();

    Optional<Prueba> getById(Integer codigo);

    Prueba update(Prueba objActualizado);

    void delete(Integer codigo);
	
	
}
