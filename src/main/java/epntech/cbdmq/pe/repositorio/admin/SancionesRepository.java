package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Sanciones;


public interface SancionesRepository extends JpaRepository<Sanciones, Integer>{

	Optional<Sanciones> findByoficialsemana(String oficialsemana);
	
}
