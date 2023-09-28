package epntech.cbdmq.pe.repositorio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.PreguntaEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaEvaluacionRepository extends JpaRepository<PreguntaEvaluacion, Long> {
}
