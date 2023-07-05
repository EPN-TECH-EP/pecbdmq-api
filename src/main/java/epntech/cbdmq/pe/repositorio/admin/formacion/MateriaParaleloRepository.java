package epntech.cbdmq.pe.repositorio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MateriaParaleloRepository extends JpaRepository<MateriaParalelo,Integer> {
    Optional<MateriaParalelo> findByCodMateriaPeriodoAndCodParalelo(Integer materiaParalelo, Integer paralelo);
}
