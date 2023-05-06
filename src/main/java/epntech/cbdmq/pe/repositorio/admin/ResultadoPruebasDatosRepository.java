package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;

public interface ResultadoPruebasDatosRepository extends JpaRepository<ResultadosPruebasDatos, Integer> {

	
	@Query(value = "select p.id_postulante, dp.cedula, dp.nombre, dp.apellido, r.resultado, "
			+ "CASE WHEN r.cumple_prueba = true THEN 'SI' "
			+ "    WHEN r.cumple_prueba = false THEN 'NO' "
			+ "    WHEN r.cumple_prueba is null THEN '--' "
			+ "END cumple_prueba "
			+ "from cbdmq.gen_resultado_prueba r, cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp "
			+ "where r.cod_postulante = p.cod_postulante "
			+ "and p.cod_datos_personales = dp.cod_datos_personales "
			+ "and upper(dp.estado) = 'ACTIVO' "
			+ "and upper(p.estado) in ('ACTIVO', 'VALIDO', 'VALIDO MUESTRA') "
			+ "and upper(r.estado) = 'ACTIVO' "
			+ "and r.cumple_prueba = true", nativeQuery=true)
	List<ResultadosPruebasDatos> getResultados();
}
