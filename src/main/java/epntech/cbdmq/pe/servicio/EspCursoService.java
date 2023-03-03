package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.EspCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EspCursoService {

	EspCurso save(EspCurso obj) throws DataException;
	
	List<EspCurso> getAll();
	
	Optional<EspCurso> getById(Integer codigo);
	
	EspCurso update(EspCurso objActualizado) throws DataException;
	
	void delete(Integer codigo);
}
