package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_ESPECIALIZACION_INSCRIPCION;
import static epntech.cbdmq.pe.constante.EmailConst.*;
import static epntech.cbdmq.pe.constante.MensajesConst.*;
import static epntech.cbdmq.pe.constante.EspecializacionConst.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import epntech.cbdmq.pe.dominio.admin.especializacion.*;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.admin.SubTipoPruebaRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.dominio.util.CursoDatos;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.dominio.util.InscripcionEstudianteDatosEspecializacion;
import epntech.cbdmq.pe.dominio.util.InscritosEspecializacion;
import epntech.cbdmq.pe.dominio.util.InscritosValidos;
import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosDatos;
import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalleEntity;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleEntityRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.especializacion.InscripcionEspService;
import jakarta.mail.MessagingException;

@Service
public class InscripcionEspServiceImpl implements InscripcionEspService {

	@Autowired
	private InscripcionEspRepository inscripcionEspRepository;
	@Autowired
	private EstudianteRepository estudianteRepository;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private InscripcionDocumentoRepository inscripcionDocumentoRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ParametroRepository parametroRepository;
	@Autowired
	private InscripcionDatosRepository inscripcionDatosRepository;
	@Autowired
	private ValidaRequisitosRepository validaRequisitosRepository;
	@Autowired
	private PruebaDetalleRepository pruebaDetalleRepository;
	@Autowired
	private PruebasRepository pruebasRepository;
	@Autowired
	private PruebaDetalleEntityRepository pruebaDetalleEntityRepository;
	@Autowired
	private CursoEntityRepository cursoEntityRepository;
	@Autowired
	private ConvocatoriaCursoRepository convocatoriaCursoRepository;
	@Autowired
	private SubTipoPruebaRepository subTipoPruebaRepository;
	@Autowired
	private CursoRequisitoRepository cursoRequisitoRepository;

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MAXIMO;

