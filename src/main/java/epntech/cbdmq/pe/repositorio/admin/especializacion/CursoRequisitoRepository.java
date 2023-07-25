package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoRequisito;
import jakarta.transaction.Transactional;

public interface CursoRequisitoRepository extends JpaRepository<CursoRequisito, Long> {
	
	@Transactional
	@Modifying
	@Query("delete from CursoRequisito a where a.codCursoEspecializacion = ?1")
	void deleteByCodCursoEspecializacion(Long codCursoEspecializacion);

	List<CursoRequisito> findByCodCursoEspecializacion(Long codCursoEspecializacion);

	Optional<CursoRequisito> findFirstByCodCursoEspecializacion(Long codCursoEspecializacion);

}
