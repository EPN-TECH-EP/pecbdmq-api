package epntech.cbdmq.pe.repositorio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.RespuestaEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaEstudianteRepository extends JpaRepository<RespuestaEstudiante, Long> {
    
    List<RespuestaEstudiante> findByCodEvaluacion(Long codEvaluacion);

    List<RespuestaEstudiante> findByCodEstudiante(Long codEstudiante);

    List<RespuestaEstudiante> findByCodEvaluacionAndCodEstudiante(Long codEvaluacion, Long codEstudiante);

    List<RespuestaEstudiante> findByCodEstudianteAndCodEvaluacion(Long codEstudiante, Long codEvaluacion);
}
