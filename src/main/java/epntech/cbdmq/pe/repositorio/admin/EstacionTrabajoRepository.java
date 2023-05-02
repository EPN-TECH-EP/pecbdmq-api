package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.EstacionTrabajo;

public interface EstacionTrabajoRepository extends JpaRepository<EstacionTrabajo, Integer> {

	Optional<EstacionTrabajo> findByNombreIgnoreCase(String nombre);
	
}
