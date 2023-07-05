package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoModelo;

public interface CursoModeloService {

	CursoModelo save(CursoModelo cursoModelo);
	
	CursoModelo update(CursoModelo cursoModeloActualizado);
	
	List<CursoModelo> listAll();
	
	Optional<CursoModelo> getById(Long codCursoModelo);
	
	void delete(Long codCursoModelo);
	
	List<CursoModelo> getByCurso(Long codCursoModelo);
}
