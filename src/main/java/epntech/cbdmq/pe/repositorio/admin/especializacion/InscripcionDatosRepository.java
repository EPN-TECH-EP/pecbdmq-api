package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionDatosEsp;

@Repository
public interface InscripcionDatosRepository extends JpaRepository<InscripcionDatosEsp, Long> {

	@Query(value = "select i.cod_inscripcion, dp.cedula, dp.nombre, dp.apellido, cc.nombre_catalogo_curso, "
			+ "i.fecha_inscripcion, c.fecha_inicio_curso, c.fecha_fin_curso "
			+ "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
			+ "where i.cod_estudiante = e.cod_estudiante " + "and e.cod_datos_personales = dp.cod_datos_personales "
			+ "and i.cod_curso_especializacion = c.cod_curso_especializacion "
			+ "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos "
			+ "and upper(e.estado) = 'ACTIVO' "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(c.estado) = 'ACTIVO' "
			+ "and upper(cc.estado) = 'ACTIVO' "
			+ "and upper(i.estado) in ('ACTIVO', 'INSCRITO', 'VALIDO', 'APROBADO') "
			+ "and i.cod_inscripcion = :codInscripcion", nativeQuery = true)
	Optional<InscripcionDatosEsp> findByInscripcion(Long codInscripcion);
}
