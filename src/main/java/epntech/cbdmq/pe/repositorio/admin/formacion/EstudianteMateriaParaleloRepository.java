package epntech.cbdmq.pe.repositorio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EstudianteMateriaParaleloRepository extends JpaRepository<EstudianteMateriaParalelo,Integer> {
    @Query("select gemp from NotasFormacion gnf left join EstudianteMateriaParalelo gemp on gnf.codEstudianteMateriaParalelo = gemp.codEstudianteMateriaParalelo where gnf.codNotaFormacion = :codNotaFormacion")
    Optional<EstudianteMateriaParalelo> findByNotaFormacion(@Param("codNotaFormacion")Integer codNotaFormacion);
    Optional<EstudianteMateriaParalelo> findByCodMateriaParaleloAndAndCodEstudiante(Integer codMateriaParalelo, Integer codEstudiante);
}
