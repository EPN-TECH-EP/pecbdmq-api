package epntech.cbdmq.pe.servicio.evaluaciones;

import java.util.List;

public interface PreguntaEvaluacionService {

    List<PreguntaEvaluacionService> getAll();

    List<PreguntaEvaluacionService> getByCodEvaluacion(Long codEvaluacion);

    PreguntaEvaluacionService getById(Long codPreguntaEvaluacion);

    PreguntaEvaluacionService save(PreguntaEvaluacionService preguntaEvaluacion);

    PreguntaEvaluacionService update(PreguntaEvaluacionService preguntaEvaluacionActualizado);

    void delete(Long codPreguntaEvaluacion);

}
