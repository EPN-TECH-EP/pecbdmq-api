package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import epntech.cbdmq.pe.dominio.admin.EvaluacionDocente;

public interface EvaluacionDocenteRepository  extends JpaRepository<EvaluacionDocente, Integer>{
	
	Optional<EvaluacionDocente> findBypregunta(String nombre);

}
