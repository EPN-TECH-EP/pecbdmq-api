package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;

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

	List<Curso> findByEstado(String estado);

	@Query(value = "select ec.* from cbdmq.esp_curso ec, cbdmq.esp_catalogo_cursos ecc, cbdmq.esp_tipo_curso etc " +
			"where ec.cod_catalogo_cursos = ecc.cod_catalogo_cursos " +
			"and ecc.cod_tipo_curso = etc.cod_tipo_curso " +
			"and etc.cod_tipo_curso = :codigoTipoCurso", nativeQuery = true)
	List<Curso> findByCodigoTipoCurso(Integer codigoTipoCurso);

	List<Curso> findByCodCatalogoCursos(Long codigoCatalogoCurso);
	List<Curso> findAllByEstadoContainsIgnoreCaseOrderByNombre(String estado);
	@Query("SELECT c FROM Curso c ORDER BY c.nombre ASC")
	List<Curso> findAllOrderedByName();


}
