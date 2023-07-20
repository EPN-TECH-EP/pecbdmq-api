package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoModulo;
import epntech.cbdmq.pe.dominio.util.CursoModuloDatosEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CursoModuloService {

	CursoModulo save(CursoModulo cursoModulo)  throws DataException;
	
	CursoModulo update(CursoModulo cursoModuloActualizado) throws DataException;
	
	List<CursoModuloDatosEspecializacion> listAll();
	
	Optional<CursoModulo> getById(Long codCursoModulo)  throws DataException;
	
	void delete(Long codCursoModulo)  throws DataException;
	
	List<CursoModulo> getByCurso(Long codCurso) throws DataException;
}

