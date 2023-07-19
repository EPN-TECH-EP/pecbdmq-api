package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteDatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.NotasFormacion;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotasFormacionRepository extends JpaRepository<NotasFormacion, Integer> {
	@Query(value = "select gnf.* from cbdmq.gen_nota_formacion gnf \n" +
			"left join cbdmq.gen_estudiante_materia_paralelo gemp on gnf.cod_estudiante_materia_paralelo = gemp.cod_estudiante_materia_paralelo \n" +
			"where gemp.cod_estudiante =:codEstudiante", nativeQuery=true)
	List<NotasFormacion> findByCodEstudiante(Integer codEstudiante);
	
	@Query(value = "select t.* "
			+ "from cbdmq.gen_nota_formacion t "
			+ "where t.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "and upper(t.estado) = 'ACTIVO' ", nativeQuery=true)
	List<NotasFormacion> getNotasFormnacion();
	
	@Query(value = "select count(*)\n" +
			"from cbdmq.gen_nota_formacion f \n" +
			"left join cbdmq.gen_estudiante_materia_paralelo gemp on f.cod_estudiante_materia_paralelo = gemp.cod_estudiante_materia_paralelo\n" +
			"left join cbdmq.gen_materia_paralelo gmp on gemp.cod_materia_paralelo = gmp.cod_materia_paralelo\n" +
			"left join cbdmq.gen_materia_periodo pe on gmp.cod_materia_periodo = pe.cod_materia_periodo \n" +
			"where upper(f.estado) = 'ACTIVO'\n" +
			"and pe.cod_materia = :codMateria\n" +
			"and gemp.cod_estudiante = :codEstudiante\n" +
			"and pe.cod_periodo_academico =  cbdmq.get_pa_activo() ", nativeQuery=true)
	Integer existeNota(Integer codMateria, Integer codEstudiante);

	@Query(value = "select gm.nombre,gpa.descripcion from NotasFormacion gnf\n" +
			"left join EstudianteMateriaParalelo gemp on gnf.codEstudianteMateriaParalelo = gemp.codEstudianteMateriaParalelo \n" +
			"left join MateriaParalelo gmp on gemp.codMateriaParalelo = gmp.codMateriaParalelo \n" +
			"left join MateriaPeriodo gmp2  on gmp.codMateriaPeriodo = gmp2.codMateriaPeriodo \n" +
			"left join gen_materia gm on gmp2.codMateria  = gm.codMateria \n" +
			"left join gen_periodo_academico gpa on gmp2.codPeriodoAcademico= gpa.codigo \n" +
			"group by gm.nombre ,gpa.descripcion")
	List<?> listaMateriasHistorico();
	@Query(nativeQuery = true, name = "EstudianteDatos.getNotasEstudiantesMateria")
	List<EstudianteDatos> getEstudianteMateriaParalelo(@Param("codMateria") Integer codMateria, @Param("codPA") Integer codPA);
}

