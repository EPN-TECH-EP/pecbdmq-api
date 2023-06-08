package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.EspCursoInstructor;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EspCursoInstructorService {

	EspCursoInstructor save(EspCursoInstructor obj) throws DataException;

	    List<EspCursoInstructor> getAll();

	    Optional<EspCursoInstructor> getById(int id);

	    EspCursoInstructor update(EspCursoInstructor objActualizado)throws DataException;

	    void delete(int id)throws DataException;
	    
	
	}
	
	
	

