package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoEstado;
import org.springframework.data.repository.query.Param;

public interface CursoEstadoRepository extends JpaRepository<CursoEstado, Long> {
	
	@Query(value = "select a.* "
			+ "from cbdmq.esp_curso_estado a "
			+ "where a.cod_curso_especializacion = :codCursoEspecializacion "
			+ "and a.cod_catalogo_estados = :codCatalogoEstados "
			+ "and upper(a.estado) = 'ACTIVO'", nativeQuery=true)
	Optional<CursoEstado> getByCursoYCatalogoEstado(Long codCursoEspecializacion, Long codCatalogoEstados);
	
	@Query(value = "select a.* "
			+ "from cbdmq.esp_curso_estado a "
			+ "where a.cod_curso_especializacion = :codCursoEspecializacion "
			+ "and a.orden = :orden "
			+ "and upper(a.estado) = 'ACTIVO'", nativeQuery=true)
	Optional<CursoEstado> getByCursoYOrden(Long codCursoEspecializacion, Integer orden);
	List<CursoEstado> findAllByCodTipoCurso(Long codTipoCurso);
	@Query(value = "select gce.nombre_catalogo_estados from cbdmq.gen_catalogo_estados gce\n" +
			"left join cbdmq.esp_curso_estado ece on gce.cod_catalogo_estados = ece.cod_catalogo_estados\n" +
			"left join cbdmq.esp_catalogo_cursos ecc on ece.cod_tipo_curso = ecc.cod_tipo_curso\n" +
			"left join cbdmq.esp_curso ec on ecc.cod_catalogo_cursos = ec.cod_catalogo_cursos \n" +
			"where ec.cod_curso_especializacion = :codCurso\n" +
			"and gce.nombre_catalogo_estados = ec.estado \n", nativeQuery = true)
	String getEstadoByCurso(@Param("codCurso") Long codCurso);
	@Query(value = "select ece.* from cbdmq.esp_curso_estado ece\n" +
			"left join cbdmq.gen_catalogo_estados gce on gce.cod_catalogo_estados = ece.cod_catalogo_estados\n" +
			"left join cbdmq.esp_catalogo_cursos ecc on ece.cod_tipo_curso = ecc.cod_tipo_curso\n" +
			"left join cbdmq.esp_curso ec on ecc.cod_catalogo_cursos = ec.cod_catalogo_cursos \n" +
			"where ec.cod_curso_especializacion = :codCurso\n" +
			"and gce.nombre_catalogo_estados = :nombreEstado", nativeQuery = true)
	CursoEstado getEstadoByCurso(@Param("codCurso") Long codCurso, @Param("nombreEstado") String nombreEstado);
	@Query(value = "select * from cbdmq.get_next_state_curso(:idCurso, :idCursoEstado)", nativeQuery=true)
	String updateState(@Param("idCurso")Integer idCurso, @Param("idCursoEstado")Integer idCursoEstado);
		@Query(value = "select * from cbdmq.get_next_state_process_curso(:idCurso)", nativeQuery=true)
	Integer updateNextState(@Param("idCurso")Integer idCurso);
		@Query(value = "select * from cbdmq.get_before_state_process_curso(:idCurso)", nativeQuery=true)
	String getBeforeState(@Param("idCurso")Integer idCurso);





}
