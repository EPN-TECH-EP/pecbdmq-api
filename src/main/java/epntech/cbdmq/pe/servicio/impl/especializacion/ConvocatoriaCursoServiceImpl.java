package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CONVOCATORIA;
import static epntech.cbdmq.pe.constante.EspecializacionConst.*;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCurso;
import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCursoData;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.ConvocatoriaCursoDataRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.ConvocatoriaCursoEspRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.ConvocatoriaCursoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.especializacion.ConvocatoriaCursoService;
import jakarta.mail.MessagingException;

@Service
public class ConvocatoriaCursoServiceImpl implements ConvocatoriaCursoService {

	@Autowired
	private ConvocatoriaCursoEspRepository convocatoriaCursoEspRepository;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private ConvocatoriaCursoRepository convocatoriaCursoRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private ConvocatoriaDocumentoRepository convocatoriaDocumentoRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ConvocatoriaCursoDataRepository convocatoriaCursoDataRepository;

	@Override
	public ConvocatoriaCurso save(ConvocatoriaCurso convocatoriaCurso, List<MultipartFile> archivos)
			throws DataException, IOException, ArchivoMuyGrandeExcepcion {

		Optional<ConvocatoriaCurso> objGuardado = convocatoriaCursoRepository
				.findByNombreConvocatoriaIgnoreCase(convocatoriaCurso.getNombreConvocatoria());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		Optional<Curso> curso = cursoRepository.getCursoAprobado(convocatoriaCurso.getCodCursoEspecializacion());
		if (curso.isEmpty())
			throw new DataException(CURSO_NO_APROBADO);

		Optional<ConvocatoriaCurso> convocatoriaCursoOptional = convocatoriaCursoRepository
				.getConvocatoriaByCodCursoEspecializacion(convocatoriaCurso.getCodCursoEspecializacion());
		// System.out.println("convocatoriaCursoOptional.isPresent(): " +
		// convocatoriaCursoOptional.isPresent());
		if (convocatoriaCursoOptional.isPresent())
			throw new DataException(CONVOCATORIA_CURSO_EXISTE);

		return convocatoriaCursoEspRepository.insertarConvocatoriaDocumentos(convocatoriaCurso, archivos);
	}

	@Override
	public List<ConvocatoriaCurso> listAll() {
		// TODO Auto-generated method stub
		return convocatoriaCursoRepository.findAll();
	}

	@Override
	public Optional<ConvocatoriaCurso> getByID(Long codConvocatoria) throws DataException {
		Optional<ConvocatoriaCurso> convocatoriaCurso = convocatoriaCursoRepository.findById(codConvocatoria);
		if (convocatoriaCurso.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		return convocatoriaCurso;
	}

	@Override
	public Optional<ConvocatoriaCurso> getByCurso(Long codCursoEspecializacion) throws DataException {
		Optional<ConvocatoriaCurso> convocatoriaCurso = convocatoriaCursoRepository
				.getConvocatoriaByCodCursoEspecializacion(codCursoEspecializacion);
		if (convocatoriaCurso.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		return convocatoriaCurso;
	}

	@Override
	public void delete(Long codConvocatoria) throws DataException {
		Optional<ConvocatoriaCurso> convocatoriaCurso = convocatoriaCursoRepository.findById(codConvocatoria);
		if (convocatoriaCurso.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		convocatoriaCursoRepository.deleteById(codConvocatoria);
	}

	@Override
	public ConvocatoriaCurso update(ConvocatoriaCurso convocatoriaCursoActualizado) throws DataException {
		Optional<ConvocatoriaCurso> objGuardado = convocatoriaCursoRepository
				.findByNombreConvocatoriaIgnoreCase(convocatoriaCursoActualizado.getNombreConvocatoria());
		if (objGuardado.isPresent()
				&& !objGuardado.get().getCodConvocatoria().equals(convocatoriaCursoActualizado.getCodConvocatoria())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return convocatoriaCursoRepository.save(convocatoriaCursoActualizado);
	}

	@Override
	public void deleteDocumento(Long codConvocatoria, Long codDocumento) throws DataException {
		Optional<Documento> documentoOptional;
		Documento documento = new Documento();

		// System.out.println("id: " + codDocumento);
		documentoOptional = documentoRepository.findById(codDocumento.intValue());
		documento = documentoOptional.get();

		Path ruta = Paths.get(documento.getRuta());

		// System.out.println("ruta: " + ruta);
		if (Files.exists(ruta)) {
			try {
				// System.out.println("ruta" + ruta);
				Files.delete(ruta);
				
				convocatoriaDocumentoRepository.deleteByCodConvocatoriaAndCodDocumento(codConvocatoria.intValue(),
						codDocumento.intValue());
				documentoRepository.deleteById(codDocumento.intValue());
			} catch (Exception e) {

				throw new DataException(e.getMessage());
				// e.printStackTrace();
			}

		}

	}

	@Override
	public void notificar(String mensaje, Long codConvocatoria) throws MessagingException, DataException {
		Optional<ConvocatoriaCursoData> convocatoriaCursoOptional = convocatoriaCursoDataRepository.getConvocatoriaCurso(codConvocatoria);
		if(convocatoriaCursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		ConvocatoriaCursoData convocatoriaCurso = new ConvocatoriaCursoData();
		convocatoriaCurso = convocatoriaCursoOptional.get();
		
		String msg = String.format(mensaje, convocatoriaCurso.getNombreCatalogoCurso(), convocatoriaCurso.getFechaInicioConvocatoria(), convocatoriaCurso.getHoraInicioConvocatoria(), convocatoriaCurso.getFechaFinConvocatoria(), convocatoriaCurso.getHoraFinConvocatoria());
		
		String[] destinatarios = convocatoriaCurso.getEmailNotificacion().split(",");
		
		emailService.enviarEmail(destinatarios, EMAIL_SUBJECT_CONVOCATORIA, msg);
		
	}

	@Override
	public Boolean validaConvocatoriaCursoActiva(Long codConvocatoria) {
		// TODO Auto-generated method stub
		return convocatoriaCursoRepository.validaConvocatoriaCursoActiva(codConvocatoria);
	}

}
