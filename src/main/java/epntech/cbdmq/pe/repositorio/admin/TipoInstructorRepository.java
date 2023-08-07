package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import epntech.cbdmq.pe.dominio.admin.TipoInstructor;

public interface TipoInstructorRepository extends JpaRepository<TipoInstructor, Integer>{

	Optional<TipoInstructor> findByNombreIgnoreCase(String nombre);

}
