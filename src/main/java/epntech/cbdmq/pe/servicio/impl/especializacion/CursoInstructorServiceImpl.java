package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoInstructorRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.InstructoresCursoRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoInstructorService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoInstructorServiceImpl implements CursoInstructorService {

	@Autowired
	private CursoInstructorRepository cursoInstructorRepository;
	@Autowired
	private InstructoresCursoRepository instructoresCursoRepository;

	@Override
	public CursoInstructor save(CursoInstructor cursoInstructor) throws DataException {
		Optional<CursoInstructor> cursoInstructorOptional = cursoInstructorRepository
				.findByCodInstructorAndCodCursoEspecializacion(cursoInstructor.getCodInstructor().intValue(),
						cursoInstructor.getCodCursoEspecializacion());

		if (cursoInstructorOptional.isPresent())
			throw new DataException(REGISTRO_YA_EXISTE);

		LocalDate fecha = LocalDate.now();
		cursoInstructor.setFechaActual(fecha);
		return cursoInstructorRepository.save(cursoInstructor);
	}

	@Override
	public CursoInstructor update(CursoInstructor cursoInstructorActualizado) {
		CursoInstructor cursoInstructor = cursoInstructorRepository.findById(cursoInstructorActualizado.getCodInstructorCurso())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		validarCursoInstructor(cursoInstructorActualizado);

		cursoInstructor.setCodCursoEspecializacion(cursoInstructorActualizado.getCodCursoEspecializacion());
		cursoInstructor.setCodInstructor(cursoInstructorActualizado.getCodInstructor());
		cursoInstructor.setCodTipoInstructor(cursoInstructorActualizado.getCodTipoInstructor());
		cursoInstructor.setDescripcion(cursoInstructorActualizado.getDescripcion());
		cursoInstructor.setEstado(cursoInstructorActualizado.getEstado());

		return cursoInstructorRepository.save(cursoInstructor);
	}

	private void validarCursoInstructor(CursoInstructor cursoInstructorActualizado) {
		Optional<CursoInstructor> cursoInstructorOptional = cursoInstructorRepository
				.findByCodInstructorAndCodCursoEspecializacion(cursoInstructorActualizado.getCodInstructor().intValue(),
						cursoInstructorActualizado.getCodCursoEspecializacion());

		if (cursoInstructorOptional.isPresent() && !cursoInstructorOptional.get().getCodInstructorCurso()
				.equals(cursoInstructorActualizado.getCodInstructorCurso()))
			throw new BusinessException(REGISTRO_YA_EXISTE);
	}

	@Override
	public List<CursoInstructor> listAll() {
		return cursoInstructorRepository.findAll();
	}

	@Override
	public Optional<CursoInstructor> getById(Long codInstructorCurso) throws DataException {
		Optional<CursoInstructor> cursoInstructorOptional = cursoInstructorRepository.findById(codInstructorCurso);

		if (cursoInstructorOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		return cursoInstructorRepository.findById(codInstructorCurso);
	}

	@Override
	public void delete(Long codInstructorCurso) throws DataException {
		Optional<CursoInstructor> cursoInstructorOptional = cursoInstructorRepository.findById(codInstructorCurso);
		if (cursoInstructorOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		cursoInstructorRepository.deleteById(codInstructorCurso);

	}

	@Transactional(readOnly = true)
	@Override
	public List<InstructoresCurso> listInstructoresCurso(Long codCurso) {
		return instructoresCursoRepository.findInstructoresCurso(codCurso);
	}

	@Transactional(readOnly = true)
	@Override
	public List<InstructoresCurso> listCursosInstructor(Long codInstructor) {
		return instructoresCursoRepository.findCursosInstructor(codInstructor);
	}

}