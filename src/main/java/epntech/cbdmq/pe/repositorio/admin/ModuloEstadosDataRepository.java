
package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.ModuloEstadosData;

public interface ModuloEstadosDataRepository extends JpaRepository<ModuloEstadosData, Integer> {
	
	@Query(value="select me.cod_modulo_estados as codigo, m.etiqueta as modulo, c.nombre_catalogo_estados as estado_catalogo, me.orden, me.estado as estado "
			+ "from cbdmq.gen_modulo_estados me, cbdmq.gen_modulo m, cbdmq.gen_catalogo_estados c "
			+ "where me.cod_modulo = m.cod_modulo "
			+ "and me.cod_catalogo_estados = c.cod_catalogo_estados "
			+ "and UPPER(m.estado) = 'ACTIVO' "
			+ "AND UPPER(c.estado) = 'ACTIVO' "
			+ "and upper(me.estado) != 'ELIMINADO' "
			+ "and m.cod_modulo = :modulo "
			+ "order by 2 ", nativeQuery=true)
	List<ModuloEstadosData> getByModulo(@Param("modulo") Integer modulo);
	
	@Query(value="select me.cod_modulo_estados as codigo, m.etiqueta as modulo, c.nombre_catalogo_estados as estado_catalogo, me.orden, me.estado as estado "
			+ "from cbdmq.gen_modulo_estados me, cbdmq.gen_modulo m, cbdmq.gen_catalogo_estados c "
			+ "where me.cod_modulo = m.cod_modulo "
			+ "and me.cod_catalogo_estados = c.cod_catalogo_estados "
			+ "and UPPER(m.estado) = 'ACTIVO' "
			+ "AND UPPER(c.estado) = 'ACTIVO' "
			+ "and upper(me.estado) != 'ELIMINADO' "
			+ "order by 2 ", nativeQuery=true)
	List<ModuloEstadosData> getAllData();
}

