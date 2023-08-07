package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoInstructor;

public interface TipoInstructorService {

    TipoInstructor save(TipoInstructor obj);

    List<TipoInstructor> getAll();

    TipoInstructor getById(Integer codigo);

    TipoInstructor update(TipoInstructor objActualizado);

    void delete(Integer codigo);

}
