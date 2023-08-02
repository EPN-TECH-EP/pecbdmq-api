package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Estados;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoEstado;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import org.springframework.data.repository.query.Param;

public interface CursoEstadoService {
	
	CursoEstado save(CursoEstado cursoEstado) throws DataException;
	
	CursoEstado update(CursoEstado cursoEstadoActualizado)  throws DataException;

	List<CursoEstado> listarTodo();
	List<CursoEstado> listarByTipoCurso(Long codTipoCurso) throws DataException;
	List<Estados> listarEstadosByTipoCurso(Long codTipoCurso) throws DataException;

	Optional<CursoEstado> getById(Long codCursoEstado) throws DataException;
	
	void delete(Long codCursoEstado) throws DataException;
	String getEstadoByCurso(Long codCurso);
	public Integer updateNextState(Integer id, Integer idCursoEstado);
}
