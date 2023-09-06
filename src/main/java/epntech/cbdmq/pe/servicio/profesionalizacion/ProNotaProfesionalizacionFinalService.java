package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacionFinal;

import java.util.Optional;

public interface ProNotaProfesionalizacionFinalService extends ProfesionalizacionService<ProNotaProfesionalizacionFinal, Integer> {
    Optional<ProNotaProfesionalizacionFinal> findByCodEstudianteSemestreAndCodEstudianteAndCodSemestre(Integer codEstudianteSemestre, Integer codEstudiante, Integer codSemestre);

}
