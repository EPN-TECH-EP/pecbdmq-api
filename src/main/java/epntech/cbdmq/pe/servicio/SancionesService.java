package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Sanciones;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface SancionesService {

	Sanciones save(Sanciones obj) throws DataException;

    List<Sanciones> getAll();

    Optional<Sanciones> getById(Integer codigo);

    Sanciones update(Sanciones objActualizado);

    void delete(Integer codigo);
	
	
}
