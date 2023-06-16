package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoEstado;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CursoEstadoService {
	
	CursoEstado save(CursoEstado cursoEstado) throws DataException;
	
	CursoEstado update(CursoEstado cursoEstadoActualizado)  throws DataException;

	List<CursoEstado> listarTodo();
	
	Optional<CursoEstado> getById(Long codCursoEstado);
	
	void delete(Long codCursoEstado);
}
