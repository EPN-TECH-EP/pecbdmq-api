package epntech.cbdmq.pe.servicio;


import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.InstructorMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface InstructorMateriaService {

	InstructorMateria save(InstructorMateria obj) throws DataException;

	    List<InstructorMateria> getAll();

	    Optional<InstructorMateria> getById(Integer codigo);

	    InstructorMateria update(InstructorMateria objActualizado) throws DataException;

	    void delete(Integer codigo);
	

}

