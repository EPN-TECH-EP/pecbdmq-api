package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.admin.PostulanteDatos;



public interface PostulanteRepository extends JpaRepository<Postulante, Integer> {
	
	@Procedure(value = "cbdmq.get_id")
	String getIdPostulante(String proceso);
	
	@Query(value="select p.* "
			+ "from cbdmq.gen_postulante p "
			+ "where p.estado = 'ASIGNADO' "
			+ "and p.cod_usuario = :usuario "
			+ "union "
			+ "select p.* "
			+ "from cbdmq.gen_postulante p "
			+ "where p.estado = 'PENDIENTE' "
			+ "order by cod_usuario ", nativeQuery=true)
	List<Postulante> getPostulantes(Integer usuario);
	
}
