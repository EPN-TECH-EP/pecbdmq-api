package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoModulo;
import epntech.cbdmq.pe.dominio.util.CursoModuloDatosEspecializacion;

public interface CursoModuloRepository extends JpaRepository<CursoModulo, Long> {

	@Query(nativeQuery = true, name = "CursoModulo.findCursoModulo")
	List<CursoModuloDatosEspecializacion> getAllCursoModulo();
	
	List<CursoModulo> findByCodCursoEspecializacion(Long codCurso);
	
	Optional<CursoModulo> findByCodCursoEspecializacionAndCodEspModulo(Long codCursoEspecializacion, Long codEspModulo);
}

