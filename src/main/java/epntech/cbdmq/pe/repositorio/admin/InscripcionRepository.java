package epntech.cbdmq.pe.repositorio.admin;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.InscripcionFor;


public interface InscripcionRepository extends JpaRepository<InscripcionFor, Integer> {

	//@Value("${spring.jpa.properties.hibernate.default_schema}")
	
	Optional<InscripcionFor> findOneByCedula(String Cedula);

	Optional<InscripcionFor> findByCorreoPersonalIgnoreCase(String Correo);
	
	@Procedure(value = "cbdmq.get_id")
	String getIdPostulante(String proceso);
	
	@Procedure(value = "cbdmq.validar_edad")
	Boolean validaEdad(Date fecha);
}
