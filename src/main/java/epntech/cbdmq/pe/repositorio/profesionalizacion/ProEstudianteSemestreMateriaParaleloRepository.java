package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParalelo;

import java.util.List;
import java.util.Optional;

public interface ProEstudianteSemestreMateriaParaleloRepository extends ProfesionalizacionRepository<ProSemestreMateriaParalelo, Integer> {

    Optional<ProSemestreMateriaParalelo> findByCodSemestreMateriaAndCodParalelo(int codPeriodoEstudianteSemestreMateria, int codParalelo);
    List<ProSemestreMateriaParalelo> findByCodSemestreMateria(int codPeriodoEstudianteSemestreMateria);
    Optional<ProSemestreMateriaParalelo> findByCodSemestreMateriaAndCodProyecto(int codPeriodoEstudianteSemestreMateria, int codProyecto);
}
