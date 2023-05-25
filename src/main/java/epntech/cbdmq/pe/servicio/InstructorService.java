package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Instructor;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface InstructorService {

	 Instructor save(Instructor obj) throws DataException;

	    List<Instructor> getAll();

	    Optional<Instructor> getById(Integer codigo);

	    Instructor update(Instructor objActualizado);

	    void delete(Integer codigo);
		Instructor getInstructorByUser(String coduser);
	    
	
	
}
