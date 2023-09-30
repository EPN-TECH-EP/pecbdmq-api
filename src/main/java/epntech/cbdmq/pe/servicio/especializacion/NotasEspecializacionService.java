package epntech.cbdmq.pe.servicio.especializacion;

import epntech.cbdmq.pe.dominio.admin.especializacion.NotasEspecializacion;
import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import epntech.cbdmq.pe.dto.especializacion.NotasEspecializacionDTO;

import java.util.List;

public interface NotasEspecializacionService {

	NotasEspecializacion save(NotasEspecializacionDTO notasEspecializacionDTO);
	
	List<NotasEspecializacionDTO> getAllByCurso(Integer codCurso);

	List<NotasEspecializacionDTO> getAllAprobadosByCurso(Integer codCurso);

	NotasEspecializacion getById(int id);
	List<NotaMateriaByEstudiante> getHistoricosByCursoAndEstudiante(Integer codCurso, Integer codEstudiante);
	List<NotaMateriaByEstudiante> getHistoricosEstudiante(Integer codEstudiante);

}

