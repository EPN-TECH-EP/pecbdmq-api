package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProInscripcion;

import java.util.Optional;

public interface ProInscripcionRepository extends ProfesionalizacionRepository<ProInscripcion, Integer> {

    Optional<ProInscripcion> findByCodEstudiante(int codEstudiante);
    Optional<ProInscripcion> findByCodEstudianteAndCodConvocatoria(int codEstudiante, int codConvocatoria);
}
