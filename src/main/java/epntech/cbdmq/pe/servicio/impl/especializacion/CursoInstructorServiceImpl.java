package epntech.cbdmq.pe.servicio.impl.especializacion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoInstructorRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.InstructoresCursoRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoInstructorService;

@Service
public class CursoInstructorServiceImpl implements CursoInstructorService {
	
	@Autowired
	private CursoInstructorRepository cursoInstructorRepository;
	@Autowired
	private InstructoresCursoRepository instructoresCursoRepository;

	@Override
	public CursoInstructor save(CursoInstructor cursoInstructor) {
		LocalDate fecha = LocalDate.now();
		cursoInstructor.setFechaActual(fecha);
		return cursoInstructorRepository.save(cursoInstructor);
	}

	@Override
	public CursoInstructor update(CursoInstructor cursoInstructorActualizado) {
		// TODO Auto-generated method stub
		return cursoInstructorRepository.save(cursoInstructorActualizado);
	}

	@Override
	public List<CursoInstructor> listAll() {
		// TODO Auto-generated method stub
		return cursoInstructorRepository.findAll();
	}

	@Override
	public Optional<CursoInstructor> getById(Long codInstructorCurso) {
		// TODO Auto-generated method stub
		return cursoInstructorRepository.findById(codInstructorCurso);
	}

	@Override
	public void delete(Long codInstructorCurso) {
		cursoInstructorRepository.deleteById(codInstructorCurso);
		
	}

	@Override
	public List<InstructoresCurso> listInstructoresCurso(Long codCurso) {
		// TODO Auto-generated method stub
		return instructoresCursoRepository.findInstructoresCurso(codCurso);
	}

	@Override
	public List<InstructoresCurso> listCursosInstructor(Long codInstructor) {
		// TODO Auto-generated method stub
		return instructoresCursoRepository.findCursosInstructor(codInstructor);
	}

}
