package epntech.cbdmq.pe.servicio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.RespuestaEstudiante;

import java.util.List;

public interface RespuestaEstudianteService {

    List<RespuestaEstudiante> getAll();

    List<RespuestaEstudiante> getByCodEvaluacion(Long codEvaluacion);

    List<RespuestaEstudiante> getByCodEstudiante(Long codEstudiante);

    List<RespuestaEstudiante> getByCodEvaluacionAndCodEstudiante(Long codEvaluacion, Long codEstudiante);

    RespuestaEstudiante getById(Long codRespuestaEstudiante);

    List<RespuestaEstudiante> saveAllRespuestas(List<RespuestaEstudiante> respuestasEstudiante);

    Boolean esEncuestaContestada(Long codEstudiante, Long codEvaluacion);

    void delete(Long codRespuestaEstudiante);
    List<RespuestaEstudiante> findByPreguntaAndCursoAndRespuesta(Long codPregunta, Long codCurso, Boolean respuesta);
    List<RespuestaEstudiante> findByPreguntaAndCurso(Long codPregunta, Long codCurso);


}
