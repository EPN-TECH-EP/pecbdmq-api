package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import epntech.cbdmq.pe.dominio.util.DatosInscripcionEsp;
import org.springframework.data.domain.Pageable;
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

	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripciones")
	List<InscripcionDatosEspecializacion> getAllInscripciones(Pageable pageable);

	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcionesByUsuario")
	List<InscripcionDatosEspecializacion> getAllInscripcionesByUsuario(@Param("codUsuario") Long codUsuario, Pageable pageable);
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcion")
	Optional<InscripcionDatosEspecializacion> getInscripcion(@Param("codInscripcion") Long codInscripcion);
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcionDatos")
	Optional<InscripcionEstudianteDatosEspecializacion> getInscripcionEstudiante(@Param("codInscripcion") Long codInscripcion);
	
	@Procedure(value = "cbdmq.cumple_porcentaje_min_inscritos_curso_esp")
	Boolean cumplePorcentajeMinimoInscritosCurso(long codCurso);
	@Procedure(value = "cbdmq.cumple_porcentaje_min_aprobados_pruebas_curso_esp")
	Boolean cumplePorcentajeMinimoAprobadosPruebasCurso(long codCurso);
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcionPorCurso")
	List<InscripcionDatosEspecializacion> getInscripcionByCurso(@Param("codCurso") Long codCurso);
	
	@Query(nativeQuery = true, name = "InscripcionEsp.findInscripcionValidaPorCurso")
	List<InscritosEspecializacion> getInscripcionesValidasByCurso(@Param("codCurso") Long codCurso);
	@Query(nativeQuery = true, name = "InscripcionEsp.getListasByEstado")
	Set<InscripcionDatosEspecializacion> getInscripcionesByCursoEstado(@Param("codCurso") Long codCurso, @Param("estado") String estado);
	@Query(nativeQuery = true, name = "DatosInscripcionEsp.aprobadosPruebas")
	List<DatosInscripcionEsp> getAprobadosPruebas(@Param("codCurso") Integer codCurso);
	@Query(nativeQuery = true, name = "DatosInscripcionEsp.aprobadosPruebasBySubtipoPrueba")
	List<DatosInscripcionEsp> getAprobadosPruebasBySubtipoPrueba(@Param("codCurso") Integer codCurso, @Param("codSubtipoPrueba") Integer codSubtipoPrueba);
	
	@Query(value = "select i.* "
			+ "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.esp_curso c "
			+ "where i.cod_estudiante = e.cod_estudiante "
			+ "and i.cod_curso_especializacion = c.cod_curso_especializacion "
			+ "and upper(c.estado) = 'ACTIVO' "
			+ "and upper(e.estado) = 'ACTIVO' "
			+ "and upper(i.estado) = 'VALIDO' "
			+ "and i.cod_inscripcion = :codInscripcion", nativeQuery = true)
	Optional<InscripcionEsp> getByCodInscripcion(Long codInscripcion);
}

