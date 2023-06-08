package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.EspTipoCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EspTipoCursoService {

	EspTipoCurso save(EspTipoCurso obj) throws DataException;
	
	List<EspTipoCurso> getAll();
	
	Optional<EspTipoCurso> getById(Integer codigo);
	
	EspTipoCurso update(EspTipoCurso objActualizado) throws DataException;

	void delete(Integer codigo) throws DataException;
	
	
}
