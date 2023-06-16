package epntech.cbdmq.pe.repositorio.admin.especializacion;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCurso;


public interface ConvocatoriaCursoRepository extends JpaRepository<ConvocatoriaCurso, Long> {

	Optional<ConvocatoriaCurso> findByNombreConvocatoriaIgnoreCase(String nombre);
	
	@Procedure(value = "cbdmq.get_id")
	String getId(String proceso);
	
	@Query(value = "select a.* from cbdmq.gen_convocatoria a where a.cod_curso_especializacion = :codCursoEspecializacion and upper(a.estado) = 'ACTIVO'", nativeQuery=true)
	Optional<ConvocatoriaCurso> getConvocatoriaByCodCursoEspecializacion(Long codCursoEspecializacion);
	
	@Procedure(value = "cbdmq.valida_convocatoria_activa_especializacion")
	Boolean validaConvocatoriaCursoActiva(Long codCursoEspecializacion);
	
}
