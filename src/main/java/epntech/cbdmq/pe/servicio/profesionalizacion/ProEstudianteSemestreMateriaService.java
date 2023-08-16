package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProEstudianteSemestreMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import java.util.List;
import java.util.Optional;

public interface ProEstudianteSemestreMateriaService {

    List<ProEstudianteSemestreMateria> getAll();

    Optional<ProEstudianteSemestreMateria> getById(int id);

    ProEstudianteSemestreMateria save(ProEstudianteSemestreMateria obj) throws DataException;

    ProEstudianteSemestreMateria update(ProEstudianteSemestreMateria objActualizado) throws DataException;

    void delete(Integer codPeriodoEstudianteSemestreMateria) throws DataException;
}
