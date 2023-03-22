package epntech.cbdmq.pe.repositorio.admin;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.Postulante;



public interface PostulanteRepository extends JpaRepository<Postulante, Integer> {
	
	@Procedure(value = "cbdmq.get_id_postulante")
	String getIdPostulante(String proceso);
	
	
}
