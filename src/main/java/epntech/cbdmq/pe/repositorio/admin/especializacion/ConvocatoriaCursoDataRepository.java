package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCursoData;

public interface ConvocatoriaCursoDataRepository extends JpaRepository<ConvocatoriaCursoData, Long> {

	@Query(value = "select c.cod_convocatoria, c.fecha_inicio_convocatoria , c.fecha_fin_convocatoria, c.hora_inicio_convocatoria, c.hora_fin_convocatoria, "
			+ "cu.email_notificacion, ca.nombre_catalogo_curso "
			+ "from cbdmq.gen_convocatoria c, cbdmq.esp_curso cu, cbdmq.esp_catalogo_cursos ca "
			+ "where c.cod_curso_especializacion = cu.cod_curso_especializacion "
			+ "and cu.cod_catalogo_cursos = ca.cod_catalogo_cursos " + "and c.cod_convocatoria = :codConvocatoria "
			+ "and upper(c.estado) = 'ACTIVO' " + "and upper(cu.estado) = 'ACTIVO' "
			+ "and upper(ca.estado) = 'ACTIVO'", nativeQuery = true)
	Optional<ConvocatoriaCursoData> getConvocatoriaCurso(Long codConvocatoria);

}
