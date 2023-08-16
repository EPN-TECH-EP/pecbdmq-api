package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodos;

import java.util.List;
import java.util.Optional;

public interface ProPeriodosRepository extends ProfesionalizacionRepository<ProPeriodos, Integer> {

    Optional<ProPeriodos> findByNombrePeriodoIgnoreCase(String nombrePeriodo);
    List<ProPeriodos> findByEstado(String estado);
}
