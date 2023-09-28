package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.admin.especializacion.*;
import epntech.cbdmq.pe.dominio.admin.llamamiento.RequisitosVerificados;
import epntech.cbdmq.pe.dominio.util.*;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.admin.*;
import epntech.cbdmq.pe.repositorio.admin.especializacion.*;
import epntech.cbdmq.pe.servicio.*;
import epntech.cbdmq.pe.servicio.especializacion.CursoDocumentoService;
import epntech.cbdmq.pe.util.Utilitarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import epntech.cbdmq.pe.servicio.especializacion.InscripcionEspService;
import jakarta.mail.MessagingException;

@Service
public class InscripcionEspServiceImpl implements InscripcionEspService {

    @Autowired
    private InscripcionEspRepository inscripcionEspRepository;
    @Autowired
    private EstudianteService estudianteService;
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
    @Autowired
    private CursoDocumentoService cursoDocumentoSvc;

    // postulante repo
    @Autowired
    private PostulanteRepository postulanteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

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

        Optional<Estudiante> estudianteOptional = estudianteService.getById(inscripcionEsp.getCodEstudiante().intValue());
        Optional<Curso> cursoOptional = cursoRepository.findById(inscripcionEsp.getCodCursoEspecializacion());

        if (estudianteOptional.isEmpty() || cursoOptional.isEmpty())
            throw new BusinessException(REGISTRO_NO_EXISTE);

        LocalDate fechaActual = LocalDate.now();
        inscripcionEsp.setFechaInscripcion(fechaActual);
        inscripcionEsp.setEstado("PENDIENTE");

        InscripcionEsp nuevaInscripcion = inscripcionEspRepository.save(inscripcionEsp);

        // inserta los requisitos por postulante para la validación
        // cbdmq.insert_requisitos_curso(p_cod_inscripcion integer, p_cod_curso integer)
        StoredProcedureQuery sql = entityManager.createStoredProcedureQuery("cbdmq.insert_requisitos_curso");
        sql.registerStoredProcedureParameter("p_cod_inscripcion", Integer.class, ParameterMode.IN);
        sql.registerStoredProcedureParameter("p_cod_curso", Integer.class, ParameterMode.IN);
        sql.setParameter("p_cod_inscripcion", nuevaInscripcion.getCodInscripcion().intValue());
        sql.setParameter("p_cod_curso", nuevaInscripcion.getCodCursoEspecializacion().intValue());
        sql.execute();
        sql.getSingleResult();


