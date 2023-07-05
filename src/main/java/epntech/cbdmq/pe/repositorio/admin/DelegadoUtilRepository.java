package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.DelegadoUtil;


public interface DelegadoUtilRepository extends JpaRepository<DelegadoUtil, Integer>{

	
	
	
	 
	@Query(value = "select gd.cod_usuario, gdp.cod_datos_personales,gd.estado ,gdp.cedula,gdp.nombre, gdp.apellido \n" +
			"\tfrom cbdmq.gen_delegado gd, cbdmq.gen_usuario gu , cbdmq.gen_dato_personal gdp  \n" +
			"\twhere gu.cod_usuario = gd.cod_usuario \n" +
			"\tand gu.cod_datos_personales  = gdp.cod_datos_personales \n" +
			"\tand UPPER(gd.estado) = 'ACTIVO'\n" +
			"\tand UPPER(gdp.estado) = 'ACTIVO'\n" +
			"\tand gd.cod_periodo_academico = cbdmq.get_pa_activo();", nativeQuery=true)
	List<DelegadoUtil> getDelegados();
}
