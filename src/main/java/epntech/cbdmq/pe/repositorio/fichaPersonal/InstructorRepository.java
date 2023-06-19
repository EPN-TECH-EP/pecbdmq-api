package epntech.cbdmq.pe.repositorio.fichaPersonal;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionInstructor;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionInstructor;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionInstructor;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

	
	Optional<Instructor> findById(Integer codigo);
	@Query("select gi from gen_instructor gi \n" +
			"join Usuario gu \n" +
			"on gi.codDatosPersonales= gu.codDatosPersonales.codDatosPersonales\n" +
			"where gu.isActive =true\n" +
			"and gu.isNotLocked =true\n" +
			"and gu.codUsuario =:codUsuario")
	Instructor getInstructorByUsuario(@Param("codUsuario") String coUsuario);
	@Query(nativeQuery = true, name = "FormacionInstructor.findHistorico")
	List<FormacionInstructor> getForHistoricos(@Param("codInstructor") Integer codUnico, Pageable pageable);

	@Query(nativeQuery = true, name = "EspecializacionInstructor.findHistorico")
	List<EspecializacionInstructor> getEspHistoricos(@Param("codInstructor") Integer codUnico, Pageable pageable);

	@Query(nativeQuery = true, name = "ProfesionalizacionInstructor.findHistorico")
	List<ProfesionalizacionInstructor> getProfHistoricos(@Param("codInstructor") Integer codUnico, Pageable pageable);
	
	
}
