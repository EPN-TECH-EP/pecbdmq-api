package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Ponderacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;


public interface PonderacionService {
	 Ponderacion save(Ponderacion obj) throws DataException;

	    List<Ponderacion> getAll();

	    Optional<Ponderacion> getById(Integer codigo);

	    Ponderacion update(Ponderacion objActualizado)throws DataException;

	    void delete(Integer codigo);
}
