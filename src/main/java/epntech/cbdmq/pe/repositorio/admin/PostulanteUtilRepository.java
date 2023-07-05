package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Jpa21Utils;

import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.util.PostulanteUtil;
import org.springframework.data.repository.query.Param;

public interface PostulanteUtilRepository extends JpaRepository<PostulanteUtil, Integer>{

	@Query(value="select\n"
			+ "	p.cod_postulante,\n"
			+ "	gdp.cod_datos_personales,\n"
			+ "	p.id_postulante,\n"
			+ "	p.estado ,\n"
			+ "	p.cod_usuario,\n"
			+ "	p.cod_periodo_academico,\n"
			+ "	gdp.nombre,\n"
			+ "	gdp.apellido,\n"
			+ "	gdp.cedula,\n"
			+ "	datosUsuario.nombre_usuario,\n"
			+ "	datosUsuario.correo_personal as correo_usuario\n"
			+ "from\n"
			+ "	cbdmq.gen_postulante p,\n"
			+ "	cbdmq.gen_dato_personal gdp,\n"
			+ "	(select gu.cod_usuario, apellido || ' ' || nombre as nombre_usuario, correo_personal  \n"
			+ "	from cbdmq.gen_dato_personal gdp2,\n"
			+ "	cbdmq.gen_usuario gu	\n"
			+ "	where gdp2.cod_datos_personales = gu.cod_datos_personales\n"
			+ "	) as datosUsuario\n"
			+ "where\n"
			+ "	p.estado in ('ASIGNADO')\n"
			+ "	and p.cod_datos_personales = gdp.cod_datos_personales\n"
			+ "	and p.cod_periodo_academico = cbdmq.get_pa_activo()\n"
			+ "	and UPPER(gdp.estado) = 'ACTIVO'\n"
			+ " 	and datosUsuario.cod_usuario = p.cod_usuario\n"
			+ "order by\n"
			+ "	nombre_usuario", nativeQuery=true)
	List<PostulanteUtil> getPostulantesAllPaginadoTodoAsignado(Pageable pageable);
	
	@Query(value="select p.cod_postulante, "
			+ "gdp.cod_datos_personales, "
			+ "p.id_postulante, "
			+ "p.estado , "
			+ "p.cod_usuario, "
			+ "p.cod_periodo_academico, "
			+ "gdp.nombre, "
			+ "gdp.apellido, "
			+ "gdp.cedula, null as nombre_usuario, null as correo_usuario "
			+ "from cbdmq.gen_postulante p,cbdmq.gen_dato_personal gdp "
			+ "where p.estado = 'ASIGNADO' "
			+ "and p.cod_datos_personales = gdp.cod_datos_personales "
			+ "and p.cod_usuario = :usuario "
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "union all "
			+ "select p.cod_postulante, "
			+ "gdp.cod_datos_personales, "
			+ "p.id_postulante, "
			+ "p.estado , "
			+ "p.cod_usuario, "
			+ "p.cod_periodo_academico, "
			+ "gdp.nombre, "
			+ "gdp.apellido, "
			+ "gdp.cedula, null, null "
			+ "from cbdmq.gen_postulante p,cbdmq.gen_dato_personal gdp "
			+ "where p.estado = 'PENDIENTE' "
			+ "and p.cod_datos_personales = gdp.cod_datos_personales "
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "order by cod_usuario ", nativeQuery=true)
	List<PostulanteUtil> getPostulantesPaginado(Integer usuario, Pageable pageable);
	@Query(value="select p.cod_postulante, gdp.cod_datos_personales, p.id_postulante, p.estado , p.cod_usuario, p.cod_periodo_academico, gdp.nombre, gdp.apellido, gdp.cedula, null as nombre_usuario, null as correo_usuario\n" +
			"from cbdmq.gen_postulante p\n" +
			"left join cbdmq.gen_dato_personal gdp on p.cod_datos_personales = gdp.cod_datos_personales\n" +
			"where p.estado=:estado \n" +
			"and p.cod_periodo_academico=:codPA", nativeQuery=true)
	List<PostulanteUtil> getPostulantesEstadoPAPaginado(@Param("estado") String estado, @Param("codPA") Integer usuario, Pageable pageable);
}
