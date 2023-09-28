package epntech.cbdmq.pe.servicio.evaluaciones;

import java.util.List;

public interface RespuestaEstudianteService {

    List<RespuestaEstudianteService> getAll();

    List<RespuestaEstudianteService> getByCodEvaluacion(Long codEvaluacion);

    List<RespuestaEstudianteService> getByCodEstudianteAnonimo(Long codEstudianteAnonimo);

    RespuestaEstudianteService getById(Long codRespuestaEstudiante);

    RespuestaEstudianteService save(RespuestaEstudianteService respuestaEstudiante);

    void delete(Long codRespuestaEstudiante);

}
