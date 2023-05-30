package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Instructor;
import epntech.cbdmq.pe.dominio.admin.InstructorMateria;
import epntech.cbdmq.pe.dominio.util.*;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import org.springframework.data.domain.Pageable;

public interface InstructorService {

	Instructor save(Instructor obj) throws DataException;

	List<Instructor> getAll();

	Optional<Instructor> getById(Integer codigo);

	Instructor update(Instructor objActualizado);

	    void delete(Integer codigo);
		Instructor getInstructorByUser(String coduser);
	    
	void saveAllMaterias(List<InstructorMateria> obj);
	List<FormacionInstructor> getFormHistoricos(Integer codEstudiante, Pageable pageable);
	List<EspecializacionInstructor> getEspecializacionHistoricos(Integer codInstructor, Pageable pageable);
	List<ProfesionalizacionInstructor> getProfesionalizacionHistoricos(Integer codInstructor, Pageable pageable);
	

}

