package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;
import epntech.cbdmq.pe.dominio.admin.InstructorMateria;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionInstructor;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionInstructor;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionInstructor;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import org.springframework.data.domain.Pageable;

public interface InstructorService {

	Instructor save(Instructor obj) throws DataException;

	List<InstructorDatos> getAll();

	Optional<Instructor> getById(Integer codigo);

	Instructor update(Instructor objActualizado);

	void delete(Integer codigo);
		Instructor getInstructorByUser(String coduser);
	
	void saveAllMaterias(List<InstructorMateria> obj);

}

