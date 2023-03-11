package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.EstacionTrabajo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EstacionTrabajoService {

	EstacionTrabajo save(EstacionTrabajo obj) throws DataException;
	
	List<EstacionTrabajo> getAll();
	
	Optional<EstacionTrabajo> getById(int id);
	
	EstacionTrabajo update(EstacionTrabajo objActualizado) throws DataException;

	void delete(int id) throws DataException;
}
