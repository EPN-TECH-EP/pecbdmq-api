package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.PostulanteDatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionDatosEsp;

@Repository
public interface InscripcionDatosRepository extends JpaRepository<InscripcionDatosEsp, Long> {

	@Query(value = "select i.cod_inscripcion, dp.cedula, dp.nombre, dp.apellido, cc.nombre_catalogo_curso, e.cod_datos_personales, "
			+ "i.fecha_inscripcion, c.fecha_inicio_curso, c.fecha_fin_curso, dp.fecha_nacimiento, dp.tipo_sangre, dp.sexo, "
			+ "(select gc.nombre from cbdmq.gen_canton gc where UPPER(gc.estado) = 'ACTIVO' and gc.cod_canton = dp.cod_canton_nacimiento) canton_nacimiento, "
			+ "(select gc.nombre from cbdmq.gen_canton gc where UPPER(gc.estado) = 'ACTIVO' and gc.cod_canton = dp.cod_canton_residencia) canton_residencia, "
			+ "dp.calle_principal_residencia, dp.calle_secundaria_residencia, dp.numero_casa, "
			+ "dp.colegio, dp.tipo_nacionalidad, dp.num_telef_celular, "
			+ "dp.nombre_titulo_segundo_nivel, "
			+ "dp.pais_titulo_segundo_nivel, "
			+ "dp.ciudad_titulo_segundo_nivel, "
			+ "dp.merito_academico_descripcion, dp.merito_deportivo_descripcion, "
			+ "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_nacimiento) provincia_nacimiento, "
			+ "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_residencia) provincia_residencia, "
			+ "dp.correo_personal "
			+ "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
			+ "where i.cod_estudiante = e.cod_estudiante "
			+ "and e.cod_datos_personales = dp.cod_datos_personales "
			+ "and i.cod_curso_especializacion = c.cod_curso_especializacion "
			+ "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos "
			+ "and upper(e.estado) = 'ACTIVO' "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(c.estado) = 'ACTIVO' "
			+ "and upper(cc.estado) = 'ACTIVO' "
			+ "and upper(i.estado) in ('ACTIVO', 'INSCRITO', 'VALIDO', 'APROBADO', 'ASIGNADO') "
			+ "and i.cod_inscripcion = :codInscripcion", nativeQuery = true)
	Optional<InscripcionDatosEsp> findByInscripcion(Long codInscripcion);

}
