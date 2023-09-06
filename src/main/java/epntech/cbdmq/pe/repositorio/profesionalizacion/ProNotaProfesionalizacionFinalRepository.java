package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacionFinal;

import java.util.Optional;

public interface ProNotaProfesionalizacionFinalRepository extends ProfesionalizacionRepository<ProNotaProfesionalizacionFinal, Integer> {

    Optional<ProNotaProfesionalizacionFinal> findByCodEstudianteSemestreAndCodEstudianteAndCodSemestre(int codEstudianteSemestre, int codEstudiante, int codSemestre);


}
