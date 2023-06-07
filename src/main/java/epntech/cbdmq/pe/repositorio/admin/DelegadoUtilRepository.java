package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.DelegadoUtil;


public interface DelegadoUtilRepository extends JpaRepository<DelegadoUtil, Integer>{

	
	
	
	 
	@Query(value = "select gd.cod_usuario, gdp.cod_datos_personales,gd.estado ,gdp.cedula,gdp.nombre, gdp.apellido \r\n"
			+ "	from cbdmq.gen_delegado gd, cbdmq.gen_usuario gu , cbdmq.gen_dato_personal gdp  \r\n"
			+ "	where gu.cod_usuario = gd.cod_usuario \r\n"
			+ "	and gu.cod_datos_personales  = gdp.cod_datos_personales \r\n"
			+ "	and UPPER(gd.estado) = 'ACTIVO'\r\n"
			+ "	and UPPER(gdp.estado) = 'ACTIVO'", nativeQuery=true)
	List<DelegadoUtil> getDelegados();
}
