package epntech.cbdmq.pe.repositorio.admin;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.AntiguedadesDatos;

public interface AntiguedadesRepository extends JpaRepository<AntiguedadesDatos, Integer> {
	
	@Query(value = "SELECT cod_postulante, id_postulante, cedula, nombre, apellido, avg(nota_promedio_final) as nota_promedio_final FROM "
			+ "(select p.cod_postulante, p.id_postulante, dp.cedula, dp.nombre, dp.apellido, r.nota_promedio_final "
			+ "from cbdmq.gen_resultado_prueba_nofisica r, cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp, cbdmq.gen_prueba_detalle pr "
			+ "where r.cod_postulante = p.cod_postulante "
			+ "and p.cod_datos_personales = dp.cod_datos_personales "
			+ "and r.cod_prueba_detalle = pr.cod_prueba_detalle "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(p.estado) in ('ACTIVO', 'VALIDO', 'VALIDO MUESTRA') "
			+ "and upper(r.estado) = 'ACTIVO' "
			+ "and r.cumple_prueba = true "
			+ "and pr.tiene_puntaje = true "
			+ "and dp.genero = 'FEMENINO' "
			+ "and pr.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "group by p.cod_postulante, p.id_postulante, dp.cedula, dp.nombre, dp.apellido "
			+ "union "
			+ "select p.cod_postulante, p.id_postulante, dp.cedula, dp.nombre, dp.apellido, r.nota_promedio_final "
			+ "from cbdmq.gen_resultado_prueba_fisica r, cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp, cbdmq.gen_prueba_detalle pr "
			+ "where r.cod_postulante = p.cod_postulante "
			+ "and p.cod_datos_personales = dp.cod_datos_personales "
			+ "and r.cod_prueba_detalle = pr.cod_prueba_detalle "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(p.estado) in ('ACTIVO', 'VALIDO', 'VALIDO MUESTRA') "
			+ "and upper(r.estado) = 'ACTIVO' "
			+ "and dp.genero = 'FEMENINO' "
			+ "and pr.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "group by p.cod_postulante, p.id_postulante, dp.cedula, dp.nombre, dp.apellido) RESULTADO "
			+ "group by cod_postulante, id_postulante, cedula, nombre, apellido "
			+ "order by nota_promedio_final desc "
			+ "limit (select c.cupo_mujeres from cbdmq.gen_convocatoria c where c.cod_periodo_academico = cbdmq.get_pa_activo() and upper(c.estado) = 'ACTIVO')", nativeQuery=true)
	Set<AntiguedadesDatos> getAntiguedadesFemenino();
	
	@Query(value = "SELECT cod_postulante, id_postulante, cedula, nombre, apellido, avg(nota_promedio_final) as nota_promedio_final FROM "
			+ "(select p.cod_postulante, p.id_postulante, dp.cedula, dp.nombre, dp.apellido, r.nota_promedio_final "
			+ "from cbdmq.gen_resultado_prueba_nofisica r, cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp, cbdmq.gen_prueba_detalle pr "
			+ "where r.cod_postulante = p.cod_postulante "
			+ "and p.cod_datos_personales = dp.cod_datos_personales "
			+ "and r.cod_prueba_detalle = pr.cod_prueba_detalle "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(p.estado) in ('ACTIVO', 'VALIDO', 'VALIDO MUESTRA') "
			+ "and upper(r.estado) = 'ACTIVO' "
			+ "and r.cumple_prueba = true "
			+ "and pr.tiene_puntaje = true "
			+ "and dp.genero = 'MASCULINO' "
			+ "and pr.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "group by p.cod_postulante, p.id_postulante, dp.cedula, dp.nombre, dp.apellido "
			+ "union "
			+ "select p.cod_postulante, p.id_postulante, dp.cedula, dp.nombre, dp.apellido, r.nota_promedio_final "
			+ "from cbdmq.gen_resultado_prueba_fisica r, cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp, cbdmq.gen_prueba_detalle pr "
			+ "where r.cod_postulante = p.cod_postulante "
			+ "and p.cod_datos_personales = dp.cod_datos_personales "
			+ "and r.cod_prueba_detalle = pr.cod_prueba_detalle "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(p.estado) in ('ACTIVO', 'VALIDO', 'VALIDO MUESTRA') "
			+ "and upper(r.estado) = 'ACTIVO' "
			+ "and dp.genero = 'MASCULINO' "
			+ "and pr.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "group by p.cod_postulante, p.id_postulante, dp.cedula, dp.nombre, dp.apellido) RESULTADO "
			+ "group by cod_postulante, id_postulante, cedula, nombre, apellido "
			+ "order by nota_promedio_final desc "
			+ "limit (select c.cupo_hombres from cbdmq.gen_convocatoria c where c.cod_periodo_academico = cbdmq.get_pa_activo() and upper(c.estado) = 'ACTIVO')", nativeQuery=true)
	Set<AntiguedadesDatos> getAntiguedadesMasculino();
	

}
