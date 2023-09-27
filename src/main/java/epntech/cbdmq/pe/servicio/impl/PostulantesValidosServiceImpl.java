package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_RESULTADO_VALIDACION;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_PRUEBAS;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.util.PostulantesAprobadosReprobados;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.*;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.PostulantesValidosService;
import jakarta.mail.MessagingException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostulantesValidosServiceImpl implements PostulantesValidosService {

    @Autowired
    private PostulantesValidosRepository repo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ResultadoPruebasRepository resultadoPruebasRepository;
    @Autowired
    private PruebaDetalleRepository pruebaDetalleRepository;
    @Autowired
    private PeriodoAcademicoRepository periodoAcademicoRepository;
    @Autowired
    private PeriodoAcademicoDocForRepository periodoAcademicoDocForRepository;
    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;
    @Autowired
    private DocumentoRepository documentoRepo;

    @Override
    public List<PostulantesValidos> getPostulantesValidos() {
        return repo.getPostulantesValidos();
    }

    @Override
    public List<PostulantesValidos> getPostulantesValidosDiferentBaja() {
        return repo.getPostulantesValidosDiferentBaja();
    }

    @Override
    public List<PostulantesValidos> getAllPostulantesValidos() {
        return repo.getAllPostulantesValidos();
    }
    @Override
    public List<PostulantesValidos> getAllPostulantesNoValidos() {
        return repo.getAllPostulantesNoValidos();
    }

    @Override
    public List<PostulantesAprobadosReprobados> getPospulantesAprobadosReprobados() {
        List<PostulantesValidos> listaValidos = getAllPostulantesValidos();
        List<PostulantesValidos> listaNoValidos = getAllPostulantesNoValidos();

        List<PostulantesAprobadosReprobados> listaAprobados = listaValidos.stream()
                .map(this::transformToAprobado)
                .collect(Collectors.toList());

        List<PostulantesAprobadosReprobados> listaNoAprobados = listaNoValidos.stream()
                .map(this::transformToNoAprobado)
                .collect(Collectors.toList());

        listaAprobados.addAll(listaNoAprobados);

        return listaAprobados;
    }
    // Método para transformar un PostulantesValidos a PostulantesAprobadosReprobados con esAprobado = true
    private PostulantesAprobadosReprobados transformToAprobado(PostulantesValidos postulanteValido) {
        PostulantesAprobadosReprobados postulante = new PostulantesAprobadosReprobados();
        postulante.setCodPostulante(postulanteValido.getCodPostulante());
        postulante.setIdPostulante(postulanteValido.getIdPostulante());
        postulante.setCedula(postulanteValido.getCedula());
        postulante.setCorreoPersonal(postulanteValido.getCorreoPersonal());
        postulante.setNombre(postulanteValido.getNombre());
        postulante.setApellido(postulanteValido.getApellido());
        postulante.setEsAprobado(true);
        return postulante;
    }

    // Método similar pero con esAprobado = false
    private PostulantesAprobadosReprobados transformToNoAprobado(PostulantesValidos postulanteValido) {
        PostulantesAprobadosReprobados postulante = transformToAprobado(postulanteValido);
        postulante.setEsAprobado(false);
        return postulante;
    }



    @Override
    public Page<PostulantesValidos> getAllPostulantesValidosPaginado(Pageable pageable) {
        return this.repo.getAllPostulantesValidosPaginado(pageable);
    }

    @Override
    public List<PostulantesValidos> getAllPostulantesValidosOrderApellido() {
        return this.repo.getAllPostulantesValidosOrderApellido();
    }

    @Override
    public Page<PostulantesValidos> getAllPostulantesValidosPaginadoOrderApellido(Pageable pageable) {
        return this.repo.getAllPostulantesValidosPaginadoOrderApellido(pageable);
    }

    @Override
    @Async
    public void notificar(String mensaje, String prueba, Date fechaIni, Date fechaFin, LocalTime hora,
                          Integer codPrueba) throws MessagingException, Exception {
        List<PostulantesValidos> lista = new ArrayList<>();
        // try {

        Optional<PruebaDetalle> pruebaDetalle = pruebaDetalleRepository
                .findByCodSubtipoPruebaAndCodPeriodoAcademico(codPrueba, periodoAcademicoRepository.getPAActive());
        if (pruebaDetalle.isPresent() && pruebaDetalle.get().getOrdenTipoPrueba().equals(1)) {
            lista = repo.getAllPostulantesValidos();
        } else if (pruebaDetalle.isPresent()) {
            lista = repo.get_approved_by_test(codPrueba);
        }
        System.out.println("lista: " + lista.size());
        for (PostulantesValidos postulantesValidos : lista) {
            String msg = String.format(mensaje, postulantesValidos.getIdPostulante(), prueba, fechaIni, fechaFin, hora);

            emailService.sendMensajeTextGenerico(postulantesValidos.getCorreoPersonal(), EMAIL_SUBJECT_PRUEBAS, msg);
        }
        /*
         * }catch(Exception ex) { System.out.println("error: " + ex.getMessage()); throw
         * new Exception(ex.getMessage()); }
         */
    }

    @Override
    public void onInitResultado(List<PostulantesValidos> obj, Integer prueba) {
        List<ResultadoPruebas> resultadoPruebas = new ArrayList<>();

        for (PostulantesValidos postulantesValidos : obj) {

            ResultadoPruebas pruebas = new ResultadoPruebas();
            pruebas.setCodPostulante(postulantesValidos.getCodPostulante());
            pruebas.setCodPruebaDetalle(prueba);
            pruebas.setEstado("ACTIVO");
            resultadoPruebas.add(pruebas);
        }

        resultadoPruebasRepository.saveAll(resultadoPruebas);
    }

    @Override
    public Page<PostulantesValidos> getAllPaginado(Pageable pageable, Integer codPrueba) throws Exception {
        // return repo.getPostulantesValidosPaginado(pageable);
        return repo.get_approved_by_test(pageable, codPrueba);
    }

    @Override
    public List<PostulantesValidos> getPostulantesAprovadosPrueba(Integer prueba) {
        return repo.get_approved_by_test(prueba);
    }

    @Override
    public List<PostulantesValidos> getPostulantesValidosFiltro(String tipoFiltro, String valorFiltro) {
        List<PostulantesValidos> lista = new ArrayList<>();
        switch (tipoFiltro) {
            case "cedula":
                lista = repo.getPostulantesValidosFiltroCedula(valorFiltro);
                break;
            case "idPostulante":
                lista = repo.getPostulantesValidosFiltroIdPostulante(valorFiltro);
                break;
            case "apellido":
                lista = repo.getPostulantesValidosFiltroApellido(valorFiltro);
                break;
            default:
                lista = repo.getAllPostulantesValidos();
                break;
        }
        return lista;
    }

    /////////////////////////////////////////////////////////
    // listado postulantes para seguimiento de inscripciones

    @Override
    public List<PostulantesValidos> getPostulantesTodoFiltro(String tipoFiltro, String valorFiltro) {
        List<PostulantesValidos> lista = new ArrayList<>();
        switch (tipoFiltro) {
            case "cedula":
                lista = repo.getPostulantesTodoFiltroCedula(valorFiltro);
                break;
            case "idPostulante":
                lista = repo.getPostulantesTodoFiltroIdPostulante(valorFiltro);
                break;
            case "apellido":
                lista = repo.getPostulantesTodoFiltroApellido(valorFiltro);
                break;
            default:
                lista = repo.getAllPostulantesTodo();
                break;
        }
        return lista;
    }

    @Override
    public Page<PostulantesValidos> getAllPostulantesTodoPaginado(Pageable pageable) {
        return this.repo.getAllPostulantesTodoPaginado(pageable);
    }


    @Override
    public Page<PostulantesValidos> getAllPostulantesTodoPaginadoOrderApellido(Pageable pageable) {
        return this.repo.getAllPostulantesTodoPaginadoOrderApellido(pageable);
    }

    @Override
    public Boolean generarArchivos(HttpServletResponse response, Boolean esAprobado) throws DataException, DocumentException, IOException {
        String[] columnas = {"Código Único", "Correo", "Cedula", "Nombre", "Apellido"};

        String ruta = ARCHIVOS_RUTA + PATH_RESULTADO_VALIDACION
                + periodoAcademicoRepository.getPAActive().toString()
                + "/";
        Integer codCurso = null;
        if (esAprobado) {
            String nombre1 = "AprobadosRequisitos.pdf";
            String nombre2 = "AprobadosRequisitos.xlsx";
            this.generarPDF(response, ruta + nombre1, nombre1, codCurso, columnas, true);
            this.generarExcel(ruta + nombre2, nombre2, columnas, codCurso, true);
        } else {
            String nombre1 = "ReprobadosRequisitos.pdf";
            String nombre2 = "ReprobadosRequisitos.xlsx";
            this.generarPDF(response, ruta + nombre1, nombre1, codCurso, columnas, false);
            this.generarExcel(ruta + nombre2, nombre2, columnas, codCurso, false);

        }

        return null;
    }

    @Override
    public Boolean generarArchivosAprobados(HttpServletResponse response) throws DataException, DocumentException, IOException {
        return this.generarArchivos(response, true);
    }

    @Override
    public Boolean generarArchivosReprobados(HttpServletResponse response) throws DataException, DocumentException, IOException {
        return this.generarArchivos(response, false);
    }

    public void generarPDF(HttpServletResponse response, String ruta, String nombre, Integer codCurso, String[] headers, Boolean esAprobado)
            throws DocumentException, IOException, DataException {

        ExporterPdf exporter = new ExporterPdf();
        //anchos de las columnas
        float[] widths = new float[]{2.5f, 2.5f, 2.5f, 2.5f, 2.5f};

        //Genera el pdf
        exporter.setArchivosRuta(ARCHIVOS_RUTA);
        if (esAprobado) {
            exporter.exportar(response, headers, obtenerDatosAprobados(codCurso), widths, ruta);
        } else {
            exporter.exportar(response, headers, obtenerDatosReprobados(codCurso), widths, ruta);
        }
        generaDocumento(ruta, nombre, codCurso, esAprobado);



    }

    public void generarExcel(String ruta, String nombre, String[] headers, Integer codCurso, Boolean esAprobado) throws IOException, DataException {
        // Optional<Prueba> pp = pruebaRepository.findById(prueba);


        if (esAprobado) {
            ExcelHelper.generarExcel(obtenerDatosAprobados(codCurso), ruta, headers);
        } else {
            ExcelHelper.generarExcel(obtenerDatosReprobados(codCurso), ruta, headers);
        }
        generaDocumento(ruta, nombre, codCurso,esAprobado);

    }

    public ArrayList<ArrayList<String>> obtenerDatosAprobados(Integer codCurso) {

        List<PostulantesValidos> datos;

        if (codCurso != null) {
            datos = null;
        } else {
            datos = this.getAllPostulantesValidos();
        }

        return entityToArrayList(datos);
    }

    public ArrayList<ArrayList<String>> obtenerDatosReprobados(Integer codCurso) {

        List<PostulantesValidos> datos;

        if (codCurso != null) {
            //TODO reprobados despues de validación en especialización
            datos = null;
        } else {
            datos = this.getAllPostulantesNoValidos();
        }

        return entityToArrayList(datos);
    }

    public static ArrayList<ArrayList<String>> entityToArrayList(List<PostulantesValidos> datos) {
        ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
        for (PostulantesValidos dato : datos) {

            arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArray(dato))));
        }
        return arrayMulti;
    }

    public static String[] entityToStringArray(PostulantesValidos entity) {
        return new String[]{entity.getIdPostulante() != null ? entity.getIdPostulante().toString() : "",
                entity.getCorreoPersonal(),
                entity.getCedula(), entity.getNombre(),
                entity.getApellido()};
    }

    @Transactional
    public void generaDocumento(String ruta, String nombre, Integer codCurso, Boolean esAprobado) throws DataException {

        Documento documento = new Documento();
        documento.setEstado("ACTIVO");
        documento.setNombre(nombre);
        documento.setRuta(ruta);

        if (esAprobado) {
            documento.setDescripcion("APROBADOS");
        } else {
            documento.setDescripcion("REPROBADOS");
        }

        documento = documentoRepo.save(documento);

        // si es curso, guarda en repo del curso, caso contrario guarda en repo de formación
        if (codCurso != null) {
            //TODO
			/*
			CursoDocumento cursoDoc = new CursoDocumento();
			cursoDoc.setCodCursoEspecializacion(codCurso.longValue());
			cursoDoc.setCodDocumento(documento.getCodDocumento().longValue());
			cursoDocumentoRepository.save(cursoDoc);
			 */
        } else {
            PeriodoAcademicoDocumentoFor docPA = new PeriodoAcademicoDocumentoFor();
            docPA.setCodPeriodoAcademico(periodoAcademicoRepository.getPAActive());
            docPA.setCodDocumento(documento.getCodDocumento());
            periodoAcademicoDocForRepository.save(docPA);
        }
    }


}
