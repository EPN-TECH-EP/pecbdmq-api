package epntech.cbdmq.pe.servicio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.Evaluacion;

import java.util.List;

public interface EvaluacionService {

    List<Evaluacion> getAll();

    Evaluacion getById(Long codEvaluacion);

    Evaluacion save(Evaluacion evaluacion);

    Evaluacion update(Long codEvalaucion, Evaluacion evaluacionActualizado);

    void delete(Long codEvaluacion);

}
