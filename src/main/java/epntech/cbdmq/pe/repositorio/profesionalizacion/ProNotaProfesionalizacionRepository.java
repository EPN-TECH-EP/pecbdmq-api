package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacion;

import java.util.Optional;

public interface ProNotaProfesionalizacionRepository extends ProfesionalizacionRepository<ProNotaProfesionalizacion, Integer> {


    Optional<ProNotaProfesionalizacion> findByCodEstudianteSemestreMateriaParaleloAndCodInstructorAndCodMateriaAndCodEstudianteAndCodSemestre(int codEstudianteSemestreMateriaParalelo, int codInstructor, int codMateria, int codEstudiante, int codSemestre);


}
