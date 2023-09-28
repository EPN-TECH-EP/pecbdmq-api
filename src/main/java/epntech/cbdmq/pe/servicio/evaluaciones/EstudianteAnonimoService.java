package epntech.cbdmq.pe.servicio.evaluaciones;

import java.util.List;

public interface EstudianteAnonimoService {

    List<EstudianteAnonimoService> getAll();

    EstudianteAnonimoService save(EstudianteAnonimoService estudianteAnonimo);

    void delete(Long codEstudianteAnonimo);

}
