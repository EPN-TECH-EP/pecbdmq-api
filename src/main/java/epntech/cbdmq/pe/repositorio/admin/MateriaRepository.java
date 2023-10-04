package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.admin.formacion.MateriaParaleloDto;
import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Materia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {

	Optional<Materia> findByNombreIgnoreCase(String nombre);
	@Query(name = "MateriaParaleloDto.getByInstructor", nativeQuery = true)
	List<MateriaParaleloDto> getAllByInstructorPA(@Param("codInstructor") Integer codInstructor, @Param("nombreTipoInstructor") String nombreTipoInstructor, @Param("periodoAcademico") Integer periodoAcademico);
	@Query("Select m from ProMateriaSemestre pms\n" +
            "left join gen_materia m on pms.codMateria=m.codMateria\n" +
            "left join pro_periodo_semestre ps on pms.codPeriodoSemestre=ps.codPeriodoSemestre\n" +
            " left  join pro_periodo p on ps.codPeriodo=p.codigoPeriodo\n" +
            "where p.estado=:estadoPeriodo\n")

	List<Materia> getAllByPeriodoSemestre(@Param("estadoPeriodo") String estadoPeriodo);
}
