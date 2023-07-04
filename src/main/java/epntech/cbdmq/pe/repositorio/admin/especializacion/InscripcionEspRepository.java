package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.dominio.util.InscripcionEstudianteDatosEspecializacion;
import epntech.cbdmq.pe.dominio.util.InscritosEspecializacion;

public interface InscripcionEspRepository extends JpaRepository<InscripcionEsp, Long> {

	Optional<InscripcionEsp> findByCodEstudianteAndCodCursoEspecializacion(Long codEstudiante, Long codCursoEspecializacion); 
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripciones")
	List<InscripcionDatosEspecializacion> getAllInscripciones();
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcion")
	Optional<InscripcionDatosEspecializacion> getInscripcion(@Param("codInscripcion") Long codInscripcion);
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcionDatos")
	Optional<InscripcionEstudianteDatosEspecializacion> getInscripcionEstudiante(@Param("codInscripcion") Long codInscripcion);
	
	@Procedure(value = "cbdmq.cumple_porcentaje_min_inscritos_curso_esp")
	Boolean cumplePorcentajeMinimoInscritosCurso(long codCurso);
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcionPorCurso")
	Optional<InscripcionDatosEspecializacion> getInscripcionByCurso(@Param("codCurso") Long codCurso);
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcionValidaPorCurso")
	List<InscritosEspecializacion> getInscripcionesValidasByCurso(@Param("codCurso") Long codCurso);
}
