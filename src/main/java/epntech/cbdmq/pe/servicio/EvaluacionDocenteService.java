package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.EvaluacionDocente;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EvaluacionDocenteService {

	
	EvaluacionDocente save(EvaluacionDocente obj) throws DataException;

    List<EvaluacionDocente> getAll();

    Optional<EvaluacionDocente> getById(Integer codigo);

    EvaluacionDocente update(EvaluacionDocente objActualizado);

    void delete(Integer codigo);
	
}

