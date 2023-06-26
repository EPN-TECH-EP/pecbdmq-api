package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MateriaPeriodoRepository extends JpaRepository<MateriaPeriodo, Integer>{

    @Query("select mp from MateriaPeriodo mp\n" +
            "left join gen_materia gm on gm.codMateria = mp.codMateria \n" +
            "left join gen_periodo_academico gpa on gpa.codigo= mp.codPeriodoAcademico \n" +
            "where gm.nombre = :nombreMateria\n" +
            "and gpa.descripcion = :periodo")
    Page<MateriaPeriodo> getMateriaByNombrePeriodo(@Param("nombreMateria") String nombreMateria, @Param("periodo") String periodo, Pageable pageable);


    Optional<MateriaPeriodo> findByCodMateriaAndCodPeriodoAcademico(Integer codMateria, Integer codPeriodoAcademico);
}
