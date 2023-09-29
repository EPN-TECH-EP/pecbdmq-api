package epntech.cbdmq.pe.repositorio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.RespuestaEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespuestaEstudianteRepository extends JpaRepository<RespuestaEstudiante, Long> {

    List<RespuestaEstudiante> findByCodEvaluacion(Long codEvaluacion);

    List<RespuestaEstudiante> findByCodEstudiante(Long codEstudiante);

    List<RespuestaEstudiante> findByCodEvaluacionAndCodEstudiante(Long codEvaluacion, Long codEstudiante);

    List<RespuestaEstudiante> findByCodEstudianteAndCodEvaluacion(Long codEstudiante, Long codEvaluacion);

    @Query(value = "select re.* from cbdmq.respuestas_estudiantes re \n" +
            "left join cbdmq.evaluaciones e on re.cod_evaluacion = e.cod_evaluacion \n" +
            "left join cbdmq.instructor_curso_evaluacion ice on e.cod_evaluacion = ice.cod_evaluacion\n" +
            "left join cbdmq.esp_curso_instructor eci on ice.cod_curso_instructor = eci.cod_instructor_curso\n" +
            "where re.cod_pregunta = :codPregunta\n" +
            "and eci.cod_curso_especializacion =:codCurso\n" +
            "and re.respuesta =:respuesta", nativeQuery = true)
    List<RespuestaEstudiante> findByPreguntaAndCursoAndRespuesta(Long codPregunta, Long codCurso, Boolean respuesta);
    @Query(value = "select re.* from cbdmq.respuestas_estudiantes re \n" +
            "left join cbdmq.evaluaciones e on re.cod_evaluacion = e.cod_evaluacion \n" +
            "left join cbdmq.instructor_curso_evaluacion ice on e.cod_evaluacion = ice.cod_evaluacion\n" +
            "left join cbdmq.esp_curso_instructor eci on ice.cod_curso_instructor = eci.cod_instructor_curso\n" +
            "where re.cod_pregunta = :codPregunta\n" +
            "and eci.cod_curso_especializacion =:codCurso\n", nativeQuery = true)
    List<RespuestaEstudiante> findByPreguntaAndCurso(Long codPregunta, Long codCurso);

}
