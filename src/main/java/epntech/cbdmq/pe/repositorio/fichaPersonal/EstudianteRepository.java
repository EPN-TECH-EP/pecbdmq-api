package epntech.cbdmq.pe.repositorio.fichaPersonal;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionEstudiante;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

	Optional<Estudiante> findByCodUnicoEstudiante(String id);
	
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

	Estudiante getEstudianteByCodUnicoEstudiante(String codUnicoEstudiante);
	@Query(value="SELECT ge.cod_estudiante, ge.cod_datos_personales , ge.codigo_unico_estudiante, ge.estado\n" +
			"FROM cbdmq.gen_estudiante ge\n" +
			"WHERE NOT EXISTS (\n" +
			"  SELECT 1\n" +
			"  FROM cbdmq.gen_estudiante_materia_paralelo gemp\n" +
			"  WHERE gemp.cod_estudiante = ge.cod_estudiante\n" +
			")\n",nativeQuery = true)
	List<Estudiante> estudiantesWithParalelo();



	@Query("select ge from NotasFormacion gnf\n"+
			"left join EstudianteMateriaParalelo gemp on gnf.codEstudianteMateriaParalelo = gemp.codEstudianteMateriaParalelo\n" +
			"left join Estudiante ge on ge.codEstudiante = gemp.codEstudiante\n" +
			"where gnf.codNotaFormacion=:notaFormacion")
			Estudiante getEstudianteByNotaFormacionFinal(@Param("notaFormacion") Integer notaFormacion);

	List<Estudiante> getAllByEstadoIsIgnoreCase(String estado);
}

