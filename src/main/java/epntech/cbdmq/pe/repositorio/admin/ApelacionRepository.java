package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Apelacion;

public interface ApelacionRepository extends JpaRepository<Apelacion, Integer>{

	
	Optional<Apelacion>findByaprobacion(String apelacion);
	
	
	
}
