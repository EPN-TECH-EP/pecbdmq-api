package epntech.cbdmq.pe.repositorio.admin;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.Postulante;


public interface PostulanteRepository extends JpaRepository<Postulante, Integer> {
	
	@Procedure(value = "cbdmq.get_id_postulante")
	String getIdPostulante(String proceso);
	
	@Query(value = "select p, d "
			+ "from cbdmq.gen_postulante p, cbdmq.gen_dato_personal d "
			+ "where p.cod_datos_personales = d.cod_datos_personales "
			+ "and p.estado != 'ELIMINADO' "
			+ "and d.cedula =:cedula", nativeQuery = true)
	Optional<?> getByCedula(String cedula);
}
