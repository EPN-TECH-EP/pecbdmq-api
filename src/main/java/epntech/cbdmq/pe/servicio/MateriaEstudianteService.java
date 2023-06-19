package epntech.cbdmq.pe.servicio;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.MateriaEstudiante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface MateriaEstudianteService {

	MateriaEstudiante save(MateriaEstudiante materiaEstudiante) throws DataException;
	
	void delete(Long codMateriaEstudiante);
	
	List<MateriaEstudiante> getByCodEstudiante(Long codEstudiante);
}
