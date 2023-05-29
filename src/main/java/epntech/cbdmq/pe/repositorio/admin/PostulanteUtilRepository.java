package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Jpa21Utils;

import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.util.PostulanteUtil;

public interface PostulanteUtilRepository extends JpaRepository<PostulanteUtil, Integer>{

	@Query(value="select p.cod_postulante,\r\n"
			+ "gdp.cod_datos_personales,\r\n"
			+ "p.id_postulante,\r\n"
			+ "p.estado ,\r\n"
			+ "p.cod_usuario,\r\n"
			+ "p.cod_periodo_academico,\r\n"
			+ "gdp.nombre,\r\n"
			+ "gdp.apellido,\r\n"
			+ "gdp.cedula \r\n"
			+ "from cbdmq.gen_postulante p,cbdmq.gen_dato_personal gdp\r\n"
			+ "where p.estado in ('ASIGNADO', 'PENDIENTE')\r\n"
			+ "and p.cod_datos_personales = gdp.cod_datos_personales \r\n"
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo()\r\n"
			+ "AND UPPER(gdp.estado) = 'ACTIVO' \r\n"
			+ "order by cod_usuario ", nativeQuery=true)
	List<PostulanteUtil> getPostulantesAllPaginadoTodo(Pageable pageable);
}
