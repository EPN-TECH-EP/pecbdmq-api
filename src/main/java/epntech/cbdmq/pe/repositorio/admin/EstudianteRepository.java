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

	@Query(nativeQuery = true)
	List<EstudianteDatos> findAllEstudiante();

	@Query(nativeQuery = true)
	Page<EstudianteDatos> findAllEstudiante(Pageable pageable);

	/* Historico

	@Query(value = "select gme.codperiodoAcademico.codigo from Estudiante ge left join gen_modulo gmod on gmod.cod_modulo =ge.codModulo  left join MateriaEstudiante gme on gme.estudiante.codEstudiante=ge.codEstudiante where gmod.cod_modulo=1 and ge.codEstudiante=:codEstudiante group by gme.codperiodoAcademico.codigo")
	List<?> findPeriodos(@Param("codEstudiante") Integer codEstudiante, Pageable pageable);

}
	 */
	@Query(nativeQuery = true, name = "FormacionEstudiante.findHistorico")
	List<FormacionEstudiante> getForHistoricos(@Param("codUnico") String codUnico, Pageable pageable);

	@Query(nativeQuery = true, name = "EspecializacionEstudiante.findHistorico")
	List<EspecializacionEstudiante> getEspHistoricos(@Param("codUnico") String codUnico, Pageable pageable);

	@Query(nativeQuery = true, name = "ProfesionalizacionEstudiante.findHistorico")
	List<ProfesionalizacionEstudiante> getProfHistoricos(@Param("codUnico") String codUnico, Pageable pageable);
	@Query("select ge from EstudianteDto ge\n" +
			"join Usuario gu \n" +
			"on ge.codDatosPersonales = gu.codDatosPersonales.cod_datos_personales\n" +
			"where gu.isActive =true\n" +
			"and gu.isNotLocked =true\n" +
			"and gu.codUsuario=:codUsuario")
	EstudianteDto getEstudianteByUsuario(@Param("codUsuario") String coUsuario);
}