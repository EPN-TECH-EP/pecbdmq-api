package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;

public interface ResultadoPruebasDatosRepository extends JpaRepository<ResultadosPruebasDatos, Integer> {

	
	@Query(value = "select p.id_postulante, dp.cedula, dp.nombre, dp.apellido, r.nota_promedio_final, "
			+ "CASE WHEN r.cumple_prueba = true THEN 'SI' "
			+ "    WHEN r.cumple_prueba = false THEN 'NO' "
			+ "    WHEN r.cumple_prueba is null THEN '--' "
			+ "END cumple_prueba "
			+ "from cbdmq.gen_resultado_prueba_nofisica r, cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp, cbdmq.gen_prueba_detalle pr "
			+ "where r.cod_postulante = p.cod_postulante "
			+ "and p.cod_datos_personales = dp.cod_datos_personales "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(p.estado) in ('ACTIVO', 'VALIDO', 'VALIDO MUESTRA') "
			+ "and upper(r.estado) = 'ACTIVO' "
			+ "and r.cumple_prueba = true "
			+ "and pr.cod_prueba_detalle = :prueba "
			+ "and pr.cod_periodo_academico = cbdmq.get_pa_activo()", nativeQuery=true)
	List<ResultadosPruebasDatos> getResultados(Integer prueba);
	
	@Query(value = "select * from cbdmq.get_approved_applicants(:prueba)", nativeQuery=true)
	List<ResultadosPruebasDatos> get_approved_applicants(Integer prueba);
}
