package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.ApelacionEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Apelacion;
import org.springframework.data.repository.query.Param;

public interface ApelacionRepository extends JpaRepository<Apelacion, Integer>{
	@Query(value = "select a.* \n" +
			"from cbdmq.gen_apelacion a\n" +
			"left join cbdmq.gen_nota_formacion n on a.cod_nota_formacion = n.cod_nota_formacion\n" +
			"left join cbdmq.gen_estudiante_materia_paralelo gemp on n.cod_estudiante_materia_paralelo = gemp.cod_estudiante_materia_paralelo  \n" +
			"left join cbdmq.gen_estudiante e on gemp.cod_estudiante = e.cod_estudiante \n" +
			"left join cbdmq.gen_materia_paralelo mpa on gemp.cod_materia_paralelo = mpa.cod_materia_paralelo \n" +
			"left join cbdmq.gen_materia_periodo mpe on mpa.cod_materia_periodo = mpe.cod_materia_periodo \t\n" +
			"where a.cod_nota_formacion = n.cod_nota_formacion \n" +
			"and mpe.cod_periodo_academico = cbdmq.get_pa_activo()\n" +
			"and upper(a.estado) = 'ACTIVO' \n" +
			"and upper(n.estado) = 'ACTIVO'\n" +
			"and gemp.cod_estudiante = :codEstudiante", nativeQuery=true)
	List<Apelacion> getApelacionesByEstudiante(Integer codEstudiante);
	
	@Query(value = "select a.* \n" +
			"from cbdmq.gen_apelacion a\n" +
			"left join cbdmq.gen_nota_formacion n on a.cod_nota_formacion = n.cod_nota_formacion\n" +
			"left join cbdmq.gen_estudiante_materia_paralelo gemp on n.cod_estudiante_materia_paralelo = gemp.cod_estudiante_materia_paralelo  \n" +
			"left join cbdmq.gen_instructor_materia_paralelo gimp on gemp.cod_materia_paralelo = gimp.cod_materia_paralelo \n" +
			"left join cbdmq.gen_instructor gi on gimp.cod_instructor = gi.cod_instructor \n" +
			"left join cbdmq.gen_tipo_instructor gti on gimp.cod_tipo_instructor = gti.cod_tipo_instructor \n" +
			"left join cbdmq.gen_materia_paralelo mpa on gemp.cod_materia_paralelo = mpa.cod_materia_paralelo \n" +
			"left join cbdmq.gen_materia_periodo mpe on mpa.cod_materia_periodo = mpe.cod_materia_periodo \t\n" +
			"where a.cod_nota_formacion = n.cod_nota_formacion \n" +
			"and mpe.cod_periodo_academico = cbdmq.get_pa_activo()\n" +
			"and upper(a.estado) = 'ACTIVO' \n" +
			"and upper(n.estado) = 'ACTIVO'\n" +
			"and gimp.cod_instructor = :codInstructor\n" +
			"and gti.nombre_tipo_instructor = :nombreTipoInstructor", nativeQuery=true)
	List<Apelacion> getApelacionesByInstructor(Integer codInstructor, String nombreTipoInstructor);
	Optional<Apelacion> findApelacionByCodNotaFormacion(Integer codNotaFormacion);

	@Query(name="ApelacionEstudiante.getApelacionesEstudiantesMateria", nativeQuery=true)
	List<ApelacionEstudiante> getApelacionesEstudiantesMateria(@Param("codMateria") Integer codMateria, @Param("codPA") Integer codPA);
	
}
