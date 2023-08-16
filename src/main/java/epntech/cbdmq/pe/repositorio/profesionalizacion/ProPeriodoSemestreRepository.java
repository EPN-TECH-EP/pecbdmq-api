package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoSemestre;

import java.util.Optional;

public interface ProPeriodoSemestreRepository extends ProfesionalizacionRepository<ProPeriodoSemestre, Integer> {

    Optional<ProPeriodoSemestre> findByCodPeriodoAndCodSemestre(Integer codPeriodo, Integer codSemestre);
}
