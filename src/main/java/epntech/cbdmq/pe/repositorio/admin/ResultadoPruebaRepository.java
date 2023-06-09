package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import epntech.cbdmq.pe.dominio.admin.ResultadoPrueba;

public interface ResultadoPruebaRepository extends JpaRepository<ResultadoPrueba, Integer>{

	
	Optional<ResultadoPrueba> findByCumplePrueba(String cumpleprueba);
	
}
