package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;

public interface CursoInstructorRepository extends JpaRepository<CursoInstructor, Long> {

	@Query(nativeQuery = true, name = "Instructor.findInstructoresCurso")
	List<InstructoresCurso> findInstructoresCurso(@Param("codCurso") Long codCurso);
}
