package epntech.cbdmq.pe.repositorio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long>{

    Optional<Evaluacion> findByNombreIgnoreCase(String nombre);

    List<Evaluacion> findByEstadoIgnoreCase(String estado);

}
