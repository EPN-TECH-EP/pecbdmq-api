package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_ESPECIALIZACION_INSCRIPCION;
import static epntech.cbdmq.pe.constante.EmailConst.*;
import static epntech.cbdmq.pe.constante.EstadosConst.ACTIVO;
import static epntech.cbdmq.pe.constante.MensajesConst.*;
import static epntech.cbdmq.pe.constante.EspecializacionConst.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.admin.especializacion.*;
import epntech.cbdmq.pe.dominio.util.*;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.admin.SubTipoPruebaRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.*;
import epntech.cbdmq.pe.servicio.*;
import epntech.cbdmq.pe.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleEntityRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
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
    @Autowired
    private Utilitarios util;
    @Autowired
    private DatoPersonalService datoPersonalSvc;
    @Autowired
    private UsuarioService usuarioSvc;
    @Autowired
    private ApiCBDMQService apiCiudadanoCBDMQSvc;
    @Autowired
    private ApiCBDMQFuncionariosService apiFuncionarioCBDMQSvc;
    @Autowired
    private UnidadGestionService unidadGestionSvc;
    @Autowired
    private CargoService cargoSvc;
    @Autowired
    private GradoService gradoSvc;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;
    @Value("${spring.servlet.multipart.max-file-size}")
    public DataSize TAMAÑO_MAXIMO;

    @Override
    public InscripcionEsp save(InscripcionEsp inscripcionEsp) {
        Boolean convocatoria = convocatoriaCursoRepository.validaConvocatoriaCursoActiva(inscripcionEsp.getCodCursoEspecializacion());
        if (!convocatoria)
            throw new BusinessException(CONVOCATORIA_NO_ACTIVA);

        Optional<InscripcionEsp> inscripcionEspRepositoryOptional = inscripcionEspRepository.findByCodEstudianteAndCodCursoEspecializacion(inscripcionEsp.getCodEstudiante(), inscripcionEsp.getCodCursoEspecializacion());
        if (inscripcionEspRepositoryOptional.isPresent())
            throw new BusinessException(REGISTRO_YA_EXISTE);

        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(inscripcionEsp.getCodEstudiante().intValue());
        Optional<Curso> cursoOptional = cursoRepository.findById(inscripcionEsp.getCodCursoEspecializacion());

        if (estudianteOptional.isEmpty() || cursoOptional.isEmpty())
            throw new BusinessException(REGISTRO_NO_EXISTE);

        LocalDate fechaActual = LocalDate.now();
        inscripcionEsp.setFechaInscripcion(fechaActual);
        inscripcionEsp.setEstado("INSCRITO");

        return inscripcionEspRepository.save(inscripcionEsp);
    }

    @Override
    public InscripcionEsp update(InscripcionEsp inscripcionEspActualizada) {
        Optional<InscripcionEsp> inscripcionEspOptional = inscripcionEspRepository.findByCodEstudianteAndCodCursoEspecializacion(inscripcionEspActualizada.getCodEstudiante(), inscripcionEspActualizada.getCodCursoEspecializacion());
        if (inscripcionEspOptional.isPresent() && !inscripcionEspOptional.get().getCodInscripcion().equals(inscripcionEspActualizada.getCodInscripcion()))
            throw new BusinessException(REGISTRO_YA_EXISTE);

        return inscripcionEspRepository.save(inscripcionEspActualizada);
    }

	@Override
	public InscripcionEsp updateDelegado(Long codInscripcion, Long codigoUsuario) {
		InscripcionEsp inscripcionEsp = inscripcionEspRepository.findById(codInscripcion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		inscripcionEsp.setCodUsuario(codigoUsuario);
		inscripcionEsp.setEstado(ESTADO_INSCRIPCION_ASIGNADO);

		return inscripcionEspRepository.save(inscripcionEsp);
	}

	@Override
	public Optional<InscripcionDatosEsp> getById(Long codInscripcion) {
		InscripcionEsp inscripcionEsp = inscripcionEspRepository.findById(codInscripcion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		return inscripcionDatosRepository.findByInscripcion(inscripcionEsp.getCodInscripcion());
	}

	@Override
	public List<InscripcionDatosEspecializacion> getAll() {
		return inscripcionEspRepository.getAllInscripciones();
	}

	@Override
	public List<InscripcionDatosEspecializacion> getByUsuarioPaginado(Long codUsuario, Pageable pageable) {
		return inscripcionEspRepository.getAllInscripcionesByUsuario(codUsuario, pageable);
	}

	@Override
	public List<InscripcionDatosEspecializacion> getAllPaginado(Pageable pageable) {
		return inscripcionEspRepository.getAllInscripciones(pageable);
	}

	@Override
	public void delete(Long codInscripcion) {
		InscripcionEsp inscripcionEsp = inscripcionEspRepository.findById(codInscripcion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        inscripcionEspRepository.deleteById(inscripcionEsp.getCodInscripcion());
    }

    @Override
    public List<Documento> uploadFiles(Long codInscripcion, Long tipoDocumento, List<MultipartFile> archivos) throws DataException, IOException, ArchivoMuyGrandeExcepcion {
        Optional<InscripcionEsp> inscripcionEspOptional = inscripcionEspRepository.findById(codInscripcion);
        if (inscripcionEspOptional.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        return guardarDocumentos(archivos, codInscripcion, tipoDocumento);
    }

    public List<Documento> guardarDocumentos(List<MultipartFile> archivos, Long codInscripcion, Long tipoDocumento)
            throws IOException, ArchivoMuyGrandeExcepcion, DataException {
        Optional<InscripcionEsp> inscripcionEspOptional = inscripcionEspRepository.findById(codInscripcion);
        if (inscripcionEspOptional.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        String resultado;
        List<Documento> documentos = new ArrayList<>();

        resultado = ruta(codInscripcion);
        Path ruta = Paths.get(resultado);

        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }

        for (Iterator iterator = archivos.iterator(); iterator.hasNext(); ) {

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
        if (inscripcionEspOptional.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        Optional<Documento> documentoOptional;
        Documento documento = new Documento();

        // System.out.println("id: " + codDocumento);
        documentoOptional = documentoRepository.findById(codDocumento.intValue());
        if (documentoOptional.isEmpty())
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
	public List<ValidaRequisitos> saveValidacionRequisito(List<ValidaRequisitos> validaRequisitos) {
		validarRequisitosCursoEspecializacion(validaRequisitos);
		try {
			return validaRequisitosRepository.saveAll(validaRequisitos);
		} catch (DataIntegrityViolationException dive) {
			throw new BusinessException(REGISTRO_YA_EXISTE);
		}
	}

	private void validarRequisitosCursoEspecializacion(List<ValidaRequisitos> validaRequisitos) {
		if (validaRequisitos.isEmpty()) {
			throw new BusinessException(REQUISITOS_OBLIGATORIO);
		}

		InscripcionEsp inscripcionEsp = inscripcionEspRepository.findById(validaRequisitos.get(0).getCodInscripcion())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		List<CursoRequisito> cursoRequisitos = cursoRequisitoRepository.findByCodCursoEspecializacion(inscripcionEsp.getCodCursoEspecializacion());

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
	public List<ValidacionRequisitosDatos> getRequisitos(Long codInscripcion) {
		InscripcionEsp inscripcionEsp = inscripcionEspRepository.findById(codInscripcion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		return validaRequisitosRepository.findRequisitosPorInscripcion(inscripcionEsp.getCodInscripcion());
	}

	@Override
	@Async
	public void updateValidacionRequisito(List<ValidaRequisitos> validaRequisitos) {
		validaRequisitosRepository.saveAll(validaRequisitos);
		String validaRequisitoEstudiante = validaRequisitosRepository.cumpleRequisitosCurso(validaRequisitos.get(0).getCodInscripcion());
		if (validaRequisitoEstudiante.equals(ESTADO_INSCRIPCION_VALIDO)) {
			notificarInscripcion(validaRequisitos.get(0).getCodInscripcion(), "especializacion.notificacion.inscripcion.valida");
		} else if(validaRequisitoEstudiante.equals(ESTADO_INSCRIPCION_INVALIDO)) {
			notificarInscripcion(validaRequisitos.get(0).getCodInscripcion(), "especializacion.notificacion.inscripcion.novalida");
		}
	}

	private void notificarInscripcion(Long codInscripcion, String nombreParametro) {
		InscripcionEstudianteDatosEspecializacion inscripcion = inscripcionEspRepository.getInscripcionEstudiante(codInscripcion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		Parametro parametro = parametroRepository.findByNombreParametro(nombreParametro)
				.orElseThrow(() -> new BusinessException(NO_PARAMETRO));

		String nombres = inscripcion.getNombre() + " " + inscripcion.getApellido();
		String cuerpoHtml = String.format(parametro.getValor(), nombres, inscripcion.getNombreCatalogoCurso(), inscripcion.getFechaInicioCurso(), inscripcion.getFechaFinCurso());

		String[] destinatarios = {inscripcion.getCorreoPersonal()};

		emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT_INSCRIPCION, cuerpoHtml);
	}

    @Override
    public List<InscritosEspecializacion> getInscritosValidosCurso(Long codCursoEspecializacion) {
        return inscripcionEspRepository.getInscripcionesValidasByCurso(codCursoEspecializacion);
    }

	@Override
    @Async
	public void notificarPrueba(Long codCursoEspecializacion, Long codSubTipoPrueba) {
		Curso curso = cursoRepository.findById(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		SubTipoPrueba subTipoPrueba = subTipoPruebaRepository.findById(codSubTipoPrueba.intValue())
				.orElseThrow(() -> new BusinessException(NO_SUBTIPO_PRUEBA));

		PruebaDetalle pruebaDetalle = pruebaDetalleRepository
				.findByCodCursoEspecializacionAndCodSubtipoPrueba(curso.getCodCursoEspecializacion().intValue(), subTipoPrueba.getCodSubtipoPrueba())
				.orElseThrow(() -> new BusinessException(CURSO_NO_PRUEBAS));

		List<InscritosEspecializacion> listaInscritos;
		listaInscritos = inscripcionEspRepository.getInscripcionesValidasByCurso(codCursoEspecializacion);

		for (InscritosEspecializacion inscritosEspecializacion : listaInscritos) {

			Parametro parametro = parametroRepository.findByNombreParametro("especializacion.notificacion.pruebas")
					.orElseThrow(() -> new BusinessException(NO_PARAMETRO));

			String nombres = inscritosEspecializacion.getNombre() + " " + inscritosEspecializacion.getApellido();
			String cuerpoHtml = String.format(parametro.getValor(), nombres, inscritosEspecializacion.getNombreCatalogoCurso(), pruebaDetalle.getDescripcionPrueba(),
					subTipoPrueba.getNombre(), pruebaDetalle.getFechaInicio(), pruebaDetalle.getFechaFin(), pruebaDetalle.getHora());

			String[] destinatarios = {inscritosEspecializacion.getCorreoPersonal()};

			emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT2, cuerpoHtml);
		}

	}

	@Override
	@Async
	public void notificarPruebaAprobada(Long codCursoEspecializacion, Long codSubTipoPrueba) {
		PruebaDetalleEntity pruebaDetalle = pruebaDetalleEntityRepository
				.findByCodCursoEspecializacionAndCodSubtipoPrueba(codCursoEspecializacion, codSubTipoPrueba)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		CursoDatos cursoDatos = cursoEntityRepository.getCursoDatos(codCursoEspecializacion)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		List<ResultadosPruebasDatos> listaInscritosValidos = pruebasRepository.get_approved_by_test_esp(codSubTipoPrueba, codCursoEspecializacion);

		for (ResultadosPruebasDatos inscritosValidos : listaInscritosValidos) {
			Parametro parametro = parametroRepository.findByNombreParametro("especializacion.notificacion.resultado.prueba")
					.orElseThrow(() -> new BusinessException(NO_PARAMETRO));

			String nombres = inscritosValidos.getNombre() + " " + inscritosValidos.getApellido();
			String cuerpoHtml = String.format(parametro.getValor(), nombres, cursoDatos.getNombreCatalogoCurso(), pruebaDetalle.getFechaInicio(), pruebaDetalle.getFechaFin());

			String[] destinatarios = {inscritosValidos.getCorreoPersonal()};

			emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT_PRUEBAS, cuerpoHtml);
		}
	}

    @Override
    @Transactional
       /* public DatoPersonalEstudianteDto confirmacionInscripcion(String cedula) throws Exception {

        DatoPersonalEstudianteDto datoPersonalEstudianteDto = new DatoPersonalEstudianteDto();
        Boolean isValid = util.validadorDeCedula(cedula);
        if (isValid) {
            try {
                Optional<DatoPersonal> datoPersonalObj = datoPersonalSvc.getByCedula(cedula);
                if (datoPersonalObj.isEmpty()) {
                    //TODO APIS FUNCIONARIOS Y CIUDADANOS
                    Optional<FuncionarioApiDto> funcionarioSinRegistrar = apiFuncionarioCBDMQSvc.servicioFuncionarios(cedula);
                    if (funcionarioSinRegistrar.isPresent()) {
                        DatoPersonal newDatoPersonal = new DatoPersonal();
                        newDatoPersonal.setApellido(funcionarioSinRegistrar.get().getApellidos());
                        newDatoPersonal.setCedula(funcionarioSinRegistrar.get().getCedula());
                        newDatoPersonal.setCorreoPersonal(funcionarioSinRegistrar.get().getCorreoPersonal());
                        newDatoPersonal.setEstado(ACTIVO);
                        newDatoPersonal.setNombre(funcionarioSinRegistrar.get().getNombres());
                        newDatoPersonal.setNumTelefConvencional(funcionarioSinRegistrar.get().getTelefonoConvencional());
                        newDatoPersonal.setTipoSangre(funcionarioSinRegistrar.get().getTipoSangre());
                        newDatoPersonal.setCodProvinciaNacimiento(Integer.valueOf(funcionarioSinRegistrar.get().getCodigoProvinciaNacimiento()));
                        newDatoPersonal.setCodUnidadGestion(unidadGestionSvc.getUnidadGestionByNombre(funcionarioSinRegistrar.get().getCodigoUnidadGestion()).get().getCodigo());
                        newDatoPersonal.setSexo(funcionarioSinRegistrar.get().getSexo().toUpperCase());
                        newDatoPersonal.setNumTelefCelular(funcionarioSinRegistrar.get().getTelefonoCelular());
                        newDatoPersonal.setResidePais(funcionarioSinRegistrar.get().getPaisResidencia().toUpperCase().equals("ECUADOR") ? true : false);
                        newDatoPersonal.setCodProvinciaResidencia(Long.valueOf(funcionarioSinRegistrar.get().getCodigoProvinciaResidencia()));
                        newDatoPersonal.setCallePrincipalResidencia(funcionarioSinRegistrar.get().getCallePrincipalResidencia());
                        newDatoPersonal.setCalleSecundariaResidencia(funcionarioSinRegistrar.get().getCalleSecundariaResidencia());
                        newDatoPersonal.setNumeroCasa(funcionarioSinRegistrar.get().getNumeroCasaResidencia());
                        newDatoPersonal.setColegio(funcionarioSinRegistrar.get().getInstitucionSegundoNivel());
                        newDatoPersonal = datoPersonalSvc.saveDatosPersonales(newDatoPersonal);
                        Usuario newUser = new Usuario();
                        newUser.setCodDatosPersonales(newDatoPersonal);
                        usuarioSvc.registrar(newUser);
                        Estudiante newEstudiante = new Estudiante();
                        newEstudiante.setCodDatosPersonales(newDatoPersonal.getCodDatosPersonales());
                        newEstudiante.setEstado(ACTIVO);
                        newEstudiante = estudianteRepository.save(newEstudiante);
                        datoPersonalEstudianteDto.setEstudiante(newEstudiante);
                        datoPersonalEstudianteDto.setDatoPersonal(newDatoPersonal);
                        return datoPersonalEstudianteDto;

                    } else {
                        Optional<?> ciudadanoSinRegistrar = apiCiudadanoCBDMQSvc.servicioCiudadanos(cedula);
                        if (ciudadanoSinRegistrar.isEmpty()) {
                            throw new DataException(REGISTRO_NO_EXISTE);
                        }
                        DatoPersonal newDatoPersonal = new DatoPersonal();
                        //newDatoPersonal.setApellido(((CiudadanoApiDto) ciudadanoSinRegistrar.get()).getApellidos());
                        newDatoPersonal = (DatoPersonal) ciudadanoSinRegistrar.get();
                        newDatoPersonal = datoPersonalSvc.saveDatosPersonales(newDatoPersonal);
                        Usuario newUser = new Usuario();
                        newUser.setCodDatosPersonales(newDatoPersonal);
                        usuarioSvc.registrar(newUser);
                        Estudiante newEstudiante = new Estudiante();
                        newEstudiante.setCodDatosPersonales(newDatoPersonal.getCodDatosPersonales());
                        newEstudiante.setEstado(ACTIVO);
                        newEstudiante = estudianteRepository.save(newEstudiante);
                        datoPersonalEstudianteDto.setEstudiante(newEstudiante);
                        datoPersonalEstudianteDto.setDatoPersonal(newDatoPersonal);
                        return datoPersonalEstudianteDto;
                    }
                } else {
                    Optional<Usuario> usuarioObj = usuarioSvc.getUsuarioByCodDatoPersonal(datoPersonalObj.get().getCodDatosPersonales());
                    if (usuarioObj.isEmpty()) {
                        //TODO CREATE USER AND STUDENT

                        Usuario newUser = new Usuario();
                        newUser.setCodDatosPersonales(datoPersonalObj.get());
                        usuarioSvc.registrar(newUser);
                        Estudiante newEstudiante = new Estudiante();
                        newEstudiante.setCodDatosPersonales(datoPersonalObj.get().getCodDatosPersonales());
                        newEstudiante.setEstado(ACTIVO);
                        newEstudiante = estudianteRepository.save(newEstudiante);
                        datoPersonalEstudianteDto.setEstudiante(newEstudiante);
                        datoPersonalEstudianteDto.setDatoPersonal(datoPersonalObj.get());
                        return datoPersonalEstudianteDto;
                    } else {
                        Estudiante estudianteObj = estudianteRepository.getEstudianteByUsuario(usuarioObj.get().getNombreUsuario());
                        if (estudianteObj == null) {
                            Estudiante newEstudiante = new Estudiante();
                            newEstudiante.setCodDatosPersonales(datoPersonalObj.get().getCodDatosPersonales());
                            newEstudiante.setEstado(ACTIVO);
                            newEstudiante = estudianteRepository.save(newEstudiante);
                            datoPersonalEstudianteDto.setEstudiante(newEstudiante);
                            datoPersonalEstudianteDto.setDatoPersonal(datoPersonalObj.get());
                            return datoPersonalEstudianteDto;

                        } else {
                            datoPersonalEstudianteDto.setEstudiante(estudianteObj);
                            datoPersonalEstudianteDto.setDatoPersonal(datoPersonalObj.get());
                            return datoPersonalEstudianteDto;
                        }
                    }
                }

            } catch (Exception ex) {
                throw ex;
            }
        }
        return datoPersonalEstudianteDto;

    }*/
    public DatoPersonalEstudianteDto confirmacionInscripcion(String cedula) throws Exception {
        DatoPersonalEstudianteDto datoPersonalEstudianteDto = new DatoPersonalEstudianteDto();
        Boolean isValid = util.validadorDeCedula(cedula);

        if (!isValid) {
            // Lanzar una excepción para indicar que la cédula no es válida
            throw new DataException("Cédula no válida");
        }

        try {
            Optional<DatoPersonal> datoPersonalObj = datoPersonalSvc.getByCedula(cedula);

            if (datoPersonalObj.isEmpty()) {
                //TODO APIS FUNCIONARIOS Y CIUDADANOS
                Optional<FuncionarioApiDto> funcionarioSinRegistrar = apiFuncionarioCBDMQSvc.servicioFuncionarios(cedula);

                if (funcionarioSinRegistrar.isPresent()) {
                    DatoPersonal newDatoPersonal = createDatoPersonalFromFuncionario(funcionarioSinRegistrar.get());
                    newDatoPersonal.setCedula(cedula);
                    Usuario newUser = new Usuario();
                    newUser.setCodDatosPersonales(newDatoPersonal);
                    newUser.setNombreUsuario(newDatoPersonal.getCedula());
                    newUser=usuarioSvc.registrar(newUser);

                    Estudiante newEstudiante = new Estudiante();
                    newEstudiante.setCodDatosPersonales(newUser.getCodDatosPersonales().getCodDatosPersonales());
                    newEstudiante.setEstado(ACTIVO);
                    newEstudiante = estudianteRepository.save(newEstudiante);

                    datoPersonalEstudianteDto.setEstudiante(newEstudiante);
                    datoPersonalEstudianteDto.setDatoPersonal(newUser.getCodDatosPersonales());

                } else {
                    List<CiudadanoApiDto> ciudadanoSinRegistrar = apiCiudadanoCBDMQSvc.servicioCiudadanos(cedula);
                    CiudadanoApiDto ciudadanoApiDto = ciudadanoSinRegistrar.get(0);

                    if (ciudadanoSinRegistrar.isEmpty()) {
                        throw new DataException(REGISTRO_NO_EXISTE);
                    }

                    //TODO se asimila que solo debe haber uno por que la cedula solo devuelve uno
                   System.out.println("ciudadanoApiDto: "+ciudadanoApiDto.getCedula());
                     DatoPersonal newDatoPersonal = createDatoPersonalFromCiudadno(ciudadanoApiDto);
                    datoPersonalEstudianteDto.setEstudiante(null);
                    datoPersonalEstudianteDto.setDatoPersonal(newDatoPersonal);




                }
            } else {
                Optional<Usuario> usuarioObj = usuarioSvc.getUsuarioByCodDatoPersonal(datoPersonalObj.get().getCodDatosPersonales());

                if (usuarioObj.isEmpty()) {
                    Usuario newUser = new Usuario();
                    newUser.setNombreUsuario(datoPersonalObj.get().getCedula());
                    newUser.setCodDatosPersonales(datoPersonalObj.get());
                    usuarioSvc.crear(newUser);

                    Estudiante newEstudiante = new Estudiante();
                    newEstudiante.setCodDatosPersonales(datoPersonalObj.get().getCodDatosPersonales());
                    newEstudiante.setEstado(ACTIVO);
                    newEstudiante = estudianteRepository.save(newEstudiante);

                    datoPersonalEstudianteDto.setEstudiante(newEstudiante);
                    datoPersonalEstudianteDto.setDatoPersonal(datoPersonalObj.get());
                } else {
                    Estudiante estudianteObj = estudianteRepository.getEstudianteByUsuario(usuarioObj.get().getNombreUsuario());

                    if (estudianteObj == null) {
                        Estudiante newEstudiante = new Estudiante();
                        newEstudiante.setCodDatosPersonales(datoPersonalObj.get().getCodDatosPersonales());
                        newEstudiante.setEstado(ACTIVO);
                        newEstudiante = estudianteRepository.save(newEstudiante);

                        datoPersonalEstudianteDto.setEstudiante(newEstudiante);
                        datoPersonalEstudianteDto.setDatoPersonal(datoPersonalObj.get());
                    } else {
                        datoPersonalEstudianteDto.setEstudiante(estudianteObj);
                        datoPersonalEstudianteDto.setDatoPersonal(datoPersonalObj.get());
                    }
                }
            }
        } catch (Exception ex) {
            // No es necesario lanzar nuevamente la misma excepción. new DataEception(ex.getMessage());
            throw ex;
        }

        return datoPersonalEstudianteDto;
    }

    @Override
    public DatoPersonalEstudianteDto colocarCorreoCiudadano(DatoPersonal datoPersonal) throws Exception {
        DatoPersonalEstudianteDto datoPersonalEstudianteDto = new DatoPersonalEstudianteDto();

        Usuario newUser = new Usuario();
        newUser.setCodDatosPersonales(datoPersonal);
        newUser.setNombreUsuario(datoPersonal.getCedula());
        newUser=usuarioSvc.registrar(newUser);

        Estudiante newEstudiante = new Estudiante();
        newEstudiante.setCodDatosPersonales(newUser.getCodDatosPersonales().getCodDatosPersonales());
        newEstudiante.setEstado(ACTIVO);
        newEstudiante = estudianteRepository.save(newEstudiante);

        datoPersonalEstudianteDto.setEstudiante(newEstudiante);
        datoPersonalEstudianteDto.setDatoPersonal(newUser.getCodDatosPersonales());

        return datoPersonalEstudianteDto;
    }

    private DatoPersonal createDatoPersonalFromFuncionario(FuncionarioApiDto funcionario) {
        DatoPersonal newDatoPersonal = new DatoPersonal();
        newDatoPersonal.setApellido(funcionario.getApellidos());
        //TODO no esta tomando cedula
        newDatoPersonal.setCedula(funcionario.getCedula());
        newDatoPersonal.setCorreoPersonal(funcionario.getCorreoPersonal());
        newDatoPersonal.setEstado(ACTIVO);
        newDatoPersonal.setNombre(funcionario.getNombres());
        newDatoPersonal.setNumTelefConvencional(funcionario.getTelefonoConvencional());
        newDatoPersonal.setTipoSangre(funcionario.getTipoSangre());
        Optional<UnidadGestion> unidadGestion= unidadGestionSvc.getUnidadGestionByNombre(funcionario.getCodigoUnidadGestion());
        newDatoPersonal.setCodUnidadGestion(unidadGestion.isEmpty() ? null : unidadGestion.get().getCodigo());
        newDatoPersonal.setSexo(funcionario.getSexo().toUpperCase());
        newDatoPersonal.setNumTelefCelular(funcionario.getTelefonoCelular());
        newDatoPersonal.setResidePais(funcionario.getPaisResidencia().toUpperCase().equals("ECUADOR"));
        newDatoPersonal.setCodProvinciaResidencia(Long.valueOf(funcionario.getCodigoProvinciaResidencia()));
        newDatoPersonal.setCallePrincipalResidencia(funcionario.getCallePrincipalResidencia());
        newDatoPersonal.setCalleSecundariaResidencia(funcionario.getCalleSecundariaResidencia());
        newDatoPersonal.setNumeroCasa(funcionario.getNumeroCasaResidencia());
        newDatoPersonal.setColegio(funcionario.getInstitucionSegundoNivel());
        newDatoPersonal.setTieneMeritoDeportivo(funcionario.getTieneMeritoDeportivo());
        newDatoPersonal.setTieneMeritoAcademico(funcionario.getTieneMeritoAcademico());
        newDatoPersonal.setNombreTituloSegundoNivel(funcionario.getTituloSegundoNivel());
        newDatoPersonal.setMeritoAcademicoDescripcion(funcionario.getDescripcionMeritoAcademico());
        newDatoPersonal.setMeritoDeportivoDescripcion(funcionario.getDescripcionMeridoDeportivo());
        newDatoPersonal.setCorreoInstitucional(funcionario.getCorreoInstitucional());
        Cargo cargo=cargoSvc.findByNombre(funcionario.getCargo());
        Grado grado=gradoSvc.findByNombre(funcionario.getGrado());
        newDatoPersonal.setCodCargo(cargo==null?null:Long.valueOf(cargo.getCodCargo()));
        newDatoPersonal.setCodGrado(grado==null?null:Long.valueOf(grado.getCodGrado()));
        newDatoPersonal.setCodCantonNacimiento(Long.valueOf(funcionario.getCodigoCantonNacimiento()));
        newDatoPersonal.setCodCantonResidencia(Long.valueOf(funcionario.getCodigoCantonResidencia()));
        newDatoPersonal.setNombreTituloTercerNivel(funcionario.getTituloTercerNivel());
        newDatoPersonal.setNombreTituloCuartoNivel(funcionario.getTituloCuartoNivel());
        newDatoPersonal.setPaisTituloTercerNivel(funcionario.getPaisTercerNivel());
        newDatoPersonal.setPaisTituloCuartoNivel(funcionario.getPaisCuartoNivel());


        return newDatoPersonal;
    }
    private DatoPersonal createDatoPersonalFromCiudadno(CiudadanoApiDto ciudadano) {
        DatoPersonal newDatoPersonal = new DatoPersonal();
        String[] nombreApellidos = dividirNombre(ciudadano.getNombre());

        newDatoPersonal.setApellido(nombreApellidos[0]);
        newDatoPersonal.setNombre(nombreApellidos[1]);
        newDatoPersonal.setCedula(ciudadano.getCedula());
        newDatoPersonal.setEstado(ACTIVO);
        //TODO mal parseado
        // newDatoPersonal.setFechaNacimiento(LocalDateTime.parse(ciudadano.getFechaNacimiento()));
        return newDatoPersonal;
    }
    public static String[] dividirNombre(String nombreCompleto) {
        String[] palabras = nombreCompleto.split(" ");
        int indiceSeparacion = 2; // Segunda palabra desde la izquierda

        // Unir las palabras de los apellidos
        StringBuilder apellidosBuilder = new StringBuilder();
        for (int i = 0; i < indiceSeparacion; i++) {
            apellidosBuilder.append(palabras[i]);
            if (i < indiceSeparacion - 1) {
                apellidosBuilder.append(" ");
            }
        }

        // Unir las palabras de los nombres
        StringBuilder nombresBuilder = new StringBuilder();
        for (int i = indiceSeparacion; i < palabras.length; i++) {
            nombresBuilder.append(palabras[i]);
            if (i < palabras.length - 1) {
                nombresBuilder.append(" ");
            }
        }

        String[] nombreApellidos = new String[2];
        nombreApellidos[0] = apellidosBuilder.toString();
        nombreApellidos[1] = nombresBuilder.toString();
        return nombreApellidos;
    }


}
