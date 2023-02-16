package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.UnidadGestion;


public interface UnidadGestionRepository extends JpaRepository<UnidadGestion, Integer> {
	Optional<UnidadGestion> findByNombre(String nombre);
	
}
