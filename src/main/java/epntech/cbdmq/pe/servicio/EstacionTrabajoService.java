package epntech.cbdmq.pe.servicio;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.EstacionTrabajo;
import epntech.cbdmq.pe.dominio.util.EstacionTrabajoDto;

public interface EstacionTrabajoService {

	EstacionTrabajoDto save(EstacionTrabajo obj);
	
	List<EstacionTrabajoDto> getAll();

	EstacionTrabajoDto getById(int id);

	EstacionTrabajoDto update(EstacionTrabajo objActualizado);

	void delete(int id);
}
