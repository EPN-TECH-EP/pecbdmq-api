package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.NotasFormacion;

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
}

