package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import epntech.cbdmq.pe.dominio.admin.PruebaFisica;

public interface PruebaFisicaRepository extends JpaRepository<PruebaFisica, Integer>{

	Optional<PruebaFisica> findById(Integer codigo);
	
	
	
	
}
