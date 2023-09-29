package epntech.cbdmq.pe.repositorio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.TipoEvaluacion;
import epntech.cbdmq.pe.dominio.util.ComponenteTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoEvaluacionRepository extends JpaRepository<TipoEvaluacion, Long> {

    Optional<TipoEvaluacion> findByNombreIgnoreCase(String nombre);

    List<TipoEvaluacion> findByEstadoIgnoreCase(String estado);

}
