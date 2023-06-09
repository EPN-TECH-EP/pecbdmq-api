package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Estudiante;
import org.springframework.data.repository.query.Param;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

	Optional<Estudiante> findByidEstudiante(String id);
	
	//@Query(nativeQuery = true)
	//List<EstudianteDatos> findAllEstudiante();

	//@Query(nativeQuery = true)
	//Page<EstudianteDatos> findAllEstudiante(Pageable pageable);

	@Query(nativeQuery = true, name = "FormacionEstudiante.findHistorico")
	List<FormacionEstudiante> getForHistoricos(@Param("codUnico") String codUnico, Pageable pageable);

	@Query(nativeQuery = true, name = "EspecializacionEstudiante.findHistorico")
	List<EspecializacionEstudiante> getEspHistoricos(@Param("codUnico") String codUnico, Pageable pageable);

	@Query(nativeQuery = true, name = "ProfesionalizacionEstudiante.findHistorico")
	List<ProfesionalizacionEstudiante> getProfHistoricos(@Param("codUnico") String codUnico, Pageable pageable);
	@Query("select ge from Estudiante ge\n" +
			"join Usuario gu \n" +
			"on ge.codDatosPersonales = gu.codDatosPersonales.codDatosPersonales\n" +
			"where gu.isActive =true\n" +
			"and gu.isNotLocked =true\n" +
			"and gu.codUsuario=:codUsuario")
	Estudiante getEstudianteByUsuario(@Param("codUsuario") String coUsuario);
}