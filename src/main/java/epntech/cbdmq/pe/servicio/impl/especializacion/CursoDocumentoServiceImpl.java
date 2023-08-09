package epntech.cbdmq.pe.servicio.impl.especializacion;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.Estados;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.InscripcionEspRepository;
import epntech.cbdmq.pe.servicio.EstadosService;
import epntech.cbdmq.pe.servicio.especializacion.CursoDocumentoService;
import epntech.cbdmq.pe.servicio.especializacion.CursoEstadoService;
import epntech.cbdmq.pe.servicio.especializacion.CursoService;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;
import static epntech.cbdmq.pe.constante.EspecializacionConst.*;
import static epntech.cbdmq.pe.constante.EstadosConst.PRUEBAS;
import static epntech.cbdmq.pe.constante.MensajesConst.DOCUMENTO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

@Service
public class CursoDocumentoServiceImpl implements CursoDocumentoService {
    @Autowired
    private CursoService cursoService;
    @Autowired
    private CursoDocumentoRepository cursoDocumentoRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private CursoEstadoService cursoEstadoService;
    @Autowired
    private InscripcionEspRepository inscripcionEspRepository;
    @Autowired
    private EstadosService estadosService;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;

    @Value("${spring.servlet.multipart.max-file-size}")
    public DataSize TAMAÑO_MÁXIMO;


