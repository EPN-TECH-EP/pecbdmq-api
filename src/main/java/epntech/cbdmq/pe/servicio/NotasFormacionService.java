package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.NotasFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface NotasFormacionService {

	void saveAll(List<NotasFormacion> lista);
	
	List<NotasFormacion> getByEstudiante(int id);
	
	NotasFormacion update(NotasFormacion objActualizado) throws DataException;
	
	Optional<NotasFormacion> getById(int id);
	
}
