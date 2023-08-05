package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;
import epntech.cbdmq.pe.dto.CursoInstructorDTO;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.admin.DatoPersonalRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.InstructorRepository;
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
	@Autowired
	private InstructorRepository instructorRepository;
	@Autowired
	private DatoPersonalRepository datoPersonalRepository;

	@Override
	@Transactional
	public CursoInstructor save(CursoInstructorDTO cursoInstructorDTO) {

		Instructor instructor = instructorRepository.findByCodDatosPersonales(cursoInstructorDTO.getCodDatosPersonales().intValue())
				.orElseGet(() -> {
					Instructor newInstructor = new Instructor();
					newInstructor.setEstado("ACTIVO");
					newInstructor.setCodEstacion(cursoInstructorDTO.getCodEstacion());
					newInstructor.setCodTipoProcedencia(cursoInstructorDTO.getCodTipoProcedencia());
					newInstructor.setCodTipoContrato(cursoInstructorDTO.getCodTipoContrato());
					newInstructor.setCodDatosPersonales(cursoInstructorDTO.getCodDatosPersonales());
					newInstructor.setCodUnidadGestion(cursoInstructorDTO.getCodUnidadGestion());
					return instructorRepository.save(newInstructor);
				});

		CursoInstructor cursoInstructor = new CursoInstructor();
		cursoInstructor.setCodInstructor(instructor.getCodInstructor());
		cursoInstructor.setEstado("ACTIVO");
		cursoInstructor.setCodTipoInstructor(cursoInstructorDTO.getCodTipoInstructor());
		cursoInstructor.setCodCursoEspecializacion(cursoInstructorDTO.getCodCursoEspecializacion());
		cursoInstructor.setFechaActual(LocalDate.now());

		return cursoInstructorRepository.save(cursoInstructor);

	}

	@Override
	public CursoInstructor update(CursoInstructorDTO cursoInstructorDTO) {
		CursoInstructor cursoInstructor = cursoInstructorRepository
				.findById(cursoInstructorDTO.getCodInstructorCurso())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		validarCursoInstructor(cursoInstructorDTO);

		cursoInstructor.setCodCursoEspecializacion(cursoInstructorDTO.getCodCursoEspecializacion());
		cursoInstructor.setCodInstructor(cursoInstructorDTO.getCodInstructor());
		cursoInstructor.setCodTipoInstructor(cursoInstructorDTO.getCodTipoInstructor());
		cursoInstructor.setDescripcion(cursoInstructorDTO.getDescripcion());
		cursoInstructor.setEstado(cursoInstructorDTO.getEstado());

		return cursoInstructorRepository.save(cursoInstructor);
	}

	private void validarCursoInstructor(CursoInstructorDTO cursoInstructorActualizado) {
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
	public Optional<CursoInstructor> getById(Long codInstructorCurso) {
		CursoInstructor cursoInstructor = cursoInstructorRepository.findById(codInstructorCurso)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		return cursoInstructorRepository.findById(cursoInstructor.getCodInstructorCurso());
	}

	@Override
	public void delete(Long codInstructorCurso) {
		CursoInstructor cursoInstructor = cursoInstructorRepository.findById(codInstructorCurso)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		cursoInstructorRepository.deleteById(cursoInstructor.getCodInstructorCurso());

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