    @Override
    public Set<Documento> getDocumentos(Long codCurso) throws IOException {
        return documentoRepository.getDocumentosEspecializacion(codCurso.intValue());
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

            if (archivo.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
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
    public Curso uploadDocumentos(Long codCursoEspecializacion, List<MultipartFile> archivos) throws IOException, ArchivoMuyGrandeExcepcion, DataException {
        Curso curso = cursoService.getById(codCursoEspecializacion);
        if (curso == null)
            new BusinessException(REGISTRO_NO_EXISTE);
        guardarDocumentos(archivos, curso.getCodCursoEspecializacion());
        return curso;
    }

    @Override
    public void deleteDocumento(Long codCursoEspecializacion, Long codDocumento) throws DataException, IOException {
        Curso curso = cursoService.getById(codCursoEspecializacion);
        if (curso == null)
            new BusinessException(REGISTRO_NO_EXISTE);

        Documento documento = documentoRepository.findById(codDocumento.intValue())
                .orElseThrow(() -> new BusinessException(DOCUMENTO_NO_EXISTE));


        Path ruta = null;

        ruta = Paths.get(documento.getRuta()).toAbsolutePath().normalize();

        try {
            Files.delete(ruta);
            cursoDocumentoRepository.deleteByCodCursoEspecializacionAndCodDocumento(curso.getCodCursoEspecializacion(), codDocumento);
            documentoRepository.deleteById(codDocumento.intValue());

        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

    }

    public void guardarDocumentos(List<MultipartFile> archivos, Long codCursoEspecializacion)
            throws IOException, ArchivoMuyGrandeExcepcion, DataException {
        String resultadoRuta;

        resultadoRuta = ruta(codCursoEspecializacion);
        Path ruta = Paths.get(resultadoRuta);

        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }

        for (MultipartFile multipartFile : archivos) {
            if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
                throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
            }

            Files.copy(multipartFile.getInputStream(),
                    ruta.getParent().resolve(multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            // LOGGER.info("Archivo guardado: " + resultado +
            this.generaDocumento(resultadoRuta, multipartFile.getOriginalFilename(), codCursoEspecializacion);
        }

    }


    @Override
    @Transactional
    public void generaDocumento(String ruta, String nombre, Long codCursoEspecializacion) throws DataException {
        ruta = ruta + nombre;
        // busca la pruebaDetalle. Si no encuentra hay un error de consistencia de datos
        Long codCursoEspecial = codCursoEspecializacion;


        Curso curso = cursoService.getById(codCursoEspecial);
        if (curso != null) {
            codCursoEspecial = curso.getCodCursoEspecializacion();
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
        cursoActivoDocumento.setCodDocumento(documento.getCodDocumento().longValue());
        cursoDocumentoRepository.save(cursoActivoDocumento);

    }

    @Override
    public Set<Documento> getDocumentosByCurso(Long codigoCurso) {
        return documentoRepository.getDocumentosEspecializacion(Math.toIntExact(codigoCurso));
    }

    @Override
    public boolean generarDocumentoInscritos(HttpServletResponse response, Long codigoCurso) {
        String mensaje = cursoEstadoService.getEstadoByCurso(codigoCurso);

        switch (mensaje) {
            case VALIDACION_REQUISITOS:
                if (inscripcionEspRepository.cumplePorcentajeMinimoInscritosCurso(codigoCurso)) {
                    return this.generarDocListadoInscripcion(response, codigoCurso);
                } else {
                    cursoService.updateEstado(codigoCurso, CIERRE_INSCRITOS);
                    return false;
                }
            case VALIDACION_PRUEBAS:
                if (inscripcionEspRepository.cumplePorcentajeMinimoInscritosCurso(codigoCurso)) {
                    return this.generarDocListadoValidacion(response, codigoCurso);
                } else {
                    cursoService.updateEstado(codigoCurso, CIERRE_VALIDACION);
                    return false;
                }
            case CURSO:
                if (true
                        //inscripcionEspRepository.cumplePorcentajeMinimoAprobadosPruebas(codigoCurso)
                 ) {
                    return this.generarDocListadoPruebas(response, codigoCurso);
                } else {
                    cursoService.updateEstado(codigoCurso, CIERRE_PRUEBAS);
                    return false;
                }
            default:
                return mensaje != null;
        }
    }


    @Override
    public void generarExcel(String filePath, String nombre, Long codCurso, String estado) throws IOException, DataException {
        String[] HEADERs = {"Codigo", "Cedula", "Correo"};
        try {
            ExcelHelper.generarExcel(obtenerDatos(codCurso, estado), filePath, HEADERs);

            this.generaDocumento(filePath, nombre, codCurso);

        } catch (IOException ex) {
            System.out.println("error: " + ex.getMessage());
        }
    }

    @Override
    public void generarPDF(HttpServletResponse response, String filePath, String nombre, Long codCurso, String estado) throws DocumentException, IOException, DataException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Cuerpo-Bomberos";
        String valor = "attachment; filename=Datos" + fechaActual + ".pdf";

        response.addHeader(cabecera, valor);

        ExporterPdf exporter = new ExporterPdf();
        String[] columnas = {"Codigo", "Cedula", "Nombre"};
        float[] widths = new float[]{2f, 3f, 6f};

        //Genera el pdf
        exporter.setArchivosRuta(ARCHIVOS_RUTA);
        exporter.exportar(response, columnas, obtenerDatos(codCurso, estado), widths, filePath);

        this.generaDocumento(filePath, nombre, codCurso);
    }

    @Override
    public Boolean generarDocListadoGeneral(HttpServletResponse response, Long codCurso, String estado) {
        try {

            String nombre = LISTADOSESPECIALIZACION+estado;
            String ruta = ARCHIVOS_RUTA + PATH_PROCESO_ESPECIALIZACION + codCurso.toString() + "/" + nombre;

            this.generarExcel(ruta + ".xlsx", nombre + ".xlsx", codCurso, estado);
            this.generarPDF(response, ruta + ".pdf", nombre + ".pdf", codCurso, estado);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error: " + e.getMessage());
        } catch (DataException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Boolean generarDocListadoInscripcion(HttpServletResponse response, Long codCurso) {
        return this.generarDocListadoGeneral(response, codCurso, INSCRIPCION);
    }

    @Override
    public Boolean generarDocListadoValidacion(HttpServletResponse response, Long codCurso) {
        return this.generarDocListadoGeneral(response, codCurso, VALIDACION);
    }

    @Override
    public Boolean generarDocListadoPruebas(HttpServletResponse response, Long codCurso) {
        return this.generarDocListadoGeneral(response, codCurso, PRUEBAS);
    }

    public ArrayList<ArrayList<String>> obtenerDatos(Long codCurso, String estado) {
        Set<InscripcionDatosEspecializacion> datos = new HashSet<>();

        datos = (Set<InscripcionDatosEspecializacion>) inscripcionEspRepository.getInscripcionesByCursoEstado(codCurso, estado);

        return entityToArrayListFormacion(datos);
    }

    public static String[] entityToStringArrayFormacion(InscripcionDatosEspecializacion entity) {
        return new String[]{entity.getCodInscripcion().toString(), entity.getCedula(),
                entity.getCorreoUsuario()};
    }

    public static ArrayList<ArrayList<String>> entityToArrayListFormacion(Set<InscripcionDatosEspecializacion> datos) {
        ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
        for (InscripcionDatosEspecializacion dato : datos) {

            arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArrayFormacion(dato))));
        }
        return arrayMulti;
    }

    private String ruta(Long codigo) {
        String resultado = null;
        resultado = ARCHIVOS_RUTA + PATH_PROCESO_ESPECIALIZACION + codigo + "/";
        return resultado;
    }
}
