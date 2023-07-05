package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.especializacion.ValidaRequisitos;
import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosDatos;

public interface ValidaRequisitosRepository extends JpaRepository<ValidaRequisitos, Long> {

	List<ValidaRequisitos> findByCodEstudianteAndCodCursoEspecializacion(Long codEstudiante, Long codCursoEspecializacion);
	
	@Query(nativeQuery = true, name = "Requisito.findRequisitosPorEstudiante")
	List<ValidacionRequisitosDatos> findRequisitosPorEstudiante(@Param("codEstudiante") Long codEstudiante, @Param("codCursoEspecializacion") Long codCursoEspecializacion);
	
	@Procedure(value = "cbdmq.cumple_requisitos_curso_esp")
	Boolean cumpleRequisitosCurso(Long codCurso, Long codEstudiante);
}
