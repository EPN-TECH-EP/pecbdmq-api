package epntech.cbdmq.pe.repositorio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MateriaParaleloRepository extends JpaRepository<MateriaParalelo,Integer> {

    @Query("select MateriaParalelo from EstudianteMateriaParalelo gnf left join MateriaParalelo gemp on gnf.codMateriaParalelo = gemp.codMateriaParalelo where gnf.codEstudianteMateriaParalelo = :codEstudianteMateriaParalelo")
    Optional<MateriaParalelo> findByEstudianteMateriaParalelo(@Param("codEstudianteMateriaParalelo")Integer codEstudianteMateriaParalelo);

}
