package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import epntech.cbdmq.pe.dominio.admin.InscripcionFor;


public interface InscripcionRepository extends JpaRepository<InscripcionFor, Integer> {

	//@Value("${spring.jpa.properties.hibernate.default_schema}")
	
	Optional<InscripcionFor> findOneByCedula(String Cedula);

	Optional<InscripcionFor> findByCorreoPersonalIgnoreCase(String Correo);
	
}
