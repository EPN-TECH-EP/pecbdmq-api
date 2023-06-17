package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.MateriaEstudiante;

public interface MateriaEstudianteRepository extends JpaRepository<MateriaEstudiante, Long> {

	@Query(value = "select a.* "
			+ "from cbdmq.gen_materia_estudiante a "
			+ "where a.cod_materia = :codMateria "
			+ "and a.cod_estudiante = :codEstudiante "
			+ "and a.cod_periodo_academico = cbdmq.get_pa_activo()", nativeQuery = true)
	Optional<MateriaEstudiante> getMateriaEstudiante(Long codMateria, Long codEstudiante);
	
	List<MateriaEstudiante> findByCodEstudiante(Long codEstudiante);
}
