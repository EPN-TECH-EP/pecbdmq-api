package epntech.cbdmq.pe.repositorio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.PreguntaTipoEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreguntaTipoEvaluacionRepository extends JpaRepository<PreguntaTipoEvaluacion, Long> {
    
    Optional<PreguntaTipoEvaluacion> findByPreguntaIgnoreCase(String pregunta);
    
    List<PreguntaTipoEvaluacion> findByEstadoIgnoreCase(String estado);

    List<PreguntaTipoEvaluacion> findByCodTipoEvaluacion(Long codTipoEvaluacion);
}
