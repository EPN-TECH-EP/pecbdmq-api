package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import epntech.cbdmq.pe.dominio.util.SubTipoPruebaDatos;


public interface SubTipoPruebaRepository extends JpaRepository<SubTipoPrueba, Integer> {
	
	public static Sort defaultSort = Sort.by(Sort.Order.asc("nombre"));
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
	
	List<SubTipoPrueba>getAllByCodTipoPruebaOrderByNombre(Long codTipoPrueba);
	
	@Query(value = "select s1_0.cod_subtipo_prueba,s1_0.cod_tipo_prueba,s1_0.estado,s1_0.nombre from cbdmq.gen_subtipo_prueba s1_0 where upper(s1_0.nombre)=upper(:nombre)", nativeQuery = true)
	Optional<SubTipoPrueba> findByNombreIgnoreCase(@Param("nombre") String nombre);

}
