package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.Postulante;

public interface PostulanteRepository extends JpaRepository<Postulante, Long> {
	
	@Procedure(value = "cbdmq.get_id")
	String getIdPostulante(String proceso);
	
	@Query(value="select p.* "
			+ "from cbdmq.gen_postulante p "
			+ "where p.estado = 'ASIGNADO' "
			+ "and p.cod_usuario = :usuario "
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "union "
			+ "select p.* "
			+ "from cbdmq.gen_postulante p "
			+ "where p.estado = 'PENDIENTE' "
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "order by cod_usuario ", nativeQuery=true)
	List<Postulante> getPostulantes(Integer usuario);
	
	@Query(value="select p.* "
			+ "from cbdmq.gen_postulante p "
			+ "where p.estado = 'ASIGNADO' "
			+ "and p.cod_usuario = :usuario "
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "union "
			+ "select p.* "
			+ "from cbdmq.gen_postulante p "
			+ "where p.estado = 'PENDIENTE' "
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "order by cod_usuario ", nativeQuery=true)
	List<Postulante> getPostulantesPaginado(Integer usuario, Pageable pageable);
	
	@Query(value="select p.* "
			+ "from cbdmq.gen_postulante p "
			+ "where p.estado = 'ASIGNADO' "
			+ "and p.cod_usuario = :usuario "
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "order by cod_usuario ", nativeQuery=true)
	List<Postulante> getPostulantesAsignadosPaginado(Integer usuario, Pageable pageable);
	
	@Procedure(value = "cbdmq.update_state_postulante")
	Boolean updateState(Integer codPostulante);
	
	@Query(value="select p.* "
			+ "from cbdmq.gen_postulante p "
			+ "where p.estado in ('ASIGNADO', 'PENDIENTE')"
			+ "and p.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "order by cod_usuario ", nativeQuery=true)
	List<Postulante> getPostulantesAllPaginado(Pageable pageable);
	
	@Query(value = "select * from cbdmq.get_sample_inscriptions()", nativeQuery=true)
	List<Postulante> getMuestra();
	
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
	List<Postulante> getPostulantesAllPaginadoTodo(Pageable pageable);

	Optional<Postulante> findByCodDatoPersonal(Integer codDatoPersonal);
}
