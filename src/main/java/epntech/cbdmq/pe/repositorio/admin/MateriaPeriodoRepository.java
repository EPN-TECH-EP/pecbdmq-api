package epntech.cbdmq.pe.repositorio.admin;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface MateriaPeriodoRepository extends JpaRepository<MateriaPeriodo, Integer>{

    @Query("select mp from MateriaPeriodo mp\n" +
            "left join gen_materia gm on gm.codMateria = mp.codMateria \n" +
            "left join gen_periodo_academico gpa on gpa.codigo= mp.codPeriodoAcademico \n" +
            "where gm.nombre = :nombreMateria\n" +
            "and gpa.descripcion = :periodo")
    Page<MateriaPeriodo> getMateriaByNombrePeriodo(@Param("nombreMateria") String nombreMateria, @Param("periodo") String periodo, Pageable pageable);


    Optional<MateriaPeriodo> findByCodMateriaAndCodPeriodoAcademico(Integer codMateria, Integer codPeriodoAcademico);
    List<MateriaPeriodo> getAllByCodPeriodoAcademico(Integer codPeriodoAcademico);
    @Query("select gemp from MateriaParalelo gnf left join MateriaPeriodo gemp on gnf.codMateriaPeriodo = gemp.codMateriaPeriodo where gnf.codMateriaParalelo = :codMateriaParalelo")
    Optional<MateriaPeriodo> findByMateriaParalelo(@Param("codMateriaParalelo")Integer codMateriaParalelo);
}
