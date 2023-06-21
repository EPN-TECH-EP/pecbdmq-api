package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import epntech.cbdmq.pe.dominio.util.SubTipoPruebaDatos;

public interface SubTipoPruebaRepository extends JpaRepository<SubTipoPrueba, Integer> {
	//Optional<SubTipoPrueba> findByCodTipoPrueba(Integer id);
	
	@Query(value = "select\r\n"
			+ "	gsp.cod_subtipo_prueba, gsp.cod_tipo_prueba, gsp.nombre, gsp.estado,\r\n"
			+ "	gtp.tipo_prueba ,\r\n"
			+ "	gtp.es_fisica \r\n"
			+ "from\r\n"
			+ "	cbdmq.gen_subtipo_prueba gsp ,\r\n"
			+ "	cbdmq.gen_tipo_prueba gtp\r\n"
			+ "where\r\n"
			+ "	gtp.cod_tipo_prueba = gsp.cod_tipo_prueba\r\n"
			+ "order by \r\n"
			+ "	gtp.tipo_prueba,\r\n"
			+ "	gsp.nombre ", nativeQuery = true)
	List<SubTipoPruebaDatos> listarTodosConDatosTipoPrueba();

}
