package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Estudiante;
import epntech.cbdmq.pe.dominio.admin.EstudianteDatos;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

	Optional<Estudiante> findByidEstudiante(String id);
	
	@Query(nativeQuery = true)
	List<EstudianteDatos> findAllEstudiante();

	@Query(nativeQuery = true)
	Page<EstudianteDatos> findAllEstudiante(Pageable pageable);
}
