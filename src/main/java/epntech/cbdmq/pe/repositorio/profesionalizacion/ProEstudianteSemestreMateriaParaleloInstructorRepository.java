package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParaleloInstructor;

import java.util.Optional;

public interface ProEstudianteSemestreMateriaParaleloInstructorRepository extends ProfesionalizacionRepository<ProSemestreMateriaParaleloInstructor, Integer> {

    Optional<ProSemestreMateriaParaleloInstructor> findByCodPeriodoSemestreMateriaParaleloAndCodInstructor(int codPeriodoSemestreMateriaParalelo, int CodInstructor);
}
