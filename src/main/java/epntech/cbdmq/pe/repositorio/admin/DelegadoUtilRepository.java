package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.DelegadoUtil;


public interface DelegadoUtilRepository extends JpaRepository<DelegadoUtil, Integer>{

	@Query(value = "select gd.cod_usuario, gdp.cod_datos_personales,gd.estado ,gdp.cedula,gdp.nombre, gdp.apellido " +
			"from cbdmq.gen_delegado gd, cbdmq.gen_usuario gu , cbdmq.gen_dato_personal gdp " +
			"where gu.cod_usuario = gd.cod_usuario " +
			"and gu.cod_datos_personales  = gdp.cod_datos_personales " +
			"and UPPER(gd.estado) = 'ACTIVO' " +
			"and UPPER(gdp.estado) = 'ACTIVO' " +
			"and gd.cod_periodo_academico = cbdmq.get_pa_activo()", nativeQuery=true)
	List<DelegadoUtil> getDelegados();

	@Query(value = "select ed.cod_usuario, gdp.cod_datos_personales, ed.estado, gdp.cedula, gdp.nombre, gdp.apellido " +
			"from cbdmq.esp_delegado ed, cbdmq.gen_usuario gu , cbdmq.gen_dato_personal gdp " +
			"where gu.cod_usuario = ed.cod_usuario " +
			"and gu.cod_datos_personales  = gdp.cod_datos_personales " +
			"and UPPER(ed.estado) = 'ACTIVO' " +
			"and UPPER(gdp.estado) = 'ACTIVO'", nativeQuery=true)
	List<DelegadoUtil> getDelegadosEsp();
}
