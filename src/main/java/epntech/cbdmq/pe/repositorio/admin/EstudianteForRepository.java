package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.EstudianteFor;

public interface EstudianteForRepository extends JpaRepository<EstudianteFor, Integer> {

	@Procedure(value = "cbdmq.get_id")
	String getIdAspirante(String proceso);
	
	@Procedure(value = "cbdmq.insert_estudiantes")
	Integer insertEstudiantes(Integer modulo);
}
