package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Instructor;


public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

	
	Optional<Instructor> findById(Integer codigo);
	
}
