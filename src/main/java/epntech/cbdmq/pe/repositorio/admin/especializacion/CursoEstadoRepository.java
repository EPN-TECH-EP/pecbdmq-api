package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoEstado;

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

}