	@Override
	public InscripcionEsp save(InscripcionEsp inscripcionEsp) throws DataException {
		Boolean convocatoria = convocatoriaCursoRepository.validaConvocatoriaCursoActiva(inscripcionEsp.getCodCursoEspecializacion());
		if(!convocatoria)
			throw new DataException(CONVOCATORIA_NO_ACTIVA);

		Optional<InscripcionEsp> inscripcionEspRepositoryOptional = inscripcionEspRepository.findByCodEstudianteAndCodCursoEspecializacion(inscripcionEsp.getCodEstudiante(), inscripcionEsp.getCodCursoEspecializacion());
		if(inscripcionEspRepositoryOptional.isPresent())
			throw new DataException(REGISTRO_YA_EXISTE);

		Optional<Estudiante> estudianteOptional = estudianteRepository.findById(inscripcionEsp.getCodEstudiante().intValue());
		Optional<Curso> cursoOptional = cursoRepository.findById(inscripcionEsp.getCodCursoEspecializacion());

		if(estudianteOptional.isEmpty() || cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		LocalDate fechaActual = LocalDate.now();
		inscripcionEsp.setFechaInscripcion(fechaActual);
		inscripcionEsp.setEstado("INSCRITO");

		return inscripcionEspRepository.save(inscripcionEsp);
	}

	@Override
	public InscripcionEsp update(InscripcionEsp inscripcionEspActualizada) throws DataException {
		Optional<InscripcionEsp> inscripcionEspOptional = inscripcionEspRepository.findByCodEstudianteAndCodCursoEspecializacion(inscripcionEspActualizada.getCodEstudiante(), inscripcionEspActualizada.getCodCursoEspecializacion());
		if(inscripcionEspOptional.isPresent() && !inscripcionEspOptional.get().getCodInscripcion().equals(inscripcionEspActualizada.getCodInscripcion()))
			throw new DataException(REGISTRO_YA_EXISTE);

		return inscripcionEspRepository.save(inscripcionEspActualizada);
	}

	@Override
	public Optional<InscripcionDatosEsp> getById(Long codInscripcion) throws DataException {
		Optional<InscripcionEsp> inscripcionEspOptional = inscripcionEspRepository.findById(codInscripcion);
		if(inscripcionEspOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		return inscripcionDatosRepository.findByInscripcion(codInscripcion);
	}

	@Override
	public List<InscripcionDatosEspecializacion> getAll() {
		// TODO Auto-generated method stub
		return inscripcionEspRepository.getAllInscripciones();
	}

	@Override
	public void delete(Long codInscripcion) throws DataException {
		Optional<InscripcionEsp> inscripcionEspOptional = inscripcionEspRepository.findById(codInscripcion);
		if(inscripcionEspOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		inscripcionEspRepository.deleteById(codInscripcion);
	}

	@Override
	public List<Documento> uploadFiles(Long codInscripcion, Long tipoDocumento, List<MultipartFile> archivos) throws DataException, IOException, ArchivoMuyGrandeExcepcion {
		Optional<InscripcionEsp> inscripcionEspOptional = inscripcionEspRepository.findById(codInscripcion);
		if(inscripcionEspOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		return guardarDocumentos(archivos, codInscripcion, tipoDocumento);
	}

	public List<Documento> guardarDocumentos(List<MultipartFile> archivos, Long codInscripcion, Long tipoDocumento)
			throws IOException, ArchivoMuyGrandeExcepcion, DataException {
		Optional<InscripcionEsp> inscripcionEspOptional = inscripcionEspRepository.findById(codInscripcion);
		if(inscripcionEspOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		String resultado;
		List<Documento> documentos = new ArrayList<>();

		resultado = ruta(codInscripcion);
		Path ruta = Paths.get(resultado);

		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

		for (Iterator iterator = archivos.iterator(); iterator.hasNext();) {

			MultipartFile multipartFile = (MultipartFile) iterator.next();
			if (multipartFile.getSize() > TAMAÑO_MAXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			// LOGGER.info("Archivo guardado: " + resultado +

			Documento documento = new Documento();
			documento.setEstado("ACTIVO");
			documento.setTipo(tipoDocumento.intValue());
			documento.setNombre(multipartFile.getOriginalFilename());
			documento.setRuta(resultado + multipartFile.getOriginalFilename());
			documento = documentoRepository.save(documento);
			documentos.add(documento);

			InscripcionDocumento cursoDocumento = new InscripcionDocumento();

			cursoDocumento.setCodInscripcion(codInscripcion);
			cursoDocumento.setCodDocumento((long) documento.getCodDocumento());
			inscripcionDocumentoRepository.save(cursoDocumento);

		}
		return documentos;

	}

	private String ruta(Long codigo) {

		String resultado = null;
		resultado = ARCHIVOS_RUTA + PATH_PROCESO_ESPECIALIZACION_INSCRIPCION + codigo + "/";
		return resultado;
	}

	@Override
	public void deleteDocumento(Long codInscripcion, Long codDocumento) throws DataException {
		Optional<InscripcionEsp> inscripcionEspOptional = inscripcionEspRepository.findById(codInscripcion);
		if(inscripcionEspOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		Optional<Documento> documentoOptional;
		Documento documento = new Documento();

		// System.out.println("id: " + codDocumento);
		documentoOptional = documentoRepository.findById(codDocumento.intValue());
		if(documentoOptional.isEmpty())
			throw new DataException(DOCUMENTO_NO_EXISTE);

		documento = documentoOptional.get();

		Path ruta = Paths.get(documento.getRuta());

		// System.out.println("ruta: " + ruta);
		if (Files.exists(ruta)) {
			try {
				// System.out.println("ruta" + ruta);
				Files.delete(ruta);
				inscripcionDocumentoRepository.deleteByCodInscripcionAndCodDocumento(codInscripcion, codDocumento);
				documentoRepository.deleteById(codDocumento.intValue());

			} catch (Exception e) {

				throw new DataException(e.getMessage());
				// e.printStackTrace();
			}

		}

	}

	@Override
	public void notificarInscripcion(Long codInscripcion) throws MessagingException, DataException {
		InscripcionEsp inscripcionEsp = inscripcionEspRepository.findById(codInscripcion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		InscripcionEstudianteDatosEspecializacion inscripcion = inscripcionEspRepository.getInscripcionEstudiante(inscripcionEsp.getCodInscripcion())
				.orElseThrow(() -> new BusinessException(INSCRIPCION_NO_DATOS));

		Parametro parametro = parametroRepository.findByNombreParametro("especializacion.inscripcion.notificacion.body")
				.orElseThrow(() -> new BusinessException(NO_PARAMETRO));

		String nombres = inscripcion.getNombre() + " " + inscripcion.getApellido();
		String cuerpoHtml = String.format(parametro.getValor(), nombres, inscripcion.getNombreCatalogoCurso(), inscripcion.getFechaInscripcion(), inscripcion.getFechaInicioCurso(), inscripcion.getFechaFinCurso());

		String[] destinatarios = {inscripcion.getCorreoPersonal()};

		emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT_INSCRIPCION, cuerpoHtml);
	}

	@Override
	public Boolean cumplePorcentajeMinimoInscritosCurso(long codCurso) {
		return inscripcionEspRepository.cumplePorcentajeMinimoInscritosCurso(codCurso);
	}

	@Override
	public List<InscripcionDatosEspecializacion> getByCurso(Long codCurso) throws DataException {
		return inscripcionEspRepository.getInscripcionByCurso(codCurso);
	}

	@Override
	public List<ValidaRequisitos> saveValidacionRequisito(List<ValidaRequisitos> validaRequisitos) throws MessagingException, DataException {
		validarRequisitosCursoEspecializacion(validaRequisitos);
		List<ValidaRequisitos> listValidaRequisitos;
		try {
			listValidaRequisitos = validaRequisitosRepository.saveAll(validaRequisitos);
			boolean validaRequisitoEstudiante = validaRequisitosRepository.cumpleRequisitosCurso(validaRequisitos.get(0).getCodCursoEspecializacion(), validaRequisitos.get(0).getCodEstudiante());
			Optional<InscripcionEsp> inscripcion = inscripcionEspRepository.findByCodEstudianteAndCodCursoEspecializacion(validaRequisitos.get(0).getCodEstudiante(), validaRequisitos.get(0).getCodCursoEspecializacion());

			if (inscripcion.isPresent()) {
				if (validaRequisitoEstudiante)
					notificarInscripcion(inscripcion.get().getCodInscripcion(), "especializacion.notificacion.inscripcion.valida");
				else
					notificarInscripcion(inscripcion.get().getCodInscripcion(), "especializacion.notificacion.inscripcion.novalida");
			}
			return listValidaRequisitos;
		} catch (DataIntegrityViolationException dive) {
			throw new BusinessException(REGISTRO_YA_EXISTE);
		}
	}

	private void validarRequisitosCursoEspecializacion(List<ValidaRequisitos> validaRequisitos) {
		if (validaRequisitos.isEmpty()) {
			throw new BusinessException(REQUISITOS_OBLIGATORIO);
		}

		Long codCursoEspecializacion = validaRequisitos.get(0).getCodCursoEspecializacion();
		List<CursoRequisito> cursoRequisitos = cursoRequisitoRepository.findByCodCursoEspecializacion(codCursoEspecializacion);

		Set<Long> requisitosCursoSet = cursoRequisitos.stream()
				.map(CursoRequisito::getCodRequisito)
				.collect(Collectors.toSet());

		boolean allRequisitosExist = validaRequisitos.stream()
				.map(ValidaRequisitos::getCodRequisito)
				.allMatch(requisitosCursoSet::contains);

		if (!allRequisitosExist) {
			throw new BusinessException(REQUISITOS_NO_COINCIDEN);
		}
	}

	@Override
	public List<ValidacionRequisitosDatos> getValidacionRequisito(Long codEstudiante, Long codCursoEspecializacion) {
		return validaRequisitosRepository.findRequisitosPorEstudiante(codEstudiante, codCursoEspecializacion);
	}

	@Override
	public List<ValidaRequisitos> updateValidacionRequisito(List<ValidaRequisitos> validaRequisitos) throws MessagingException, DataException {
		List<ValidaRequisitos> listValidaRequisitos;
		listValidaRequisitos = validaRequisitosRepository.saveAll(validaRequisitos);

		boolean validaRequisitoEstudiante = validaRequisitosRepository.cumpleRequisitosCurso(validaRequisitos.get(0).getCodCursoEspecializacion(), validaRequisitos.get(0).getCodEstudiante());
		Optional<InscripcionEsp> inscripcion = inscripcionEspRepository.findByCodEstudianteAndCodCursoEspecializacion(validaRequisitos.get(0).getCodEstudiante(), validaRequisitos.get(0).getCodCursoEspecializacion());

		if (inscripcion.isPresent()) {
			if (validaRequisitoEstudiante)
				notificarInscripcion(inscripcion.get().getCodInscripcion(), "especializacion.notificacion.inscripcion.valida");
			else
				notificarInscripcion(inscripcion.get().getCodInscripcion(), "especializacion.notificacion.inscripcion.novalida");
		}

		return listValidaRequisitos;
	}

	@Override
	public List<InscritosEspecializacion> getInscritosValidosCurso(Long codCursoEspecializacion) {
		return inscripcionEspRepository.getInscripcionesValidasByCurso(codCursoEspecializacion);
	}

	@Override
	@Async
	public void notificarPrueba(Long codCursoEspecializacion, Long codSubTipoPrueba) throws MessagingException, DataException {
		Curso curso = cursoRepository.findById(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		SubTipoPrueba subTipoPrueba = subTipoPruebaRepository.findById(codSubTipoPrueba.intValue())
				.orElseThrow(() -> new BusinessException(NO_SUBTIPO_PRUEBA));

		PruebaDetalle pruebaDetalle = pruebaDetalleRepository
				.findByCodCursoEspecializacionAndCodSubtipoPrueba(curso.getCodCursoEspecializacion(), subTipoPrueba.getCodSubtipoPrueba().longValue())
				.orElseThrow(() -> new BusinessException(CURSO_NO_PRUEBAS));

		List<InscritosEspecializacion> listaInscritos;
		listaInscritos = inscripcionEspRepository.getInscripcionesValidasByCurso(codCursoEspecializacion);

		for (InscritosEspecializacion inscritosEspecializacion : listaInscritos) {

			Optional<Parametro> parametro = parametroRepository.findByNombreParametro("especializacion.notificacion.pruebas");
			if(parametro.isEmpty())
				throw new DataException(NO_PARAMETRO);

			String nombres = inscritosEspecializacion.getNombre() + " " + inscritosEspecializacion.getApellido();
			String cuerpoHtml = String.format(parametro.get().getValor(), nombres, inscritosEspecializacion.getNombreCatalogoCurso(), pruebaDetalle.getDescripcionPrueba(),
					subTipoPrueba.getNombre(), pruebaDetalle.getFechaInicio(), pruebaDetalle.getFechaFin(), pruebaDetalle.getHora());

			String[] destinatarios = {inscritosEspecializacion.getCorreoPersonal()};

			emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT2, cuerpoHtml);
		}

	}

	private void notificarInscripcion(Long codInscripcion, String nombreParametro) throws MessagingException, DataException {
		InscripcionEstudianteDatosEspecializacion inscripcion = inscripcionEspRepository.getInscripcionEstudiante(codInscripcion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		Optional<Parametro> parametro = parametroRepository.findByNombreParametro(nombreParametro);
		if(parametro.isEmpty())
			throw new DataException(NO_PARAMETRO);

		String nombres = inscripcion.getNombre() + " " + inscripcion.getApellido();
		String cuerpoHtml = String.format(parametro.get().getValor(), nombres, inscripcion.getNombreCatalogoCurso(), inscripcion.getFechaInicioCurso(), inscripcion.getFechaFinCurso());

		String[] destinatarios = {inscripcion.getCorreoPersonal()};

		emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT_INSCRIPCION, cuerpoHtml);
	}

	@Override
	@Async
	public void notificarPruebaAprobada(Long codCursoEspecializacion, Long codSubTipoPrueba)
			throws MessagingException, DataException {

		Optional<PruebaDetalleEntity> pruebaDetalleOptional = pruebaDetalleEntityRepository.findByCodCursoEspecializacionAndCodSubtipoPrueba(codCursoEspecializacion, codSubTipoPrueba);
		Optional<CursoDatos> cursoDatos = cursoEntityRepository.getCursoDatos(codCursoEspecializacion);
		if(cursoDatos.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		List<InscritosValidos> listaInscritosValidos = pruebasRepository.get_approved_by_test_esp(codSubTipoPrueba, codCursoEspecializacion);

		for (InscritosValidos inscritosValidos : listaInscritosValidos) {
			Optional<Parametro> parametro = parametroRepository.findByNombreParametro("especializacion.notificacion.resultado.prueba");
			if(parametro.isEmpty())
				throw new DataException(NO_PARAMETRO);

			String nombres = inscritosValidos.getNombre() + " " + inscritosValidos.getApellido();
			String cuerpoHtml = String.format(parametro.get().getValor(), nombres, cursoDatos.get().getNombreCatalogoCurso(), pruebaDetalleOptional.get().getFechaInicio(), pruebaDetalleOptional.get().getFechaFin());

			String[] destinatarios = {inscritosValidos.getCorreoPersonal()};

			emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT_PRUEBAS, cuerpoHtml);
		}
	}

}
