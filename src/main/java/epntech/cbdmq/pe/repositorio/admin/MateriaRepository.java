package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Materia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {

	Optional<Materia> findByNombreIgnoreCase(String nombre);
	@Query("select mat from InstructorMateriaParalelo imp\n" +
			"left join gen_tipo_instructor ti on imp.codTipoInstructor= ti.codigo\n" +
			"left join gen_instructor i on imp.codInstructor=i.codInstructor\n" +
			"left join MateriaParalelo mp on mp.codMateriaParalelo = imp.codMateriaParalelo\n" +
			"left join MateriaPeriodo mpe on mp.codMateriaPeriodo=mpe.codMateriaPeriodo\n" +
			"left join gen_materia mat on mpe.codMateria= mat.codMateria\n" +
			"where i.codInstructor = :codInstructor\n" +
			"and ti.nombre=:nombreTipoInstructor\n" +
			"and mpe.codPeriodoAcademico= :periodoAcademico\n")
	List<Materia> getAllByInstructorPA(@Param("codInstructor") Integer codInstructor, @Param("nombreTipoInstructor") String nombreTipoInstructor, @Param("periodoAcademico") Integer periodoAcademico);
}
