package epntech.cbdmq.pe.repositorio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.PreguntaTipoEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public interface PreguntaTipoEvaluacionRepository extends JpaRepository<PreguntaTipoEvaluacion, Long> {

    Optional<PreguntaTipoEvaluacion> findByPreguntaIgnoreCase(String pregunta);

    List<PreguntaTipoEvaluacion> findByEstadoIgnoreCase(String estado);

    List<PreguntaTipoEvaluacion> findByCodTipoEvaluacion(Long codTipoEvaluacion);

    @Query(value = "select pte .* from cbdmq.preguntas_tipo_evaluacion pte \n" +
            "left join cbdmq.tipos_evaluaciones te on pte.cod_tipo_evaluacion = te.cod_tipo_evaluacion \n" +
            "where te.nombre ilike :nombreTipoEvaluacion", nativeQuery = true)
    List<PreguntaTipoEvaluacion> findByNombreTipoEvaluacion(String nombreTipoEvaluacion);
}
