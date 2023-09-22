package epntech.cbdmq.pe.servicio.impl.especializacion;

import epntech.cbdmq.pe.dominio.admin.especializacion.NotasEspecializacion;
import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import epntech.cbdmq.pe.dto.especializacion.NotasEspecializacionDTO;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.admin.especializacion.NotasEspecializacionRepository;
import epntech.cbdmq.pe.servicio.especializacion.NotasEspecializacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class NotasEspecializacionServiceImpl implements NotasEspecializacionService {

	@Autowired
	private NotasEspecializacionRepository notasEspecializacionRepository;

	@Override
	public NotasEspecializacion save(NotasEspecializacionDTO notasEspecializacionDTO) {
		if (notasEspecializacionDTO.getCodNotaEspecializacion() == null) {
			return guardar(notasEspecializacionDTO);
		} else {
			return  actualizar(notasEspecializacionDTO);
		}
	}

	private NotasEspecializacion guardar(NotasEspecializacionDTO notasEspecializacionDTO) {

		Optional<NotasEspecializacion> notasEspecializacionBuscar = notasEspecializacionRepository
				.findByCodInscripcion(notasEspecializacionDTO.getCodInscripcion());

		if(notasEspecializacionBuscar.isPresent()){
			return modificar(notasEspecializacionBuscar.get(), notasEspecializacionDTO);
		}

		NotasEspecializacion notasEspecializacion = new NotasEspecializacion();
		notasEspecializacion.setFechaCreaNota(LocalDate.now());
		notasEspecializacion.setHoraCreaNota(LocalTime.now());
		notasEspecializacion.setFechaModNota(LocalDate.now());
		notasEspecializacion.setHoraModNota(LocalTime.now());
		notasEspecializacion.setNotaFinalEspecializacion(notasEspecializacionDTO.getNotaFinalEspecializacion());
		notasEspecializacion.setNotaSupletorio(notasEspecializacionDTO.getNotaSupletorio());
		notasEspecializacion.setCodInscripcion(notasEspecializacionDTO.getCodInscripcion());
		notasEspecializacion.setCodInstructor(notasEspecializacionDTO.getCodInstructor());

		return notasEspecializacionRepository.save(notasEspecializacion);
	}

	private NotasEspecializacion actualizar(NotasEspecializacionDTO notasEspecializacionDTO) {
		NotasEspecializacion notasEspecializacion = notasEspecializacionRepository
				.findById(notasEspecializacionDTO.getCodNotaEspecializacion())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		return modificar(notasEspecializacion, notasEspecializacionDTO);
	}

	private NotasEspecializacion modificar(NotasEspecializacion notasEspecializacion, NotasEspecializacionDTO notasEspecializacionDTO) {
		notasEspecializacion.setFechaModNota(LocalDate.now());
		notasEspecializacion.setHoraModNota(LocalTime.now());
		notasEspecializacion.setNotaFinalEspecializacion(notasEspecializacionDTO.getNotaFinalEspecializacion());
		notasEspecializacion.setNotaSupletorio(notasEspecializacionDTO.getNotaSupletorio());

		return notasEspecializacionRepository.save(notasEspecializacion);
	}

	@Override
	public NotasEspecializacion getById(int id) {
		return notasEspecializacionRepository.findById(id)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
	}

	@Override
	public List<NotaMateriaByEstudiante> getHistoricosByCursoAndEstudiante(Integer codCurso, Integer codEstudiante) {
		return notasEspecializacionRepository.findNotasByCursoAndEstudiante(codCurso, codEstudiante);
	}

	@Override
	public List<NotasEspecializacionDTO> getAllByCurso(Integer codCurso) {
		return notasEspecializacionRepository.findNotasCurso(codCurso);
	}

	@Override
	public List<NotasEspecializacionDTO> getAllAprobadosByCurso(Integer codCurso) {
		return notasEspecializacionRepository.findAprobadosCurso(codCurso);
	}

}
