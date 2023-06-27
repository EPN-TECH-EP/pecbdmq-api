package epntech.cbdmq.pe.repositorio.admin.especializacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;

public interface InstructoresCursoRepository extends JpaRepository<InstructoresCurso, Long> {

	@Query(value = "select ci.cod_instructor, dp.cedula, dp.nombre, dp.apellido, \r\n"
		+ "c.cod_curso_especializacion, cc.nombre_catalogo_curso,\r\n"
		+ "ti.cod_tipo_instructor, ti.nombre_tipo_instructor \r\n"
		+ "from cbdmq.esp_curso_instructor ci, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc, \r\n"
		+ "cbdmq.gen_instructor i, cbdmq.gen_dato_personal dp, cbdmq.gen_tipo_instructor ti \r\n"
		+ "where ci.cod_curso_especializacion = c.cod_curso_especializacion \r\n"
		+ "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos \r\n"
		+ "and ci.cod_instructor = i.cod_instructor \r\n"
		+ "and i.cod_datos_personales = dp.cod_datos_personales \r\n"
		+ "and ci.cod_tipo_instructor = ti.cod_tipo_instructor\r\n"
		+ "and upper(ci.estado) = 'ACTIVO'\r\n"
		+ "and upper(c.estado) = 'ACTIVO'\r\n"
		+ "and upper(cc.estado) = 'ACTIVO'\r\n"
		+ "and upper(dp.estado) = 'ACTIVO' \r\n"
		+ "and ci.cod_curso_especializacion = :codCurso", nativeQuery = true)
	List<InstructoresCurso> findInstructoresCurso(Long codCurso);
	
	@Query(value = "select ci.cod_instructor, dp.cedula, dp.nombre, dp.apellido, \r\n"
			+ "c.cod_curso_especializacion, cc.nombre_catalogo_curso,\r\n"
			+ "ti.cod_tipo_instructor, ti.nombre_tipo_instructor \r\n"
			+ "from cbdmq.esp_curso_instructor ci, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc, \r\n"
			+ "cbdmq.gen_instructor i, cbdmq.gen_dato_personal dp, cbdmq.gen_tipo_instructor ti \r\n"
			+ "where ci.cod_curso_especializacion = c.cod_curso_especializacion \r\n"
			+ "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos \r\n"
			+ "and ci.cod_instructor = i.cod_instructor \r\n"
			+ "and i.cod_datos_personales = dp.cod_datos_personales \r\n"
			+ "and ci.cod_tipo_instructor = ti.cod_tipo_instructor\r\n"
			+ "and upper(ci.estado) = 'ACTIVO'\r\n"
			+ "and upper(c.estado) = 'ACTIVO'\r\n"
			+ "and upper(cc.estado) = 'ACTIVO'\r\n"
			+ "and upper(dp.estado) = 'ACTIVO' \r\n"
			+ "and i.cod_instructor = :codInstructor", nativeQuery = true)
		List<InstructoresCurso> findCursosInstructor(Long codInstructor);
}
