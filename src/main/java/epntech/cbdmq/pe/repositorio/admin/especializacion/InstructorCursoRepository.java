package epntech.cbdmq.pe.repositorio.admin.especializacion;

import epntech.cbdmq.pe.dto.especializacion.CursoInstructorResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorCursoRepository extends JpaRepository<CursoInstructorResponse, Long> {

	@Procedure("cbdmq.get_instructores_curso")
	List<CursoInstructorResponse> findInstructoresCurso(@Param("p_cod_curso") Long codCurso);

}