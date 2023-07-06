package epntech.cbdmq.pe.repositorio.fichaPersonal;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionEstudiante;
import epntech.cbdmq.pe.dominio.util.EstudianteDatos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
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
	@Query("select gd.cedula from Estudiante ge\n" +
			"join gen_dato_personal gd\n" +
			"on ge.codDatosPersonales = gd.codDatosPersonales\n" +
			"where ge.codEstudiante= :codEstudiante")
	String getCedulaByEstudiante(@Param("codEstudiante") Integer codEstudiante);
	
	@Query(nativeQuery = true, name = "EstudianteDatos.findEstudiante")
	Optional<EstudianteDatos> getEstudiante(@Param("codEstudiante") Long codEstudiante);
}