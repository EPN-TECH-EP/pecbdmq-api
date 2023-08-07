package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface InstructoresCursoRepository extends JpaRepository<InstructoresCurso, Long> {

	@Procedure("cbdmq.get_cursos_instructor")
	List<InstructoresCurso> findCursosInstructor(@Param("p_cod_instructor") Long codInstructor);

}