package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.PeriodoEvaluacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PeriodoEvaluacionService {
	
	PeriodoEvaluacion save(PeriodoEvaluacion obj) throws DataException;

	List<PeriodoEvaluacion> getAll();
	
	Optional<PeriodoEvaluacion> getById(int id);
	
	PeriodoEvaluacion update(PeriodoEvaluacion objActualizado);
	
	void delete(int id);
}
