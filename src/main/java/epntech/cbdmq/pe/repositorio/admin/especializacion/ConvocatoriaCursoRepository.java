package epntech.cbdmq.pe.repositorio.admin.especializacion;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCurso;
import epntech.cbdmq.pe.dominio.util.ListaRequisitos;


public interface ConvocatoriaCursoRepository extends JpaRepository<ConvocatoriaCurso, Long> {

	Optional<ConvocatoriaCurso> findByNombreConvocatoriaIgnoreCase(String nombre);
	
	@Procedure(value = "cbdmq.get_id")
	String getId(String proceso);
	
	@Query(value = "select a.* from cbdmq.gen_convocatoria a where a.cod_curso_especializacion = :codCursoEspecializacion and upper(a.estado) = 'ACTIVO'", nativeQuery=true)
	Optional<ConvocatoriaCurso> getConvocatoriaByCodCursoEspecializacion(Long codCursoEspecializacion);
	
	@Procedure(value = "cbdmq.valida_convocatoria_activa_especializacion")
	Boolean validaConvocatoriaCursoActiva(Long codCursoEspecializacion);
	
	@Query(value = "select a.* from cbdmq.gen_convocatoria a where UPPER(a.estado) = 'ACTIVO' and a.cod_curso_especializacion is not null", nativeQuery=true)
	List<ConvocatoriaCurso> getAll();
	
	@Query(value = "select a.* from cbdmq.gen_convocatoria a where UPPER(a.estado) = 'ACTIVO' and a.cod_curso_especializacion is not null and a.cod_convocatoria = :codConvocatoria", nativeQuery=true)
	Optional<ConvocatoriaCurso> getConvocatoriaCursoxId(Long codConvocatoria);
	
	@Query(nativeQuery = true, name = "ConvocatoriaCurso.findRequisitos")
	List<ListaRequisitos> findRequisitosCurso(@Param("codConvocatoria") Long codConvocatoria);
	
}
