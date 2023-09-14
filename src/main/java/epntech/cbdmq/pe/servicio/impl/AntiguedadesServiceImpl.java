package epntech.cbdmq.pe.servicio.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.*;
import epntech.cbdmq.pe.repositorio.admin.especializacion.TipoCursoRepository;
import epntech.cbdmq.pe.servicio.DatoPersonalService;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.EstudianteService;
import epntech.cbdmq.pe.servicio.especializacion.CursoDocumentoService;
import epntech.cbdmq.pe.servicio.especializacion.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.util.AntiguedadesDatos;
import epntech.cbdmq.pe.dominio.util.AntiguedadesFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.servicio.AntiguedadesService;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;

import static epntech.cbdmq.pe.constante.EmailConst.*;
import static epntech.cbdmq.pe.constante.EstadosConst.ACTIVO;
import static epntech.cbdmq.pe.constante.MensajesConst.NO_PARAMETRO;

@Service
public class AntiguedadesServiceImpl implements AntiguedadesService {

    @Autowired
    private AntiguedadesRepository antiguedadesRepository;
    @Autowired
    private DocumentoRepository documentoRepo;
    @Autowired
    private PeriodoAcademicoRepository periodoAcademicoRepository;
    @Autowired
    private AntiguedadesFormacionRepository antiguedadesFormacionRepository;
    @Autowired
    private PeriodoAcademicoDocForRepository periodoAcademicoDocForRepository;
    @Autowired
    private CursoDocumentoService cursoDocumentoSvc;
    @Autowired
    private ParametroRepository parametroRepository;
    @Autowired
    private DatoPersonalService dpSvc;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CursoService cursoSc;
    @Autowired
    private TipoCursoRepository tipoCursoRepository;
    @Autowired
    private CatalogoCursoRepository catalogoCursoRepository;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;

    @Override
    public Set<AntiguedadesDatos> getAntiguedadesMasculino() {

        return antiguedadesRepository.getAntiguedadesMasculino();
    }

    @Override
    public Set<AntiguedadesDatos> getAntiguedadesFemenino() {
        return antiguedadesRepository.getAntiguedadesFemenino();
    }

    @Override
    public void generarExcel(String filePath, String nombre, int genero, Integer codTipoDocumento) throws IOException, DataException {
        String[] HEADERs = {"Codigo", "id", "Cedula", "Nombre", "Apellido"};
        try {
            ExcelHelper.generarExcel(obtenerDatos(genero), filePath, HEADERs);

            generaDocumento(filePath, nombre);

        } catch (IOException ex) {
            System.out.println("error: " + ex.getMessage());
        }

    }

    @Override
    public void generarPDF(HttpServletResponse response, String filePath, String nombre, int genero, Integer codTipoDocumento)
            throws DocumentException, IOException, DataException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Cuerpo-Bomberos";
        String valor = "attachment; filename=Datos" + fechaActual + ".pdf";

        response.addHeader(cabecera, valor);

        ExporterPdf exporter = new ExporterPdf();
        String[] columnas = {"Codigo", "id", "Cedula", "Nombre", "Apellido", "Nota"};
        float[] widths = new float[]{2f, 3f, 6f, 6f, 2.5f, 2.5f};

        //Genera el pdf
        exporter.setArchivosRuta(ARCHIVOS_RUTA);
        exporter.exportar(response, columnas, obtenerDatos(genero), widths, filePath);

