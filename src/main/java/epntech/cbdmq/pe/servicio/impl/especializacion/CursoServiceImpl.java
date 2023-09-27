package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CURSO_RECHAZO_DOCUMENTO;
import static epntech.cbdmq.pe.constante.MensajesConst.*;
import static epntech.cbdmq.pe.constante.EspecializacionConst.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.admin.especializacion.*;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.AulaRepository;
import epntech.cbdmq.pe.repositorio.admin.CatalogoCursoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.*;
import epntech.cbdmq.pe.servicio.DatoPersonalService;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.UsuarioService;
import epntech.cbdmq.pe.servicio.especializacion.CursoEstadoService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoService;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private CursoEspRepository cursoEspRepository;
    @Autowired
    private CursoDocumentoRepository cursoDocumentoRepository;
    @Autowired
    private CursoRequisitoRepository cursoRequisitoRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private CursoInstructorRepository cursoInstructorRepository;
    @Autowired
    private ParametroRepository parametroRepository;
    @Autowired
    private TipoCursoRepository tipoCursoRepository;
    @Autowired
    private CatalogoCursoRepository catalogoCursoRepository;
    @Autowired
    private CursoEstadoService cursoEstadoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private DatoPersonalService datoPersonalService;

    @Autowired
    private EmailService emailService;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;
    @Value("${spring.servlet.multipart.max-file-size}")
    public DataSize TAMAÑO_MAXIMO;

    @Override
    public Curso save(String datos, List<MultipartFile> documentos/*, Long codTipoDocumento*/) throws JsonProcessingException, ParseException, MessagingException {

        // valida si no hay documentos y sale con error
        if (documentos == null || documentos.isEmpty()) {
            throw new BusinessException("No se ha adjuntado ningún documento");
        }


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        JsonNode jsonNode = objectMapper.readTree(datos);
        System.out.println("jsonNode: " + jsonNode);

        Curso cursoDatos = objectMapper.readValue(datos, Curso.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();

            if (key.equals("codAula")) {
                cursoDatos.setCodAula((int) value.asLong());
            }
            if (key.equals("numeroCupo")) {
                cursoDatos.setNumeroCupo((int) value.asLong());
            }
            if (key.equals("fechaInicioCurso")) {
                if (value.asText().compareToIgnoreCase("null") != 0) {
                    LocalDateTime ldt = LocalDateTime.parse(value.asText().substring(0, 19), formatter);
                    LocalDate fecha = ldt.toLocalDate();
                    cursoDatos.setFechaInicioCurso(fecha);
                } else {
                    cursoDatos.setFechaInicioCurso(null);
                }
            }
            if (key.equals("fechaFinCurso")) {
                if (value.asText().compareToIgnoreCase("null") != 0) {
                    LocalDateTime ldt = LocalDateTime.parse(value.asText().substring(0, 19), formatter);
                    LocalDate fecha = ldt.toLocalDate();
                    cursoDatos.setFechaFinCurso(fecha);
                } else {
                    cursoDatos.setFechaInicioCurso(null);
                }
            }
            if (key.equals("fechaInicioCargaNota")) {
                if (value.asText().compareToIgnoreCase("null") != 0) {
                    LocalDateTime ldt = LocalDateTime.parse(value.asText().substring(0, 19), formatter);
                    LocalDate fecha = ldt.toLocalDate();
                    cursoDatos.setFechaInicioCargaNota(fecha);
                } else {
                    cursoDatos.setFechaInicioCurso(null);
                }
            }
            if (key.equals("fechaFinCargaNota")) {
                if (value.asText().compareToIgnoreCase("null") != 0) {

                    LocalDateTime ldt = LocalDateTime.parse(value.asText().substring(0, 19), formatter);
                    LocalDate fecha = ldt.toLocalDate();
                    cursoDatos.setFechaFinCargaNota(fecha);
                } else {
                    cursoDatos.setFechaInicioCurso(null);
                }
            }
            if (key.equals("notaMinima")) {
                cursoDatos.setNotaMinima((double) value.asLong());
            }
            if (key.equals("apruebaCreacionCurso")) {
                cursoDatos.setApruebaCreacionCurso(value.asBoolean());
            }
            if (key.equals("codCatalogoCursos")) {
                cursoDatos.setCodCatalogoCursos(value.asLong());
            }
            if (key.equals("estado")) {
                cursoDatos.setEstado(value.asText());
            }
            if (key.equals("emailNotificacion")) {
                cursoDatos.setEmailNotificacion(value.asText());
            }
            if (key.equals("tieneModulos")) {
                cursoDatos.setTieneModulos(value.asBoolean());
            }
            if (key.equals("porcentajeAceptacionCurso")) {
                cursoDatos.setPorcentajeAceptacionCurso((double) value.asLong());
            }
            if (key.equals("codUsuarioCreacion")) {
                cursoDatos.setCodUsuarioCreacion(value.asLong());
            }
            if (key.equals("nombre")) {
                cursoDatos.setNombre(value.asText());
            }

            cursoDatos.setCodUsuarioValidacion(null);
        }

        Set<Requisito> requisitos = cursoDatos.getRequisitos();

        // valida si no hay requisitos y sale con error
        if (requisitos == null || requisitos.isEmpty()) {
            throw new BusinessException("No se ha establecido ningún requisito");
        }

        /*Set<Requisito> reqs = new HashSet<>();
        for (
                Requisito r : requisitos) {
            Requisito requisito = new Requisito();
            requisito.setCodigoRequisito(r.getCodigoRequisito());
            reqs.add(requisito);
        }*/

        Aula aula = aulaRepository.findById(cursoDatos.getCodAula())
                .orElseThrow(() -> new BusinessException(AULA_NO_EXISTE));

        cursoDatos.setCodAula(aula.getCodAula());
        Curso cc;
        cc = cursoEspRepository.insertarCursosDocumentosRequisitos(cursoDatos, requisitos, documentos/*, codTipoDocumento*/);
        CatalogoCurso catalogoCurso= catalogoCursoRepository.findById(cc.getCodCatalogoCursos().intValue()).get();
        TipoCurso tipoCurso= tipoCursoRepository.findById(catalogoCurso.getCodTipoCurso().longValue()).get();
        LocalDateTime now = LocalDateTime.now();
// Formatear la fecha y hora al formato que desees, por ejemplo "dd/MM/yyyy HH:mm:ss"
        DateTimeFormatter formatterI = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = now.format(formatterI);
        Usuario usuario= usuarioService.getById(cc.getCodUsuarioCreacion()).get();
        String mensaje="Se ha creado el curso " + cc.getNombre() +"-"+catalogoCurso.getNombreCatalogoCurso()+" de tipo "+tipoCurso.getNombreTipoCurso()+ " con éxito."+"\n"+"El curso fue creado por "+usuario.getCodDatosPersonales().getNombre()+" "+ usuario.getCodDatosPersonales().getApellido()+" con fecha y hora "+ formattedDate;

        emailService.sendMensajeGeneral(cc.getEmailNotificacion(), "Creación de curso", mensaje);

        return cc;
    }

    @Override
    public Curso update(Curso objActualizado) throws MessagingException {
        Curso curso = cursoRepository.findById(objActualizado.getCodCursoEspecializacion())
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        curso.setCodAula(objActualizado.getCodAula());
        curso.setNumeroCupo(objActualizado.getNumeroCupo());
        curso.setFechaInicioCargaNota(objActualizado.getFechaInicioCargaNota());
        curso.setFechaFinCargaNota(objActualizado.getFechaFinCargaNota());
        curso.setNotaMinima(objActualizado.getNotaMinima());
        curso.setCodCatalogoCursos(objActualizado.getCodCatalogoCursos());
        curso.setEmailNotificacion(objActualizado.getEmailNotificacion());
        curso.setEstado(objActualizado.getEstado());
        curso.setTieneModulos(objActualizado.getTieneModulos());
        CatalogoCurso catalogoCurso= catalogoCursoRepository.findById(curso.getCodCatalogoCursos().intValue()).get();
        TipoCurso tipoCurso= tipoCursoRepository.findById(catalogoCurso.getCodTipoCurso().longValue()).get();
        LocalDateTime now = LocalDateTime.now();
// Formatear la fecha y hora al formato que desees, por ejemplo "dd/MM/yyyy HH:mm:ss"
        DateTimeFormatter formatterI = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = now.format(formatterI);
        String mensaje="Se ha editado el curso " + curso.getNombre() +"-"+catalogoCurso.getNombreCatalogoCurso()+" de tipo "+tipoCurso.getNombreTipoCurso()+ " con éxito."+"\n"+
                "El curso fue editado con fecha y hora "+ formattedDate;

        emailService.sendMensajeGeneral(curso.getEmailNotificacion(), "Edición de curso", mensaje);


        return cursoRepository.save(objActualizado);
    }
    @Override
    public Curso updateEstado(long codigo, String estado) {
        Curso curso = cursoRepository.findById(codigo)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        curso.setEstado(estado);

        return cursoRepository.save(curso);
    }

    @Override
    public Curso updateEstadoAprobadoObservaciones(long codigo, Boolean aprobadoCurso, String observaciones, long codigoUserAprueba) throws MessagingException {
        if(aprobadoCurso) {
            cursoEstadoService.updateNextState((int) codigo);
        }
        Curso curso = cursoRepository.findById(codigo)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
        curso.setApruebaCreacionCurso(aprobadoCurso);
        curso.setObservacionesValidacion(observaciones);
        curso.setCodUsuarioValidacion(codigoUserAprueba);
        curso= cursoRepository.save(curso);
        String mensaje=null;
        CatalogoCurso catalogoCurso= catalogoCursoRepository.findById(curso.getCodCatalogoCursos().intValue()).get();
        TipoCurso tipoCurso= tipoCursoRepository.findById(catalogoCurso.getCodTipoCurso().longValue()).get();
        if(aprobadoCurso) {
            mensaje="Se ha aprobado el curso " + curso.getNombre() + "-"+catalogoCurso.getNombreCatalogoCurso()+" de tipo "+tipoCurso.getNombreTipoCurso()+" con éxito." + (curso.getObservacionesValidacion() != null ? curso.getObservacionesValidacion() : "");

        }else{
            mensaje="Se ha rechazado el curso " +curso.getNombre() + "-"+catalogoCurso.getNombreCatalogoCurso()+" de tipo "+tipoCurso.getNombreTipoCurso()+ ". Verifique los datos y documentos registrados. " + (curso.getObservacionesValidacion() != null ? curso.getObservacionesValidacion() : "");
        }
        emailService.sendMensajeGeneral(curso.getEmailNotificacion(), "Validación de curso",mensaje );

        return curso;
    }



    @Override
    public List<Curso> listarAll() {
        return cursoRepository.findAll();
    }

    @Override
    public List<Curso> listarPorEstado(String estado) {
        return cursoRepository.findByEstado(estado);
    }

    @Override
    public List<Curso> getByCodigoTipoCurso(Integer codigoTipoCurso) {
        TipoCurso tipoCurso = tipoCursoRepository.findById(codigoTipoCurso.longValue())
                .orElseThrow(() -> new BusinessException(TIPO_CURSO_NO_EXISTE));
        return cursoRepository.findByCodigoTipoCurso(tipoCurso.getCodTipoCurso().intValue());
    }

    @Override
    public List<Curso> getByCodigoCatalogoCurso(Integer codigoCatalogoCurso) {
        CatalogoCurso catalogoCurso = catalogoCursoRepository.findById(codigoCatalogoCurso)
                .orElseThrow(() -> new BusinessException(CATALOGO_CURSO_NO_EXISTE));
        return cursoRepository.findByCodCatalogoCursos(catalogoCurso.getCodCatalogoCursos().longValue());
    }

    @Override
    public Curso getById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
    }

    @Override
    public CursoDocumento updateEstadoAprobadoValidado(Boolean estadoAprobado, Boolean estadoValidado,
                                                       String observaciones, Long codCursoEspecializacion, Long codDocumento) {
        Curso curso = cursoRepository.findById(codCursoEspecializacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        Documento documento = documentoRepository.findById(codDocumento.intValue())
                .orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));

        cursoDocumentoRepository.updateEstadoAprobado(estadoAprobado, estadoValidado, observaciones,
                codCursoEspecializacion, codDocumento);
        cursoRepository.validaDocumentosCursoEspecializacion(codCursoEspecializacion);

        if (Boolean.TRUE.equals(!estadoAprobado) || Boolean.TRUE.equals(!estadoValidado))
            notificarRechazo(curso.getCodCursoEspecializacion(), documento, observaciones);

        return cursoDocumentoRepository.findByCodCursoEspecializacionAndCodDocumento(codCursoEspecializacion,
                codDocumento);
    }

    private void notificarRechazo(Long codCursoEspecializacion, Documento documento, String observaciones) {
        cursoInstructorRepository.findInstructoresCurso(codCursoEspecializacion)
                .forEach(ci -> {
                    notificarRechazoInstructor(ci, documento, observaciones);
                });
    }

    private void notificarRechazoInstructor(InstructoresCurso instructoresCurso, Documento documento, String
            observaciones) {
        Parametro parametro = parametroRepository.findByNombreParametro("especializacion.rechazo.curso.notificacion.body")
                .orElseThrow(() -> new BusinessException(NO_PARAMETRO));
        String nombres = instructoresCurso.getNombre() + " " + instructoresCurso.getApellido();
        String cuerpoHtml = String.format(parametro.getValor(), nombres, instructoresCurso.getNombreCatalogoCurso(), documento.getNombre(), observaciones);
        String[] destinatarios = {instructoresCurso.getCorreoInstitucional(), instructoresCurso.getCorreoPersonal()};
        emailService.sendMensajeGeneralList(destinatarios, EMAIL_SUBJECT_CURSO_RECHAZO_DOCUMENTO, cuerpoHtml);
    }

    @Override
    public Curso iniciarCurso(Long codCursoEspecializacion) {
        Curso curso = cursoRepository.findById(codCursoEspecializacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        if (Boolean.FALSE.equals(curso.getApruebaCreacionCurso())) {
            throw new BusinessException(CURSO_NO_VALIDADO);
        }

        curso.setEstado(CURSO_ESTADO_INICIADO);
        cursoRepository.save(curso);

        return curso;
    }

    @Override
    public Curso updateEstadoProceso(Long estado, Long codCurso) {
        Curso curso = cursoRepository.findById(codCurso)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        int result = cursoRepository.updateEstadoProceso(estado, curso.getCodCursoEspecializacion());
        if (result == 1)
            return cursoRepository.findById(curso.getCodCursoEspecializacion()).get();
        else
            throw new BusinessException(ESTADO_INCORRECTO);
    }


    @Override
    public Curso updateRequisitos(Long codCursoEspecializacion, List<Requisito> requisitos) {
        Curso curso = cursoRepository.findById(codCursoEspecializacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        Optional<CursoRequisito> cursoRequisito = cursoRequisitoRepository
                .findFirstByCodCursoEspecializacion(curso.getCodCursoEspecializacion());
        if (cursoRequisito.isPresent())
            cursoRequisitoRepository.deleteByCodCursoEspecializacion(codCursoEspecializacion);

        for (Requisito requisito : requisitos) {
            CursoRequisito cr = new CursoRequisito();
            cr.setCodCursoEspecializacion(codCursoEspecializacion);
            cr.setCodRequisito((long) requisito.getCodigoRequisito());
            cursoRequisitoRepository.save(cr);
        }

        return curso;
    }

    @Override
    public Documento updateDocumento(Long codDocumento, MultipartFile archivo) throws IOException {
        Documento documento = documentoRepository.findById(codDocumento.intValue())
                .orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));

        if (archivo.getSize() == 0)
            throw new BusinessException(NO_ADJUNTO);

        Path ruta = Paths.get(documento.getRuta());

        try {

            if (Files.exists(ruta)) {
                Files.delete(ruta);
            }

            if (archivo.getSize() > TAMAÑO_MAXIMO.toBytes()) {
                throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
            }
            if (!Files.exists(ruta)) {
                Files.createDirectories(ruta);
            }
            Files.copy(archivo.getInputStream(), ruta.getParent().resolve(Objects.requireNonNull(archivo.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);

            documento.setNombre(archivo.getOriginalFilename());
            documento.setRuta(ruta.getParent() + "/" + archivo.getOriginalFilename());
            documentoRepository.save(documento);

        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        return documento;
    }

    @Override
    public Curso uploadDocumentos(Long codCursoEspecializacion, List<MultipartFile> archivos, Long
            codTipoDocumento) throws IOException, ArchivoMuyGrandeExcepcion {
        Curso curso = cursoRepository.findById(codCursoEspecializacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
        guardarDocumentos(archivos, curso.getCodCursoEspecializacion(), codTipoDocumento);
        return curso;
    }

    public void guardarDocumentos(List<MultipartFile> archivos, Long codCursoEspecializacion, Long tipoDocumento)
            throws IOException, ArchivoMuyGrandeExcepcion {
        String resultadoRuta;

        resultadoRuta = ruta(codCursoEspecializacion);
        Path ruta = Paths.get(resultadoRuta);

        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }

        for (MultipartFile multipartFile : archivos) {
            if (multipartFile.getSize() > TAMAÑO_MAXIMO.toBytes()) {
                throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
            }

            Files.copy(multipartFile.getInputStream(), ruta.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            // LOGGER.info("Archivo guardado: " + resultado +
            Documento documento = new Documento();
            documento.setEstado("ACTIVO");
            documento.setTipo(tipoDocumento.intValue());
            documento.setNombre(multipartFile.getOriginalFilename());
            documento.setRuta(resultadoRuta + multipartFile.getOriginalFilename());
            documento = documentoRepository.save(documento);

            CursoDocumento cursoDocumento = new CursoDocumento();
            cursoDocumento.setCodCursoEspecializacion(codCursoEspecializacion);
            cursoDocumento.setCodDocumento((long) documento.getCodDocumento());
            cursoDocumentoRepository.save(cursoDocumento);
        }

    }

    @Transactional
    public void generaDocumento(String ruta, String nombre, Long codCursoEspecializacion) throws DataException {

        // busca la pruebaDetalle. Si no encuentra hay un error de consistencia de datos
        Long codCursoEspecial = null;


        Optional<Curso> curso = cursoRepository.findById(codCursoEspecial);
        if (curso.isPresent()) {
            codCursoEspecial = curso.get().getCodCursoEspecializacion();
        } else {
            throw new DataException(CURSO_NO_EXISTE);
        }

        // busca documentos para el curso
        List<CursoDocumento> listaDocCurso = cursoDocumentoRepository.findAllByCodCursoEspecializacion(codCursoEspecial);

        if (listaDocCurso != null && listaDocCurso.size() > 0) {


            // busca si existe un documento con el mismo nombre
            List<Documento> docsGeneral = this.documentoRepository.findAllByNombre(nombre);

            // si hay documentos con el mismo nombre, busca el que corresponda a ese curso
            if (docsGeneral != null && docsGeneral.size() > 0) {

                List<Integer> listaCodCurso = listaDocCurso.stream()
                        .map(p -> {
                            return p.getCodDocumento().intValue();
                        })
                        .collect(Collectors.toList());

                List<Integer> listaCodDocumentoGeneral = docsGeneral.stream()
                        .map(Documento::getCodDocumento)
                        .collect(Collectors.toList());

                // intersección de las listas
                Set<Integer> resultado = listaCodCurso.stream()
                        .distinct()
                        .filter(listaCodDocumentoGeneral::contains)
                        .collect(Collectors.toSet());

                if (resultado != null && resultado.size() > 0) {

                    final Long cursoEspecializacion = codCursoEspecial;

                    resultado.stream()
                            .forEach(codDoc -> {
                                // elimina de documentoPrueba
                                try {
                                    cursoDocumentoRepository.deleteByCodCursoEspecializacionAndCodDocumento(cursoEspecializacion, codDoc.longValue());
                                    documentoRepository.deleteById(codDoc);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            });

                }

            }

        }

        // genera documento
        Documento documento = new Documento();
        documento.setEstado("ACTIVO");
        documento.setNombre(nombre);
        documento.setRuta(ruta);


        documento = documentoRepository.save(documento);

        CursoDocumento cursoActivoDocumento = new CursoDocumento();
        cursoActivoDocumento.setCodCursoEspecializacion(codCursoEspecial);
        ;
        cursoActivoDocumento.setCodDocumento(documento.getCodDocumento().longValue());
        cursoDocumentoRepository.save(cursoActivoDocumento);

    }

    private String ruta(Long codigo) {
        String resultado = null;
        resultado = ARCHIVOS_RUTA + PATH_PROCESO_ESPECIALIZACION + codigo + "/";
        return resultado;
    }

    @Override
    public void delete(Long codCursoEspecializacion) {
        Curso curso = cursoRepository.findById(codCursoEspecializacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        cursoRepository.deleteById(curso.getCodCursoEspecializacion());

    }

    @Override
    public Boolean cumpleMinimoAprobadosCurso(Long codCursoEspecializacion) {
        Curso curso = cursoRepository.findById(codCursoEspecializacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        return cursoRepository.cumplePorcentajeMinimoAprobadosCurso(curso.getCodCursoEspecializacion());
    }

    @Override
    public void deleteDocumento(Long codCursoEspecializacion, Long codDocumento) {
        Curso curso = cursoRepository.findById(codCursoEspecializacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        Documento documento = documentoRepository.findById(codDocumento.intValue())
                .orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));

        Path ruta = Paths.get(documento.getRuta());

        if (Files.exists(ruta)) {
            try {
                Files.delete(ruta);
                cursoDocumentoRepository.deleteByCodCursoEspecializacionAndCodDocumento(curso.getCodCursoEspecializacion(), codDocumento);
                documentoRepository.deleteById(codDocumento.intValue());
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }

        }

    }

    @Override
    public List<Curso> listarPorEstadoAll(String estado) {
        List<Curso> lista;
        Sort sort = Sort.by(Sort.Direction.ASC, "nombre");

        if (ABIERTOS.equals(estado)) {
            List<Curso> listaCerrados = cursoRepository.findAllByEstadoContainsIgnoreCase(CIERRE,sort);
            lista = listarAll();
            lista.removeAll(listaCerrados);
        } else if (CERRADOS.equals(estado)) {
            lista = cursoRepository.findAllByEstadoContainsIgnoreCase(CIERRE,sort);
        } else if (TODOS.equals(estado)) {
            lista = cursoRepository.findAllOrderedByName();
        } else {
            lista = listarPorEstado(estado);
        }

        return lista;
    }

    @Override
    public List<Curso> listarPorInstructorAndEstado(Integer codUsuario, String estado) {
        return cursoRepository.findByInstructorAndEstado(codUsuario, estado);
    }
    @Override
    public Boolean reabrirCurso(Integer idCurso) throws DataException {
        String mensaje = this.getById(idCurso.longValue()).getEstado();
        Integer codCursoEstadoToUpdate = null;

        switch (mensaje) {
            case CIERRE_INSCRITOS:
                codCursoEstadoToUpdate = cursoEstadoService.getByTipoCursoAndEstado(idCurso.longValue(), INSCRIPCION).getCodCursoEstado().intValue();
                break;

            case CIERRE_VALIDACION:
                codCursoEstadoToUpdate = cursoEstadoService.getByTipoCursoAndEstado(idCurso.longValue(), VALIDACION_REQUISITOS).getCodCursoEstado().intValue();
                break;

            case CIERRE_PRUEBAS:
                codCursoEstadoToUpdate = cursoEstadoService.getByTipoCursoAndEstado(idCurso.longValue(), VALIDACION_PRUEBAS).getCodCursoEstado().intValue();
                break;

            default:
                return false;
        }

        if (codCursoEstadoToUpdate != null) {
            cursoEstadoService.updateState(idCurso, codCursoEstadoToUpdate);
            return true;
        }

        return false;
    }

    @Override
    public List<Curso> listarPorEstudianteAndEstado(Integer codigoEstudiante, String estado) {
        return cursoRepository.findByEstudianteAndEstado(codigoEstudiante, estado);
    }

    @Override
    public List<Curso> listarCerradosPorEstudiante(Integer codigoEstudiante) {
        return this.listarPorEstudianteAndEstado(codigoEstudiante, "cierre%");
    }

    @Override
    public List<Curso> listarAllPorEstudiante(Integer codigoEstudiante) {
        return this.listarPorEstudianteAndEstado(codigoEstudiante, "%");
    }

    @Override
    public List<Curso> findByFechaInicioBetween(LocalDate startDate, LocalDate endDate) {
        return cursoRepository.findByFechaInicioCursoBetween(startDate, endDate);
    }


}
