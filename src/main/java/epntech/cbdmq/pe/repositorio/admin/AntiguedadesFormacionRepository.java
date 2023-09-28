package epntech.cbdmq.pe.repositorio.admin;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.AntiguedadesFormacion;
import org.springframework.data.repository.query.Param;

public interface AntiguedadesFormacionRepository extends JpaRepository<AntiguedadesFormacion, String> {

	@Query(value = "select e.codigo_unico_estudiante, dp.cedula, dp.nombre, dp.apellido, dp.correo_personal, nf.nota_final "
			+ "from cbdmq.gen_nota_formacion_final nf, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp "
			+ "where nf.cod_estudiante = e.cod_estudiante "
			+ "and e.cod_datos_personales = dp.cod_datos_personales "
			+ "and nf.aprobado = true "
			+ "and upper(e.estado) = 'ACTIVO' "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and nf.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "order by nf.nota_final desc", nativeQuery=true)
	Set<AntiguedadesFormacion> getAntiguedadesFormacion();
	@Query(value = "select ge.codigo_unico_estudiante, gdp.cedula, gdp.nombre, gdp.apellido, gdp.correo_personal, ene.nota_final_especializacion as nota_final\n" +
			"from cbdmq.esp_nota_especializacion ene \n" +
			"left join cbdmq.esp_inscripcion ei on ene.cod_inscripcion = ei.cod_inscripcion \n" +
			"left join cbdmq.gen_estudiante ge on ei.cod_estudiante = ge.cod_estudiante \n" +
			"left join cbdmq.gen_dato_personal gdp on ge.cod_datos_personales = gdp.cod_datos_personales \n" +
			"left join cbdmq.esp_curso ec on ei.cod_curso_especializacion = ec.cod_curso_especializacion \n" +
			"where ec.cod_curso_especializacion = :codCurso\n" +
			"AND (ene.nota_final_especializacion >= ec.nota_minima OR COALESCE(ene.nota_supletorio, 0) >= ec.nota_minima)" +
			"order by ene.nota_final_especializacion desc", nativeQuery=true)
	Set<AntiguedadesFormacion> getAntiguedadesEspecializacion(@Param("codCurso") Long codCurso);
	@Query(value = "select ge.codigo_unico_estudiante, gdp.cedula, gdp.nombre, gdp.apellido, gdp.correo_personal, ene.nota_final_especializacion as nota_final\n" +
			"from cbdmq.esp_nota_especializacion ene \n" +
			"left join cbdmq.esp_inscripcion ei on ene.cod_inscripcion = ei.cod_inscripcion \n" +
			"left join cbdmq.gen_estudiante ge on ei.cod_estudiante = ge.cod_estudiante \n" +
			"left join cbdmq.gen_dato_personal gdp on ge.cod_datos_personales = gdp.cod_datos_personales \n" +
			"left join cbdmq.esp_curso ec on ei.cod_curso_especializacion = ec.cod_curso_especializacion \n" +
			"where ec.cod_curso_especializacion = :codCurso\n" +
			"AND (ene.nota_final_especializacion < ec.nota_minima AND COALESCE(ene.nota_supletorio, 0) < ec.nota_minima)" +
			"order by ene.nota_final_especializacion desc", nativeQuery=true)
	Set<AntiguedadesFormacion> getReprobadosEspecializacion(@Param("codCurso") Long codCurso);

}
