package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.llamamiento.RequisitosVerificados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.especializacion.ValidaRequisitos;
import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosDatos;

public interface ValidaRequisitosRepository extends JpaRepository<ValidaRequisitos, Long> {

	//List<ValidaRequisitos> findByCodEstudianteAndCodCursoEspecializacion(Long codEstudiante, Long codCursoEspecializacion);
	
//	@Query(nativeQuery = true, name = "Requisito.findRequisitosPorEstudiante")
//	List<ValidacionRequisitosDatos> findRequisitosPorEstudiante(@Param("codEstudiante") Long codEstudiante, @Param("codCursoEspecializacion") Long codCursoEspecializacion);

	@Query(nativeQuery = true, name = "Requisito.findRequisitosPorInscripcion")
	List<ValidacionRequisitosDatos> findRequisitosPorInscripcion(@Param("codInscripcion") Long codInscripcion);
	@Query(nativeQuery = true, name = "RequisitosVerificados.findForEspByDp")
	List<RequisitosVerificados> findRequisitosForEspByDp(@Param("codDP") Integer codDp);
	
	@Procedure(value = "cbdmq.cumple_requisitos_curso_esp")
	String cumpleRequisitosCurso(Long codInscripcion);
}

