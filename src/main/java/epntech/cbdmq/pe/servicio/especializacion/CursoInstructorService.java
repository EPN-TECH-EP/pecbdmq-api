package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
import epntech.cbdmq.pe.dto.especializacion.CursoInstructorRequest;
import epntech.cbdmq.pe.dto.especializacion.CursoInstructorResponse;

public interface CursoInstructorService {

	CursoInstructor save(CursoInstructorRequest cursoInstructorRequest) ;
	
	CursoInstructor update(CursoInstructorRequest cursoInstructorRequest) ;
	
	List<CursoInstructor> listAll();
	
	Optional<CursoInstructor> getById(Long codInstructorCurso);
	
	void delete(Long codInstructorCurso);
	
	List<CursoInstructorResponse> listInstructoresCurso(Long codCurso);
	CursoInstructorResponse getInstructorByTipoInsCurso(Long codCurso, String nombreTipoInstructor);
	CursoInstructorResponse getInstructorCoordinadorCurso(Long codInstructorCurso);
	
	List<InstructoresCurso> listCursosInstructor(Long codInstructor);

	List<CursoInstructor> getByusuario(int codUsuario);


	
}

