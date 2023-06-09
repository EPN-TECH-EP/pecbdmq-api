package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface TipoCursoService {

	TipoCurso save(TipoCurso tipoCurso)  throws DataException;
	
	TipoCurso update(TipoCurso tipoCursoActualizado)  throws DataException;
	
	Optional<TipoCurso> getById(Long codTipoCurso);
	
	List<TipoCurso> getAll();
	
	void delete(Long codTipoCurso)  throws DataException;
}
