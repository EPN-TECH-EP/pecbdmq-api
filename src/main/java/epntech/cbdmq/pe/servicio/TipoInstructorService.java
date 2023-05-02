package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoInstructor;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface TipoInstructorService {

    TipoInstructor save(TipoInstructor obj) throws DataException;

    List<TipoInstructor> getAll();

    Optional<TipoInstructor> getById(Integer codigo);

    TipoInstructor update(TipoInstructor objActualizado) throws DataException;

    void delete(Integer codigo);
	
	
	
}
