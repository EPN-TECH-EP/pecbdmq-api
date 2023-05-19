package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.NotasFormacion;

public interface NotasFormacionService {

	void saveAll(List<NotasFormacion> lista);
	
	List<NotasFormacion> getByEstudiante(int id);
	
	NotasFormacion update(NotasFormacion objActualizado);
	
	Optional<NotasFormacion> getById(int id);
}
