package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;

public interface CursoInstructorRepository extends JpaRepository<CursoInstructor, Long> {

	@Query(nativeQuery = true, name = "Instructor.findInstructoresCurso")
	List<InstructoresCurso> findInstructoresCurso(@Param("codCurso") Long codCurso);
	
	Optional<CursoInstructor> findByCodInstructorAndCodCursoEspecializacion(Integer codInstructor, Long codCursoEspecializacion);

	@Query(value = "select eci.* from cbdmq.esp_curso_instructor eci, cbdmq.gen_instructor gi, cbdmq.gen_usuario gu " +
			"where eci.cod_instructor = gi.cod_instructor " +
			"and gi.cod_datos_personales = gu.cod_datos_personales " +
			"and gu.cod_usuario = :codUsuario", nativeQuery = true)
	List<CursoInstructor> findByCodUsuario(Integer codUsuario);
	
}
