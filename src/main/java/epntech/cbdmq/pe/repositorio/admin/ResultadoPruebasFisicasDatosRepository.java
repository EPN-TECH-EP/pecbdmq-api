package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasFisicasDatos;

public interface ResultadoPruebasFisicasDatosRepository extends JpaRepository<ResultadosPruebasFisicasDatos, Integer> {

	
	@Query(value = "select p.id_postulante, dp.cedula, dp.nombre, dp.apellido, COALESCE(r.resultado, 0) resultado, "
			+ "r.resultado_tiempo, COALESCE(r.nota_promedio_final, 0) nota_promedio_final "
			+ "from cbdmq.gen_resultado_prueba_fisica r, cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp, cbdmq.gen_prueba pr "
			+ "where r.cod_postulante = p.cod_postulante "
			+ "and p.cod_datos_personales = dp.cod_datos_personales "
			+ "and r.cod_prueba = pr.cod_prueba "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(p.estado) in ('ACTIVO', 'VALIDO', 'VALIDO MUESTRA') "
			+ "and upper(r.estado) = 'ACTIVO' "
			+ "and r.cod_prueba = :prueba "
			+ "and pr.cod_periodo_academico = cbdmq.get_pa_activo()", nativeQuery=true)
	List<ResultadosPruebasFisicasDatos> getResultados(Integer prueba);
}