        generaDocumento(filePath, nombre);

    }

    public ArrayList<ArrayList<String>> obtenerDatos(int genero) {
        Set<AntiguedadesDatos> datos = new HashSet<>();
        if (genero == 1) {
            datos = antiguedadesRepository.getAntiguedadesFemenino();
        } else if (genero == 0) {
            datos = antiguedadesRepository.getAntiguedadesMasculino();
        }

        return entityToArrayList(datos);
    }

    public ArrayList<ArrayList<String>> obtenerDatos() {
        Set<AntiguedadesFormacion> datos = new HashSet<>();

        datos = antiguedadesFormacionRepository.getAntiguedadesFormacion();

        return entityToArrayListAntiguedades(datos);
    }

    public ArrayList<ArrayList<String>> obtenerDatosEsp(Long codCurso) {
        Set<AntiguedadesFormacion> datos = new HashSet<>();

        datos = antiguedadesFormacionRepository.getAntiguedadesEspecializacion(codCurso);

        return entityToArrayListAntiguedades(datos);
    }

    public ArrayList<ArrayList<String>> obtenerDatosReprobadosEsp(Long codCurso) {
        Set<AntiguedadesFormacion> datos = new HashSet<>();

        datos = antiguedadesFormacionRepository.getReprobadosEspecializacion(codCurso);

        return entityToArrayListAntiguedades(datos);
    }

    public static String[] entityToStringArray(AntiguedadesDatos entity) {
        return new String[]{entity.getCodPostulante().toString(), entity.getIdPostulante().toString(),
                entity.getCedula(), entity.getNombre(), entity.getApellido(),
                entity.getNotaPromedioFinal().toString()};
    }

    public static ArrayList<ArrayList<String>> entityToArrayList(Set<AntiguedadesDatos> datos) {
        ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
        for (AntiguedadesDatos dato : datos) {

            arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArray(dato))));
        }
        return arrayMulti;
    }

    public static String[] entityToStringArrayAntiguedades(AntiguedadesFormacion entity) {
        return new String[]{entity.getCodigoUnicoEstudiante(), entity.getCedula(),
                entity.getNombre(), entity.getApellido(), entity.getCorreoPersonal(),
                entity.getNotaFinal().toString()};
    }

    public static ArrayList<ArrayList<String>> entityToArrayListAntiguedades(Set<AntiguedadesFormacion> datos) {
        ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
        for (AntiguedadesFormacion dato : datos) {

            arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArrayAntiguedades(dato))));
        }
        return arrayMulti;
    }

    private void generaDocumento(String ruta, String nombre) {
        Documento documento = new Documento();
        Optional<Documento> documento2 = documentoRepo.findByNombre(nombre);
        if (documento2.isPresent()) {
            documento = documento2.get();
        }
        documento.setEstado(ACTIVO);
        documento.setNombre(nombre);
        documento.setRuta(ruta);

        documento = documentoRepo.save(documento);

        PeriodoAcademicoDocumentoFor doc = new PeriodoAcademicoDocumentoFor();
        doc.setCodPeriodoAcademico(periodoAcademicoRepository.getPAActive());
        ;
        doc.setCodDocumento(documento.getCodDocumento());
        periodoAcademicoDocForRepository.save(doc);

        // System.out.println("documento.getCodigo(): " + documento.getCodigo());
    }

    @Override
    public Set<AntiguedadesFormacion> getAntiguedadesFormacion() {
        // TODO Auto-generated method stub
        return antiguedadesFormacionRepository.getAntiguedadesFormacion();
    }

    @Override
    public Set<AntiguedadesFormacion> getAntiguedadesEspecializacion(Long codCurso) {
        return antiguedadesFormacionRepository.getAntiguedadesEspecializacion(codCurso);
    }

    @Override
    public void generarExcel(String filePath, String nombre)
            throws IOException, DataException {
        String[] HEADERs = {"Codigo Unico", "Cedula", "Nombre", "Apellido", "Correo", "Nota"};
        try {
            ExcelHelper.generarExcel(obtenerDatos(), filePath, HEADERs);

            generaDocumento(filePath, nombre);

        } catch (IOException ex) {
            System.out.println("error: " + ex.getMessage());
        }

    }

    @Override
    public void generarPDF(HttpServletResponse response, String filePath, String nombre)
            throws DocumentException, IOException, DataException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Cuerpo-Bomberos";
        String valor = "attachment; filename=Datos" + fechaActual + ".pdf";

        response.addHeader(cabecera, valor);

        ExporterPdf exporter = new ExporterPdf();
        String[] columnas = {"Codigo Unico", "Cedula", "Nombre", "Apellido", "Correo", "Nota"};
        float[] widths = new float[]{2f, 2f, 6f, 6f, 2.5f, 2f};

        //Genera el pdf
        exporter.setArchivosRuta(ARCHIVOS_RUTA);
        exporter.exportar(response, columnas, obtenerDatos(), widths, filePath);

        generaDocumento(filePath, nombre);



    }

    @Override
    public void generarExcelEsp(String filePath, String nombre, Long codCurso) throws IOException, DataException {
        this.generarExcelEspGeneral(filePath, nombre, codCurso, true);
    }

    @Override
    public void generarPDFEsp(HttpServletResponse response, String filePath, String nombre, Long codCurso) throws DocumentException, IOException, DataException {
        this.generarPDFEspGeneral(response, filePath, nombre, codCurso, true);
    }

    @Override
    public void generarExcelReprobadosEsp(String filePath, String nombre, Long codCurso) throws IOException, DataException {
        this.generarExcelEspGeneral(filePath, nombre, codCurso, false);
    }

    @Override
    public void generarPDFReprobadosEsp(HttpServletResponse response, String filePath, String nombre, Long codCurso) throws DocumentException, IOException, DataException {
        this.generarPDFEspGeneral(response, filePath, nombre, codCurso, false);
    }

    @Override
    public void generarExcelEspGeneral(String filePath, String nombre, Long codCurso, Boolean aprobados) throws IOException, DataException {
        String[] HEADERs = {"Codigo Unico", "Cedula", "Nombre", "Apellido", "Correo", "Nota"};
        try {
            if (aprobados) {
                ExcelHelper.generarExcel(obtenerDatosEsp(codCurso), filePath + "/" + nombre, HEADERs);
            } else {
                ExcelHelper.generarExcel(obtenerDatosReprobadosEsp(codCurso), filePath + "/" + nombre, HEADERs);
            }
            cursoDocumentoSvc.generaDocumento(filePath, nombre, codCurso);

        } catch (IOException ex) {
            System.out.println("error: " + ex.getMessage());
        }
    }

    @Override
    public void generarPDFEspGeneral(HttpServletResponse response, String filePath, String nombre, Long codCurso, Boolean aprobados) throws DocumentException, IOException, DataException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Cuerpo-Bomberos";
        String valor = "attachment; filename=Datos" + fechaActual + ".pdf";

        response.addHeader(cabecera, valor);

        ExporterPdf exporter = new ExporterPdf();
        String[] columnas = {"Codigo Unico", "Cedula", "Nombre", "Apellido", "Correo", "Nota"};
        float[] widths = new float[]{2f, 2f, 6f, 6f, 2.5f, 2f};

        //Genera el pdf
        exporter.setArchivosRuta(ARCHIVOS_RUTA);
        if (aprobados)
            exporter.exportar(response, columnas, obtenerDatosEsp(codCurso), widths, filePath + "/" + nombre);
        else
            exporter.exportar(response, columnas, obtenerDatosReprobadosEsp(codCurso), widths, filePath + "/" + nombre);
        cursoDocumentoSvc.generaDocumento(filePath, nombre, codCurso);

    }

    @Override
    public void notificarReprobados(Long codCurso) throws DataException {

        Parametro parametro = parametroRepository.findByNombreParametro("especializacion.reprobacion.curso")
                .orElseThrow(() -> new BusinessException(NO_PARAMETRO));
        Set<AntiguedadesFormacion> aprobados;
        // llama a procedimiento cbdmq.get_approved_by_test_esp(p_sub_tipo_prueba bigint, p_cod_curso bigint)
        aprobados = antiguedadesFormacionRepository.getReprobadosEspecializacion(codCurso);
        Curso curso= cursoSc.getById(codCurso);
        CatalogoCurso catalogoCurso= catalogoCursoRepository.findById(curso.getCodCatalogoCursos().intValue()).get();
        TipoCurso tipoCurso= tipoCursoRepository.findById(catalogoCurso.getCodTipoCurso().longValue()).get();
        String mensajeCurso=curso.getNombre() +"-"+catalogoCurso.getNombreCatalogoCurso()+" de tipo "+tipoCurso.getNombreTipoCurso();


        StringBuilder errorMessageBuilder = new StringBuilder();

        for (AntiguedadesFormacion resultadosPruebasDatos : aprobados) {
            DatoPersonal dato;

            // si es curso, obtiene dato personal asociado a estudiante

            dato = dpSvc.getByCedula(resultadosPruebasDatos.getCedula()).get();


            if (dato == null) {
                throw new DataException("No existe un dato personal asociado");
            }

            try {
                String nombres = dato.getNombre() + " " + dato.getApellido();
                String cuerpoHtml = String.format(parametro.getValor(), nombres,mensajeCurso);
                String[] destinatarios = {resultadosPruebasDatos.getCorreoPersonal()};
                emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT_CURSO_REPROBADO, cuerpoHtml);

            } catch (Exception e) {
                String errorMessage = e.getMessage();
                errorMessageBuilder.append(errorMessage).append("\n");
            }
        }

    }

    @Override
    public void notificarAprobados(Long codCurso) throws DataException {
        Parametro parametro = parametroRepository.findByNombreParametro("especializacion.aprobacion.curso")
                .orElseThrow(() -> new BusinessException(NO_PARAMETRO));
        Set<AntiguedadesFormacion> aprobados;
        // llama a procedimiento cbdmq.get_approved_by_test_esp(p_sub_tipo_prueba bigint, p_cod_curso bigint)
        aprobados = antiguedadesFormacionRepository.getReprobadosEspecializacion(codCurso);
        Curso curso= cursoSc.getById(codCurso);
        CatalogoCurso catalogoCurso= catalogoCursoRepository.findById(curso.getCodCatalogoCursos().intValue()).get();
        TipoCurso tipoCurso= tipoCursoRepository.findById(catalogoCurso.getCodTipoCurso().longValue()).get();
        String mensajeCurso=curso.getNombre() +"-"+catalogoCurso.getNombreCatalogoCurso()+" de tipo "+tipoCurso.getNombreTipoCurso();


        StringBuilder errorMessageBuilder = new StringBuilder();

        for (AntiguedadesFormacion resultadosPruebasDatos : aprobados) {
            DatoPersonal dato;


            dato = dpSvc.getByCedula(resultadosPruebasDatos.getCedula()).get();


            if (dato == null) {
                throw new DataException("No existe un dato personal asociado");
            }

            try {
                String nombres = dato.getNombre() + " " + dato.getApellido();
                String cuerpoHtml = String.format(parametro.getValor(), nombres,mensajeCurso);
                String[] destinatarios = {resultadosPruebasDatos.getCorreoPersonal()};
                emailService.enviarEmailHtml(destinatarios, EMAIL_SUBJECT_CURSO_APROBADO, cuerpoHtml);

            } catch (Exception e) {
                String errorMessage = e.getMessage();
                errorMessageBuilder.append(errorMessage).append("\n");
            }
        }
    }

}

