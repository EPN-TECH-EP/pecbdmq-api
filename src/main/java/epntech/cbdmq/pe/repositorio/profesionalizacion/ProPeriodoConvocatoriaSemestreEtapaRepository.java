package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoConvocatoriaSemestreEtapa;

import java.util.Optional;

public interface ProPeriodoConvocatoriaSemestreEtapaRepository extends ProfesionalizacionRepository<ProPeriodoConvocatoriaSemestreEtapa, Integer> {

    Optional<ProPeriodoConvocatoriaSemestreEtapa> findByCodPeriodo(int codPeriodo);

}
