package epntech.cbdmq.pe.repositorio.admin.especializacion;

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
}
