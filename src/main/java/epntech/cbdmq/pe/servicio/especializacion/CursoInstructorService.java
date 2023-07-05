package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;

public interface CursoInstructorService {

	CursoInstructor save(CursoInstructor cursoInstructor);
	
	CursoInstructor update(CursoInstructor cursoInstructorActualizado);
	
	List<CursoInstructor> listAll();
	
	Optional<CursoInstructor> getById(Long codInstructorCurso);
	
	void delete(Long codInstructorCurso);
	
	List<InstructoresCurso> listInstructoresCurso(Long codCurso);
	
	List<InstructoresCurso> listCursosInstructor(Long codInstructor);
	
}

