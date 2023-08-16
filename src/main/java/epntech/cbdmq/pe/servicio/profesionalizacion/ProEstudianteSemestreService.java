package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProEstudianteSemestre;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import java.util.List;
import java.util.Optional;

public interface ProEstudianteSemestreService {
    List<ProEstudianteSemestre> getAll();
    Optional<ProEstudianteSemestre> getById(int id);

    ProEstudianteSemestre save(ProEstudianteSemestre obj) throws DataException;
    ProEstudianteSemestre update(ProEstudianteSemestre datosGuardados) throws DataException;
    void delete(int codigo) throws DataException;
}
