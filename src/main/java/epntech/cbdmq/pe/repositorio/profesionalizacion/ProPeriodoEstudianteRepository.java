package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoEstudiante;

import java.util.Optional;

public interface ProPeriodoEstudianteRepository extends ProfesionalizacionRepository<ProPeriodoEstudiante, Integer>{
    Optional<ProPeriodoEstudiante> findByCodPeriodoAndCodDatosPersonales(Integer codPeriodo, Integer codDatosPersonales);

}
