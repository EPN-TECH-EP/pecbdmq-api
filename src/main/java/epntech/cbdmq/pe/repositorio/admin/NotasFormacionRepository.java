package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteDatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.NotasFormacion;
import org.springframework.data.repository.query.Param;

public interface NotasFormacionRepository extends JpaRepository<NotasFormacion, Integer> {

	List<NotasFormacion> findByCodEstudiante(Integer codEstudiante);
	
	@Query(value = "select t.* "
			+ "from cbdmq.gen_nota_formacion t "
			+ "where t.cod_periodo_academico = cbdmq.get_pa_activo() "
			+ "and upper(t.estado) = 'ACTIVO' ", nativeQuery=true)
	List<NotasFormacion> getNotasFormnacion();
	
	@Query(value = "select count(*) "
			+ "from cbdmq.gen_nota_formacion f "
			+ "where upper(f.estado) = 'ACTIVO' "
			+ "and cod_instructor = :codInstructor "
			+ "and cod_materia = :codMateria "
			+ "and cod_estudiante = :codEstudiante "
			+ "and cod_periodo_academico =  cbdmq.get_pa_activo() ", nativeQuery=true)
	Integer existeNota(Integer codInstructor, Integer codMateria, Integer codEstudiante);

	@Query(value = "select gm.nombre ,gpa.descripcion from NotasFormacion gnf \n" +
			"left join gen_materia gm on gnf.codMateria = gm.codMateria \n" +
			"left join gen_periodo_academico gpa on gnf.codPeriodoAcademico = gpa.codigo \n" +
			"group by gm.nombre,gpa.descripcion")
	List<?> listaMateriasHistorico();
	@Query(nativeQuery = true, name = "EstudianteDatos.getNotasEstudiantesMateria")
	List<EstudianteDatos> getEstudianteMateriaParalelo(@Param("codMateria") Integer codMateria, @Param("codPA") Integer codPA);
}

