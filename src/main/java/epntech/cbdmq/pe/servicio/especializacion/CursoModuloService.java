package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoModulo;
import epntech.cbdmq.pe.dominio.util.CursoModuloDatosEspecializacion;

public interface CursoModuloService {

	CursoModulo save(CursoModulo cursoModulo);
	
	CursoModulo update(CursoModulo cursoModuloActualizado);
	
	List<CursoModuloDatosEspecializacion> listAll();
	
	Optional<CursoModulo> getById(Long codCursoModulo);
	
	void delete(Long codCursoModulo);
	
	List<CursoModulo> getByCurso(Long codCurso);
}
