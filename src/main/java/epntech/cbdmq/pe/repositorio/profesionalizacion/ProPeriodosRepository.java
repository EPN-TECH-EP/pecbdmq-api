package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodos;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProPeriodosRepository extends ProfesionalizacionRepository<ProPeriodos, Integer> {

    Optional<ProPeriodos> findByNombrePeriodoIgnoreCase(String nombrePeriodo);
    List<ProPeriodos> findByEstado(String estado);

    @Query(value = "select pp.* from cbdmq.pro_periodo pp " +
            "inner join cbdmq.pro_convocatoria pc on pp.cod_periodo = pc.cod_periodo " +
            "where pc.cod_convocatoria = :codConvocatoria limit 1", nativeQuery = true)
    ProPeriodos findByConvocatoria(Integer codConvocatoria);
    List<ProPeriodos> findByFechaInicioBetween(Date startDate, Date endDate);
}
