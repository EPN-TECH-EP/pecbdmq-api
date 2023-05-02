package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.PostulanteDatoPersonal;

public interface PostulanteDPRepository extends JpaRepository<PostulanteDatoPersonal, Integer> {

	@Query(value = "select p.cod_postulante, p.id_postulante, d.nombre, d.apellido, d.cedula, d.correo_personal "
			+ "from cbdmq.gen_postulante p, cbdmq.gen_dato_personal d "
			+ "where p.cod_datos_personales = d.cod_datos_personales "
			+ "and p.estado != 'ELIMINADO' "
			+ "and d.cedula =:cedula", nativeQuery = true)
	Optional<PostulanteDatoPersonal> getByCedula(String cedula);
}
