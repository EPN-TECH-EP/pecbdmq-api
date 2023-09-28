package epntech.cbdmq.pe.repositorio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.InstructorCursoEvaluacion;
import epntech.cbdmq.pe.dominio.evaluaciones.InstructorCursoEvaluacionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstructorCursoEvaluacionRepository extends JpaRepository<InstructorCursoEvaluacion, InstructorCursoEvaluacionId> {

    @Query("SELECT ice FROM InstructorCursoEvaluacion ice WHERE ice.id.codCursoInstructor = ?1")
    InstructorCursoEvaluacion findByCodCursoInstructor(Long codCursoInstructor);

}
