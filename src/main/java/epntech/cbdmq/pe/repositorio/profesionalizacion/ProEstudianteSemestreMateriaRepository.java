package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProEstudianteSemestreMateria;

import java.util.Optional;

public interface ProEstudianteSemestreMateriaRepository extends ProfesionalizacionRepository<ProEstudianteSemestreMateria, Integer> {

    Optional<ProEstudianteSemestreMateria> findByCodPeriodoAndCodPeriodoEstudianteSemestreAndCodMateria(int codPeriodo, int codPeriodoEstudianteSemestre, int codMateria);
}
