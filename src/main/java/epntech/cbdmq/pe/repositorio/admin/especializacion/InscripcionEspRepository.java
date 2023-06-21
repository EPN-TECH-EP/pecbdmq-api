package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;

public interface InscripcionEspRepository extends JpaRepository<InscripcionEsp, Long> {

	Optional<InscripcionEsp> findByCodEstudianteAndCodCursoEspecializacion(Long codEstudiante, Long codCursoEspecializacion); 
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripciones")
	List<InscripcionDatosEspecializacion> getAllInscripciones();
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcion")
	Optional<InscripcionDatosEspecializacion> getInscripcion(@Param("codInscripcion") Long codInscripcion);
}