        return nuevaInscripcion;

    }

    @Override
    public InscripcionEsp saveFully(String datos, List<MultipartFile> archivos) throws DataException, ArchivoMuyGrandeExcepcion, IOException {
        if (archivos == null || archivos.isEmpty()) {
            throw new BusinessException("No se ha adjuntado ningún documento");
        }


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        JsonNode jsonNode = objectMapper.readTree(datos);
        System.out.println("jsonNode: " + jsonNode);

        InscripcionEsp inscripcionEsp = objectMapper.readValue(datos, InscripcionEsp.class);

        InscripcionEsp inscripcionEsp1 = this.save(inscripcionEsp);
        this.guardarDocumentos(archivos, inscripcionEsp1.getCodInscripcion());

        return inscripcionEsp1;
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
    public List<InscripcionDatosEspecializacion> getByCursoAndUsuarioPaginado(Long codCurso, Long codUsuario, Pageable pageable) {
        return inscripcionEspRepository.getAllInscripcionesByCursoAndUsuario(codCurso, codUsuario, pageable);
    }

    @Override
    public List<InscripcionDatosEspecializacion> getAllByCursoPaginado(Long codCurso, Pageable pageable) {
        return inscripcionEspRepository.getAllInscripcionesByCurso(codCurso, pageable);
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

        return guardarDocumentos(archivos, codInscripcion);
    }

    public List<Documento> guardarDocumentos(List<MultipartFile> archivos, Long codInscripcion)
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

        emailService.sendMensajeHtmlGenerico(destinatarios, EMAIL_SUBJECT_INSCRIPCION, cuerpoHtml);
    }

    @Override
    public Boolean cumplePorcentajeMinimoInscritosCurso(long codCurso) {
        return inscripcionEspRepository.cumplePorcentajeMinimoInscritosCurso(codCurso);
    }

    @Override
    public Set<InscripcionDatosEspecializacion> getByCursoEstado(Long codCurso, String Estado) throws DataException {
        return inscripcionEspRepository.getInscripcionesByCursoEstado(codCurso, Estado);
    }

    @Override
    public List<DatosInscripcionEsp> getAprobadosPruebas(Integer codCurso) throws DataException {
        return inscripcionEspRepository.getAprobadosPruebas(codCurso);
    }

    @Override
    public List<DatosInscripcionEsp> getDesAprobadosPruebas(Integer codCurso) throws DataException {
        return inscripcionEspRepository.getDesAprobadosPruebas(codCurso);
    }

    @Override
    public List<DatosInscripcionEsp> getAprobadosPruebasSubtipoPrueba(Integer codCurso, Integer codSubtipoPrueba) {
        return inscripcionEspRepository.getAprobadosPruebasBySubtipoPrueba(codCurso, codSubtipoPrueba);
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
        } else if (validaRequisitoEstudiante.equals(ESTADO_INSCRIPCION_INVALIDO)) {
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

        emailService.sendMensajeHtmlGenerico(destinatarios, EMAIL_SUBJECT_INSCRIPCION, cuerpoHtml);
    }

    @Override
    public List<InscritosEspecializacion> getInscritosValidosCurso(Long codCursoEspecializacion) {
        return inscripcionEspRepository.getInscripcionesValidasByCurso(codCursoEspecializacion);
    }

    // monitoreo inscripciones curso
    @Override
    public List<InscritosEspecializacion> getInscritosTodoCurso(Long codCursoEspecializacion) {
        return inscripcionEspRepository.getInscripcionesValidasByCurso(codCursoEspecializacion);
    }

    @Override
    public List<InscripcionEsp> getAll2() {
        return inscripcionEspRepository.findAll();
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

            emailService.sendMensajeHtmlGenerico(destinatarios, EMAIL_SUBJECT2, cuerpoHtml);
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

            emailService.sendMensajeHtmlGenerico(destinatarios, EMAIL_SUBJECT_PRUEBAS, cuerpoHtml);
        }
    }

    @Override
    @Transactional
    public DatoPersonalEstudianteDto confirmacionInscripcion(String cedula, Long codCurso) throws Exception {
        DatoPersonalEstudianteDto datoPersonalEstudianteDto = new DatoPersonalEstudianteDto();
        Boolean isValid = util.validadorDeCedula(cedula);

        if (!isValid) {
            // Lanzar una excepción para indicar que la cédula no es válida
            throw new DataException("Cédula no válida");
        }

        try {
            Optional<DatoPersonal> datoPersonalObj = datoPersonalSvc.getByCedula(cedula);
            //SI ES QUE NO EXISTE UN DATO PERSONAL
            if (datoPersonalObj.isEmpty()) {
                Optional<FuncionarioApiDto> funcionarioSinRegistrar = apiFuncionarioCBDMQSvc.servicioFuncionarios(cedula);
                //API FUNCIONARIO
                if (funcionarioSinRegistrar.isPresent()) {
                    DatoPersonal newDatoPersonal = createDatoPersonalFromFuncionario(funcionarioSinRegistrar.get());
                    datoPersonalEstudianteDto.setEstudiante(null);
                    datoPersonalEstudianteDto.setDatoPersonal(newDatoPersonal);

                }
                //API CIUDADANO
                else {
                    List<CiudadanoApiDto> ciudadanoSinRegistrar = apiCiudadanoCBDMQSvc.servicioCiudadanos(cedula);
                    CiudadanoApiDto ciudadanoApiDto = ciudadanoSinRegistrar.get(0);

                    if (ciudadanoSinRegistrar.isEmpty()) {
                        throw new DataException(REGISTRO_NO_EXISTE);
                    }
                    //TODO se asume que solo debe haber uno por que la cedula solo devuelve uno
                    DatoPersonal newDatoPersonal = createDatoPersonalFromCiudadno(ciudadanoApiDto);
                    datoPersonalEstudianteDto.setEstudiante(null);
                    datoPersonalEstudianteDto.setDatoPersonal(newDatoPersonal);
                    datoPersonalEstudianteDto.setEsCiudadano(true);
                }
            }
            //SI ES QUE EXISTE UN DATO PERSONAL
            else {
                Optional<Usuario> usuarioObj = usuarioSvc.getUsuarioByCodDatoPersonal(datoPersonalObj.get().getCodDatosPersonales());
                Optional<FuncionarioApiDto> funcionario = apiFuncionarioCBDMQSvc.servicioFuncionarios(cedula);
                Boolean esFuncionario = funcionario.isPresent() ? true : false;
                //NO EXISTE USUARIO
                if (usuarioObj.isEmpty()) {
                    Usuario newUser = new Usuario();
                    newUser.setNombreUsuario(datoPersonalObj.get().getCedula());
                    newUser.setCodDatosPersonales(datoPersonalObj.get());
                    newUser.setClave(this.encodePassword(cedula));
                    usuarioSvc.crear(newUser);

                    Estudiante newEstudiante = new Estudiante();
                    newEstudiante.setCodDatosPersonales(datoPersonalObj.get().getCodDatosPersonales());
                    newEstudiante.setEstado(ACTIVO);
                    newEstudiante.setCodUnicoEstudiante(this.postulanteRepository.getIdPostulante("E"));
                    newEstudiante = estudianteService.save(newEstudiante);

                    datoPersonalEstudianteDto.setEstudiante(newEstudiante);
                    datoPersonalEstudianteDto.setDatoPersonal(datoPersonalObj.get());
                    datoPersonalEstudianteDto.setEsFuncionario(esFuncionario);
                }
                //EXISTE USUARIO
                else {
                    Estudiante estudianteObj = estudianteService.getEstudianteEspecializacionByUser(usuarioObj.get().getNombreUsuario());
                    //NO ES ESTUDIANTE
                    if (estudianteObj == null) {
                        Estudiante newEstudiante = new Estudiante();
                        newEstudiante.setCodDatosPersonales(datoPersonalObj.get().getCodDatosPersonales());
                        newEstudiante.setEstado(ACTIVO);
                        newEstudiante.setCodUnicoEstudiante(this.postulanteRepository.getIdPostulante("E"));
                        newEstudiante = estudianteService.save(newEstudiante);

                        datoPersonalEstudianteDto.setEstudiante(newEstudiante);
                        datoPersonalEstudianteDto.setDatoPersonal(datoPersonalObj.get());
                        datoPersonalEstudianteDto.setEsFuncionario(esFuncionario);

                    } else {

                        // verifica si el estudiante ya esta inscrito en el curso
                        Optional<InscripcionEsp> inscripcionEspRepositoryOptional = inscripcionEspRepository.findByCodEstudianteAndCodCursoEspecializacion(estudianteObj.getCodEstudiante().longValue(), codCurso);
                        if (inscripcionEspRepositoryOptional.isPresent())
                            throw new BusinessException(REGISTRO_YA_EXISTE);

                        datoPersonalEstudianteDto.setEstudiante(estudianteObj);
                        datoPersonalEstudianteDto.setDatoPersonal(datoPersonalObj.get());
                        datoPersonalEstudianteDto.setEsFuncionario(esFuncionario);
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
    public DatoPersonalEstudianteDto colocarMasInformacion(DatoPersonal datoPersonal) throws Exception {
        Optional<DatoPersonal> datoPersonalObj = datoPersonalSvc.getByCedula(datoPersonal.getCedula());
        DatoPersonalEstudianteDto datoPersonalEstudianteDto = new DatoPersonalEstudianteDto();
        //SI ES QUE NO EXISTE UN DATO PERSONAL
        if (datoPersonalObj.isEmpty()) {
            Usuario newUser = new Usuario();
            newUser.setCodDatosPersonales(datoPersonal);
            newUser.setNombreUsuario(datoPersonal.getCedula());
            newUser.setClave(this.encodePassword(datoPersonal.getCedula()));
            newUser = usuarioSvc.crear(newUser);

            Estudiante newEstudiante = new Estudiante();
            newEstudiante.setCodDatosPersonales(newUser.getCodDatosPersonales().getCodDatosPersonales());
            newEstudiante.setEstado(ACTIVO);
            newEstudiante.setCodUnicoEstudiante(this.postulanteRepository.getIdPostulante("E"));
            newEstudiante = estudianteService.save(newEstudiante);

            datoPersonalEstudianteDto.setEstudiante(newEstudiante);
            datoPersonalEstudianteDto.setDatoPersonal(newUser.getCodDatosPersonales());
        } else {
            DatoPersonal datoPersonal1 = datoPersonalSvc.updateDatosPersonales(datoPersonal);
            Usuario usuario = usuarioSvc.getUsuarioByCodDatoPersonal(datoPersonal1.getCodDatosPersonales()).get();
            Estudiante estudiante = estudianteService.getEstudianteEspecializacionByUser(usuario.getNombreUsuario().toString());
            datoPersonalEstudianteDto.setEstudiante(estudiante);
            datoPersonalEstudianteDto.setDatoPersonal(datoPersonal1);
        }


        return datoPersonalEstudianteDto;
    }

    @Override
    public List<RequisitosVerificados> findRequisitosForEspByDp(Integer codDp) {
        return validaRequisitosRepository.findRequisitosForEspByDp(codDp);
    }

    @Override
    public List<InscripcionEsp> findByCodCursoEspecializacionAndEstado(Long codCursoEspecializacion, String estado) {
        return inscripcionEspRepository.findByCodCursoEspecializacionAndEstado(codCursoEspecializacion, estado);
    }


    private DatoPersonal createDatoPersonalFromFuncionario(FuncionarioApiDto funcionario) {
        DatoPersonal newDatoPersonal = new DatoPersonal();
        newDatoPersonal.setApellido(funcionario.getApellidos());
        //TODO no esta tomando cedula
        newDatoPersonal.setCedula(funcionario.getCedula());

        String correo = funcionario.getCorreoInstitucional();

        newDatoPersonal.setCorreoPersonal(correo);
        newDatoPersonal.setEstado(ACTIVO);
        newDatoPersonal.setNombre(funcionario.getNombres());
        newDatoPersonal.setNumTelefConvencional(funcionario.getTelefonoConvencional());
        newDatoPersonal.setTipoSangre(funcionario.getTipoSangre());
        Optional<UnidadGestion> unidadGestion = unidadGestionSvc.getUnidadGestionByNombre(funcionario.getCodigoUnidadGestion());
        newDatoPersonal.setCodUnidadGestion(unidadGestion.isEmpty() ? null : unidadGestion.get().getCodigo());
        newDatoPersonal.setSexo(funcionario.getSexo().toUpperCase());

        newDatoPersonal.setNumTelefCelular(funcionario.getTelefonoCelular());

        // transforma fecha de api "fechaNacimiento":"1993-09-27" a LocalDateTime con time 00:00:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(funcionario.getFechaNacimiento(), formatter);

        newDatoPersonal.setFechaNacimiento(fechaNacimiento.atStartOfDay());

        /*newDatoPersonal.setResidePais(funcionario.getPaisResidencia().toUpperCase().equals("ECUADOR"));
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
        newDatoPersonal.setNombreTituloTercerNivel(funcionario.getTituloTercerNivel());
        newDatoPersonal.setNombreTituloCuartoNivel(funcionario.getTituloCuartoNivel());
        newDatoPersonal.setPaisTituloTercerNivel(funcionario.getPaisTercerNivel());
        newDatoPersonal.setPaisTituloCuartoNivel(funcionario.getPaisCuartoNivel());*/


        return newDatoPersonal;
    }

    private DatoPersonal createDatoPersonalFromCiudadno(CiudadanoApiDto ciudadano) {
        DatoPersonal newDatoPersonal = new DatoPersonal();
        String[] nombreApellidos = dividirNombre(ciudadano.getNombre());

        newDatoPersonal.setApellido(nombreApellidos[0]);
        newDatoPersonal.setNombre(nombreApellidos[1]);
        newDatoPersonal.setCedula(ciudadano.getCedula());
        newDatoPersonal.setEstado(ACTIVO);

        // transforma fecha de api "fechaNacimiento":"05/01/1980" a LocalDateTime con time 00:00:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(ciudadano.getFechaNacimiento(), formatter);
        newDatoPersonal.setFechaNacimiento(fechaNacimiento.atStartOfDay());

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


    private String encodePassword(String password) {

        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }


}
