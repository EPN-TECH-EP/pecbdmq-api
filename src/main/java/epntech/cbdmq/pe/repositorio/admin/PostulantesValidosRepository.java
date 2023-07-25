package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import epntech.cbdmq.pe.dominio.util.PostulantesValidos;

@Repository
public interface PostulantesValidosRepository extends JpaRepository<PostulantesValidos, String> {
	
	@Query(value = "select p.cod_postulante, p.id_postulante, dp.cedula, dp.correo_personal, dp.nombre, dp.apellido "
			+ "from cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp "
			+ "where p.cod_datos_personales = dp.cod_datos_personales "
			+ "and UPPER(dp.estado) = 'ACTIVO' "
			+ "and UPPER(p.estado) in ('VALIDO', 'VALIDO MUESTRA') "
			+ "and cod_periodo_academico = cbdmq.get_pa_activo()", nativeQuery=true)
	List<PostulantesValidos> getPostulantesValidos();
	@Query(value = "select p.cod_postulante, p.id_postulante, dp.cedula, dp.correo_personal, dp.nombre, dp.apellido "
			+ "from cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp "
			+ "where p.cod_datos_personales = dp.cod_datos_personales "
			+ "and UPPER(dp.estado) <> 'INACTIVO' "
			+ "and UPPER(p.estado) in ('VALIDO', 'VALIDO MUESTRA') "
			+ "and cod_periodo_academico = cbdmq.get_pa_activo()", nativeQuery=true)
	List<PostulantesValidos> getPostulantesValidosDiferentBaja();

	
	String queryBase = "select p.cod_postulante, p.id_postulante, dp.cedula, dp.correo_personal, dp.nombre, dp.apellido "
			+ "from cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp "
			+ "where p.cod_datos_personales = dp.cod_datos_personales "
			+ "and UPPER(dp.estado) = 'ACTIVO' "
			+ "and UPPER(p.estado) in ('VALIDO', 'VALIDO MUESTRA') "
			+ "and cod_periodo_academico = cbdmq.get_pa_activo() ";
	String orderByIdPostulante	=	 "order by p.id_postulante";
	String orderByApellidoPostulante	=	 "order by dp.apellido";

	// string para filtrar por cedula
	String filtroCedula = " and dp.cedula like '%' || trim(:filtro) || '%' ";

	// string para filtrar por id_postulante
	String filtroIdPostulante = " and upper(p.id_postulante) = trim(upper(:filtro)) ";

	// string para filtrar por apellido
	String filtroApellido = " and upper(dp.apellido) like '%' || trim(upper(:filtro)) || '%' ";

	// consulta de postulantes válidos por filtro
	@Query(value = queryBase + filtroCedula + orderByIdPostulante, nativeQuery=true)
	List<PostulantesValidos> getPostulantesValidosFiltroCedula(String filtro);

	@Query(value = queryBase + filtroIdPostulante + orderByIdPostulante, nativeQuery=true)
	List<PostulantesValidos> getPostulantesValidosFiltroIdPostulante(String filtro);

	@Query(value = queryBase + filtroApellido + orderByIdPostulante, nativeQuery=true)
	List<PostulantesValidos> getPostulantesValidosFiltroApellido(String filtro);

	@Query(value = queryBase  + orderByIdPostulante, nativeQuery=true)
	List<PostulantesValidos> getAllPostulantesValidos();
	
	@Query(value = queryBase  + orderByApellidoPostulante, nativeQuery=true)
	List<PostulantesValidos> getAllPostulantesValidosOrderApellido();
	
	@Query(value = queryBase + orderByIdPostulante, nativeQuery=true)
	Page<PostulantesValidos> getAllPostulantesValidosPaginado(Pageable pageable);
	
	@Query(value = queryBase + orderByApellidoPostulante, nativeQuery=true)
	Page<PostulantesValidos> getAllPostulantesValidosPaginadoOrderApellido(Pageable pageable);
	
	@Query(value = "select p.cod_postulante, p.id_postulante, dp.cedula, dp.correo_personal, dp.nombre, dp.apellido "
			+ "from cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp "
			+ "where p.cod_datos_personales = dp.cod_datos_personales "
			+ "and UPPER(dp.estado) = 'ACTIVO' "
			+ "and UPPER(p.estado) in ('VALIDO', 'VALIDO MUESTRA') "
			+ "and cod_periodo_academico = cbdmq.get_pa_activo()", nativeQuery=true)
    Page<PostulantesValidos> getPostulantesValidosPaginado(Pageable pageable);
	
	@Query(value = "select * from cbdmq.get_approved_by_test(:prueba)", nativeQuery=true)
	List<PostulantesValidos> get_approved_by_test(Integer prueba);
	
	@Query(value = "select * from cbdmq.get_approved_by_test(:prueba)", nativeQuery=true)
	Page<PostulantesValidos> get_approved_by_test(Pageable pageable, Integer prueba);
	
}
