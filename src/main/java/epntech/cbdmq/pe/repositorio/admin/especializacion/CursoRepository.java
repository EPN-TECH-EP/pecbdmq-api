package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import org.springframework.data.repository.query.Param;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	@Procedure(value = "cbdmq.update_estado_curso")
	int updateEstadoProceso(Long estado, Long codCurso);
	
	@Query(value = "select a.* "
			+ "from cbdmq.esp_curso a "
			+ "where a.aprueba_creacion_curso = true "
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

	@Query(value = "select\n" +
			" ec.*\n" +
			"from\n" +
			" cbdmq.esp_curso ec,\n" +
			" cbdmq.esp_curso_instructor eci,\n" +
			" cbdmq.gen_instructor gi,\n" +
			" cbdmq.gen_usuario gu \n" +
			"where\n" +
			" gu.cod_usuario = :codUsuario \n" +
			" and ec.cod_curso_especializacion = eci.cod_curso_especializacion\n" +
			" and eci.cod_instructor  = gi.cod_instructor \n" +
			" and gu.cod_datos_personales = gi.cod_datos_personales \n" +
			" and ec.estado = :estado and eci.estado <> 'ELIMINADO'", nativeQuery = true)
	List<Curso> findByInstructorAndEstado(Integer codUsuario, String estado);

	List<Curso> findByCodCatalogoCursos(Long codigoCatalogoCurso);
	List<Curso> findAllByEstadoContainsIgnoreCase(String estado, Sort sort);
	@Query("SELECT c FROM Curso c ORDER BY c.nombre ASC")
	List<Curso> findAllOrderedByName();

	@Query(value = "select ec.* from cbdmq.esp_curso ec \n" +
			"left join cbdmq.esp_inscripcion ei on ec.cod_curso_especializacion = ei.cod_curso_especializacion \n" +
			"where ei.cod_estudiante = :codEstudiante\n" +
			"and ec.estado ilike :estado\n", nativeQuery = true)
	List<Curso> findByEstudianteAndEstado(@Param("codEstudiante") Integer codEstudiante, @Param("estado")String estado);

	List<Curso> findByFechaInicioCursoBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
