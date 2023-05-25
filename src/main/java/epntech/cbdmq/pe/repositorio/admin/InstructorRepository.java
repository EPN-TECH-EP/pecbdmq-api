package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.EstudianteDto;
import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Instructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

	
	Optional<Instructor> findById(Integer codigo);
	@Query("select gi from gen_instructor gi \n" +
			"join Usuario gu \n" +
			"on gi.cod_datos_personales= gu.codDatosPersonales.cod_datos_personales\n" +
			"where gu.isActive =true\n" +
			"and gu.isNotLocked =true\n" +
			"and gu.codUsuario =:codUsuario")
	Instructor getInstructorByUsuario(@Param("codUsuario") String coUsuario);
	
}
