package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Estudiante;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EstudianteRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.InscripcionEspRepository;
import epntech.cbdmq.pe.servicio.especializacion.InscripcionEspService;

@Service
public class InscripcionEspServiceImpl implements InscripcionEspService {
	
	@Autowired
	private InscripcionEspRepository inscripcionEspRepository;
	@Autowired
	private EstudianteRepository estudianteRepository;
	@Autowired
	private CursoRepository cursoRepository;

	@Override
	public InscripcionEsp save(InscripcionEsp inscripcionEsp) throws DataException {
		Optional<Estudiante> estudianteOptional = estudianteRepository.findById(inscripcionEsp.getCodEstudiante().intValue());
		Optional<Curso> cursoOptional = cursoRepository.findById(inscripcionEsp.getCodCursoEspecializacion());
		
		if(estudianteOptional.isEmpty() || cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		

		return inscripcionEspRepository.save(inscripcionEsp);
	}

	@Override
	public InscripcionEsp update(InscripcionEsp inscripcionEspActualizada) throws DataException {
		// TODO Auto-generated method stub
		return inscripcionEspRepository.save(inscripcionEspActualizada);
	}

	@Override
	public Optional<InscripcionEsp> getById(Long codInscripcion) throws DataException {
		// TODO Auto-generated method stub
		return inscripcionEspRepository.findById(codInscripcion);
	}

	@Override
	public List<InscripcionEsp> getAll() {
		// TODO Auto-generated method stub
		return inscripcionEspRepository.findAll();
	}

	@Override
	public void delete(Long codInscripcion) throws DataException {
		// TODO Auto-generated method stub
		inscripcionEspRepository.deleteById(codInscripcion);
	}

	@Override
	public InscripcionEsp uploadFiles(Long codInscripcion, List<MultipartFile> archivos) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

}
