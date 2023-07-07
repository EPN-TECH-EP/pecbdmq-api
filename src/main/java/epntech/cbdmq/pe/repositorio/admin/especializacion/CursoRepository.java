package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.util.CursoDatos;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	@Procedure(value = "cbdmq.update_estado_curso")
	int updateEstadoProceso(Long estado, Long codCurso);
	
	@Query(value = "select a.* "
			+ "from cbdmq.esp_curso a "
			+ "where a.aprueba_creacion_curso = true "
			+ "and upper(a.estado) = 'ACTIVO' "
			+ "and cod_curso_especializacion = :codCursoEspecializacion", nativeQuery=true)
	Optional<Curso> getCursoAprobado(Long codCursoEspecializacion);
	
	@Procedure(value = "cbdmq.cumple_porcentaje_min_aprobados_curso_esp")
	Boolean cumplePorcentajeMinimoAprobadosCurso(long codCurso);
	
	@Procedure(value = "cbdmq.valida_documentos_curso_especializacion")
    Integer validaDocumentosCursoEspecializacion(Long codCursoEspecializacion);
}
