package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.EstacionTrabajoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.EstacionTrabajo;
import org.springframework.data.jpa.repository.Query;

public interface EstacionTrabajoRepository extends JpaRepository<EstacionTrabajo, Integer> {

	Optional<EstacionTrabajo> findByNombreIgnoreCase(String nombre);

	@Query(name = "EstacionTrabajo.findEstaciones", nativeQuery = true)
	List<EstacionTrabajoDto> findAllWithProvince();

	@Query(name = "EstacionTrabajo.findEstacion", nativeQuery = true)
	Optional<EstacionTrabajoDto> findWithProvince(Integer codigo);
	
}
