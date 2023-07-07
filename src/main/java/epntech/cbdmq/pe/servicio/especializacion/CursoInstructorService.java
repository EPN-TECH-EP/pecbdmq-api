package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CursoInstructorService {

	CursoInstructor save(CursoInstructor cursoInstructor) throws DataException;
	
	CursoInstructor update(CursoInstructor cursoInstructorActualizado) throws DataException;
	
	List<CursoInstructor> listAll();
	
	Optional<CursoInstructor> getById(Long codInstructorCurso) throws DataException;
	
	void delete(Long codInstructorCurso) throws DataException;
	
	List<InstructoresCurso> listInstructoresCurso(Long codCurso);
	
	List<InstructoresCurso> listCursosInstructor(Long codInstructor);
	
}

