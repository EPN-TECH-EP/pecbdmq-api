package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;

public interface TipoCursoService {

	TipoCurso save(TipoCurso tipoCurso);
	
	TipoCurso update(TipoCurso tipoCursoActualizado);
	
	TipoCurso getById(Long codTipoCurso);
	
	List<TipoCurso> getAll();
	
	void delete(Long codTipoCurso);
}
