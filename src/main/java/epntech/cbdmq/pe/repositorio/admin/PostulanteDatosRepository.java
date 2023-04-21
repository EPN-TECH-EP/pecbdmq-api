package epntech.cbdmq.pe.repositorio.admin;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.PostulanteDatos;



public interface PostulanteDatosRepository extends JpaRepository<PostulanteDatos, Integer> {
	
	@Query(value="select p.cod_postulante, p.id_postulante, p.fecha_postulacion, p.edad_postulacion, "
			+ "dp.cod_datos_personales, dp.apellido, dp.cedula, dp.nombre, dp.fecha_nacimiento, "
			+ "dp.tipo_sangre, dp.genero, dp.canton_nacimiento, dp.canton_residencia, "
			+ "dp.calle_principal_residencia, dp.calle_secundaria_residencia, dp.numero_casa, "
			+ "dp.colegio, dp.tipo_nacionalidad, dp.num_telef_celular, "
			+ "dp.nombre_titulo, dp.pais_titulo, dp.ciudad_titulo,  "
			+ "dp.merito_academico_descripcion, dp.merito_deportivo_descripcion, "
			+ "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_nacimiento) provincia_nacimiento, "
			+ "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_residencia) provincia_residencia "
			+ "from cbdmq.gen_postulante p, cbdmq.gen_dato_personal dp "
			+ "where p.cod_datos_personales = dp.cod_datos_personales "
			+ "and UPPER(p.estado) = 'ACTIVO' "
			+ "and UPPER(dp.estado) = 'ACTIVO' "
			+ "and cod_postulante = :codPostulante", nativeQuery=true)
	Optional<PostulanteDatos> getDatos(Integer codPostulante);
	
}
