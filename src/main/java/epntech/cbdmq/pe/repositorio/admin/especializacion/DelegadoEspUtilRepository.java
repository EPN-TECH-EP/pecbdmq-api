package epntech.cbdmq.pe.repositorio.admin.especializacion;

import epntech.cbdmq.pe.dominio.admin.especializacion.DelegadoUtilEsp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface DelegadoEspUtilRepository extends JpaRepository<DelegadoUtilEsp, Long> {

	@Query(value = "select ed.cod_esp_delegado, ed.cod_usuario, " +
			"gdp.cod_datos_personales, ed.estado , " +
			"gdp.cedula, gdp.nombre, gdp.apellido " +
			"from cbdmq.esp_delegado ed, cbdmq.gen_usuario gu , cbdmq.gen_dato_personal gdp " +
			"where gu.cod_usuario = ed.cod_usuario " +
			"and gu.cod_datos_personales  = gdp.cod_datos_personales " +
			"and UPPER(ed.estado) = 'ACTIVO' " +
			"and UPPER(gdp.estado) = 'ACTIVO' ", nativeQuery = true)
	List<DelegadoUtilEsp> findDelegados();

}
