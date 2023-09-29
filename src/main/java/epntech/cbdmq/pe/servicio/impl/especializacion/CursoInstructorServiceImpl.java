package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;
import epntech.cbdmq.pe.dto.especializacion.CursoInstructorRequest;
import epntech.cbdmq.pe.dto.especializacion.CursoInstructorResponse;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.admin.DatoPersonalRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.InstructorCursoRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
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
	private InstructorCursoRepository instructorCursoRepository;
	@Autowired
	private InstructorRepository instructorRepository;
	@Autowired
	private DatoPersonalRepository datoPersonalRepository;

	@Override
	public CursoInstructor save(CursoInstructorRequest cursoInstructorRequest) {
		if (cursoInstructorRequest.getCodInstructorCurso() == 0) {
			return this.guardar(cursoInstructorRequest);
		} else {
			return this.actualizar(cursoInstructorRequest);
		}
	}

	@Transactional
	private CursoInstructor guardar(CursoInstructorRequest cursoInstructorRequest) {
		Instructor instructor = instructorRepository.findByCodDatosPersonales(cursoInstructorRequest.getCodDatosPersonales().intValue())
				.orElseGet(() -> {
					Instructor newInstructor = new Instructor();
					newInstructor.setEstado("ACTIVO");
					newInstructor.setCodEstacion(cursoInstructorRequest.getCodEstacion());
					newInstructor.setCodTipoProcedencia(cursoInstructorRequest.getCodTipoProcedencia());
					newInstructor.setCodTipoContrato(cursoInstructorRequest.getCodTipoContrato());
					newInstructor.setCodDatosPersonales(cursoInstructorRequest.getCodDatosPersonales());
					newInstructor.setCodUnidadGestion(cursoInstructorRequest.getCodUnidadGestion());
					return instructorRepository.save(newInstructor);
				});

		// busca cursoInstructor por cursoInstructorRequest.getCodInstructor() y cursoInstructorRequest.getCodCursoEspecializacion()
		Optional<CursoInstructor> cursoInstructorOptional = cursoInstructorRepository
				.findByCodInstructorAndCodCursoEspecializacion(instructor.getCodInstructor().intValue(),
						cursoInstructorRequest.getCodCursoEspecializacion());

		CursoInstructor cursoInstructor;

		// si el instructor ya estaba registrado en el curso, se actualiza el estado a ACTIVO
		if (cursoInstructorOptional.isPresent()) {
			cursoInstructor = cursoInstructorOptional.get();

			cursoInstructor.setEstado("ACTIVO");
			cursoInstructor.setFechaActual(LocalDate.now());
		}
		// si no estaba registrado, se crea un nuevo registro
		else {
			cursoInstructor = new CursoInstructor();
			cursoInstructor.setCodInstructor(instructor.getCodInstructor());
			cursoInstructor.setEstado("ACTIVO");
			cursoInstructor.setCodTipoInstructor(cursoInstructorRequest.getCodTipoInstructor());
			cursoInstructor.setCodCursoEspecializacion(cursoInstructorRequest.getCodCursoEspecializacion());
			cursoInstructor.setDescripcion(cursoInstructor.getDescripcion());
			cursoInstructor.setFechaActual(LocalDate.now());
		}

		return cursoInstructorRepository.save(cursoInstructor);
	}

	@Override
	public CursoInstructor update(CursoInstructorRequest cursoInstructorRequest) {
		return this.actualizar(cursoInstructorRequest);
	}

	@Transactional
	private CursoInstructor actualizar(CursoInstructorRequest cursoInstructorRequest) {
		Instructor instructor = instructorRepository.findById(cursoInstructorRequest.getCodInstructor())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		instructor.setCodUnidadGestion(cursoInstructorRequest.getCodUnidadGestion());
		instructor.setCodTipoContrato(cursoInstructorRequest.getCodTipoContrato());
		instructor.setCodEstacion(cursoInstructorRequest.getCodEstacion());
		instructor.setCodTipoProcedencia(cursoInstructorRequest.getCodTipoProcedencia());

		instructorRepository.save(instructor);

		CursoInstructor cursoInstructor = cursoInstructorRepository
				.findById(cursoInstructorRequest.getCodInstructorCurso())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		validarCursoInstructor(cursoInstructorRequest);

		cursoInstructor.setCodTipoInstructor(cursoInstructorRequest.getCodTipoInstructor());
		cursoInstructor.setDescripcion(cursoInstructorRequest.getDescripcion());

		return cursoInstructorRepository.save(cursoInstructor);
	}

	private void validarCursoInstructor(CursoInstructorRequest cursoInstructorActualizado) {
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
	public List<CursoInstructorResponse> listInstructoresCurso(Long codCurso) {
		return instructorCursoRepository.findInstructoresCurso(codCurso);
	}
	@Transactional(readOnly = true)
	@Override
	public CursoInstructorResponse getInstructorByTipoInsCurso(Long codCurso,String nombreTipoCurso) {
		return instructorCursoRepository.getInstructorByTipoInsCurso(codCurso,nombreTipoCurso);
	}
	@Transactional(readOnly = true)
	@Override
	public CursoInstructorResponse getInstructorCoordinadorCurso(Long codInstructorCurso) {
		return this.getInstructorByTipoInsCurso(codInstructorCurso,"COORDINADOR");
	}

	@Transactional(readOnly = true)
	@Override
	public List<InstructoresCurso> listCursosInstructor(Long codInstructor) {
		return instructoresCursoRepository.findCursosInstructor(codInstructor);
	}

	@Override
	public List<CursoInstructor> getByusuario(int codUsuario) {
		List<CursoInstructor> lista = cursoInstructorRepository.findByCodUsuario(codUsuario);
		if (lista != null && !lista.isEmpty()) {
			return lista;
		} else {
			throw new BusinessException(REGISTRO_NO_EXISTE);
		}
	}

}