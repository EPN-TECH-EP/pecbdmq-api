package epntech.cbdmq.pe.servicio.impl;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.constante.EspecializacionConst;
import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.constante.FormacionConst;
import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import epntech.cbdmq.pe.dominio.util.DatosInscripcionEsp;
import epntech.cbdmq.pe.dominio.util.InscritosValidos;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasFisicasDatos;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.*;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.PruebasRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.ResultadoPruebasTodoRepository;
import epntech.cbdmq.pe.servicio.PruebaDetalleService;
import epntech.cbdmq.pe.servicio.ResultadoPruebaService;
import epntech.cbdmq.pe.servicio.especializacion.CursoDocumentoService;
import epntech.cbdmq.pe.servicio.especializacion.InscripcionEspService;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_RESULTADO_PRUEBAS;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_RESULTADO_PRUEBAS_CURSO;
import static epntech.cbdmq.pe.constante.EspecializacionConst.CURSO_NO_PRUEBAS;
import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class ResultadoPruebaServiceImpl implements ResultadoPruebaService {


    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ResultadoPruebasFisicasDatosRepository repo1;
    @Autowired
    private DocumentoRepository documentoRepo;
    @Autowired
    private DocumentoPruebaRepository docPruebaRepo;
    @Autowired
    private PruebaDetalleRepository pruebaDetalleRepository;
    @Autowired
    ResultadoPruebaRepository repo;
    @Autowired
    private ResultadoPruebasTodoRepository repo2;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;
    @Autowired
    private PeriodoAcademicoRepository periodoAcademicoRepository;
    @Autowired
    private PeriodoAcademicoDocForRepository periodoAcademicoDocForRepository;

    // cursoDocumento repo
    @Autowired
    private CursoDocumentoRepository cursoDocumentoRepository;

    // cursos
    @Autowired
    private PruebasRepository pruebasRepository;
    @Autowired
    private InscripcionEspService inscripcionEspService;
    @Autowired
    CursoDocumentoService cursoDocumentoSvc;

    @Override
    public ResultadoPrueba save(ResultadoPrueba obj) throws DataException {
        if (obj.getCumplePrueba().trim().isEmpty())
            throw new DataException(REGISTRO_VACIO);
        Optional<ResultadoPrueba> objGuardado = repo.findByCumplePrueba(obj.getCumplePrueba());
        if (objGuardado.isPresent()) {

            // valida si existe eliminado
            ResultadoPrueba stp = objGuardado.get();
            if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
                stp.setEstado(EstadosConst.ACTIVO);
                return repo.save(stp);
            } else {
                throw new DataException(REGISTRO_YA_EXISTE);
            }

        }
		/*if(obj.get()>=0.8) {
			throw new DataException(CURSO_APROBADO);
		}else {
			throw new DataException(CURSO_REPROBADO);
		}*/
        return repo.save(obj);
    }

    @Override
    public List<ResultadoPrueba> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<ResultadoPrueba> getById(Integer codigo) {
        return repo.findById(codigo);
    }

    @Override
    public ResultadoPrueba update(ResultadoPrueba objActualizado) throws DataException {
        return repo.save(objActualizado);
    }

    @Override
    public void delete(Integer codigo) {
        repo.deleteById(codigo);
    }

    @Override
    public Boolean generarArchivo(HttpServletResponse response, Integer codSubtipoPrueba, Integer codCurso, Boolean esAprobado) throws DataException, DocumentException, IOException {
        String[] columnas = {"Código Único", "Correo", "Cedula", "Nombre", "Apellido"};

        Optional<PruebaDetalle> pp = null;
        String ruta = "";
        String nombreArchivo = "";
        Integer codPruebaDetalle = null;
        String prefijo = esAprobado ? "Lista de aprobados " : "Lista de reprobados ";


        // si recibe curso como parametro, se genera el archivo de aprobados por curso
        if (codCurso != null) {

            ruta = ARCHIVOS_RUTA + PATH_RESULTADO_PRUEBAS_CURSO
                    + codCurso.toString()
                    + "/";
            if (codSubtipoPrueba != null) {

                PruebaDetalle pruebaDetalle = pruebaDetalleRepository
                        .findByCodCursoEspecializacionAndCodSubtipoPrueba(codCurso, codSubtipoPrueba)
                        .orElseThrow(() -> new BusinessException(CURSO_NO_PRUEBAS));

                codPruebaDetalle = pruebaDetalle.getCodPruebaDetalle();

                String detallePrueba = pruebaDetalle.getDescripcionPrueba() != null ? pruebaDetalle.getDescripcionPrueba() : pruebaDetalle.getCodPruebaDetalle().toString();
                nombreArchivo = prefijo + detallePrueba + " " + codCurso;

            } else {
                nombreArchivo = prefijo+ codCurso;
            }
        } else {

            ruta = ARCHIVOS_RUTA + PATH_RESULTADO_PRUEBAS
                    + periodoAcademicoRepository.getPAActive().toString()
                    + "/";
            String pActive = periodoAcademicoRepository.getPAActive().toString();


            pp = pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(codSubtipoPrueba,
                    periodoAcademicoRepository.getPAActive());

            codPruebaDetalle = pp.get().getCodPruebaDetalle();
            String detallePrueba = pp.get().getDescripcionPrueba() + " " + pActive;
            nombreArchivo = prefijo + detallePrueba;
        }
        String nombre1 = nombreArchivo + ".pdf";
        String nombre2 = nombreArchivo + ".xlsx";
        if (esAprobado) {
            this.generarPDF(response, ruta + nombre1, nombre1, codSubtipoPrueba, columnas, codCurso, codPruebaDetalle);
            this.generarExcel(ruta + nombre2, nombre2, codSubtipoPrueba, columnas, codCurso, codPruebaDetalle);
        } else {
            this.generarPDFReprobados(response, ruta + nombre1, nombre1, codSubtipoPrueba, columnas, codCurso, codPruebaDetalle);
            this.generarExcelReprobados(ruta + nombre2, nombre2, codSubtipoPrueba, columnas, codCurso, codPruebaDetalle);
        }

        return true;

    }

    @Override
    public Boolean generarArchivoAprobados(HttpServletResponse response, Integer codSubtipoPrueba, Integer codCurso) throws DataException, DocumentException, IOException {
        return generarArchivo(response, codSubtipoPrueba, codCurso, true);
    }

    @Override
    public Boolean generarArchivoReprobados(HttpServletResponse response, Integer codSubtipoPrueba, Integer codCurso) throws DataException, DocumentException, IOException {
        return generarArchivo(response, codSubtipoPrueba, codCurso, false);
    }


    public void generarPDF(HttpServletResponse response, String ruta, String nombre, Integer subTipoPrueba, String[] headers, Integer codCurso, Integer codPruebaDetalle, Boolean esAprobado)
            throws DocumentException, IOException, DataException {


        //TODO el response no tiene ninguna funcionalidad
        /*
                response.setContentType("application/pdf");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String fechaActual = dateFormatter.format(new Date());
                String cabecera = "Cuerpo-Bomberos";
                String valor = "attachment; filename=Datos" + fechaActual + ".pdf";
                response.addHeader(cabecera, valor);

         */
        ExporterPdf exporter = new ExporterPdf();
        //anchos de las columnas
        float[] widths = new float[]{2.5f, 2.5f, 2.5f, 2.5f, 2.5f};

        //Genera el pdf
        exporter.setArchivosRuta(ARCHIVOS_RUTA);
        if(esAprobado) {
            exporter.exportar(response, headers, obtenerDatosEsp(subTipoPrueba, codCurso), widths, ruta);
        }else{
            exporter.exportar(response, headers, obtenerDatosEspDesaprobados(subTipoPrueba, codCurso), widths, ruta);
        }
        generaDocumento(ruta, nombre, subTipoPrueba, codCurso);


    }

    public void generarExcel(String ruta, String nombre, Integer subTipoPrueba, String[] headers, Integer codCurso, Integer codPruebaDetalle, Boolean esAprobado) throws IOException, DataException {
        // Optional<Prueba> pp = pruebaRepository.findById(prueba);


        if (esAprobado){
            ExcelHelper.generarExcel(obtenerDatosEsp(subTipoPrueba, codCurso), ruta, headers);
        }else {
            ExcelHelper.generarExcel(obtenerDatosEspDesaprobados(subTipoPrueba, codCurso), ruta, headers);
        }
        generaDocumento(ruta, nombre, subTipoPrueba, codCurso);

    }   public void generarPDFReprobados(HttpServletResponse response, String ruta, String nombre, Integer subTipoPrueba, String[] headers, Integer codCurso, Integer codPruebaDetalle)
            throws DocumentException, IOException, DataException {


        //TODO el response no tiene ninguna funcionalidad
        /*
                response.setContentType("application/pdf");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String fechaActual = dateFormatter.format(new Date());
                String cabecera = "Cuerpo-Bomberos";
                String valor = "attachment; filename=Datos" + fechaActual + ".pdf";
                response.addHeader(cabecera, valor);

         */
        ExporterPdf exporter = new ExporterPdf();
        //anchos de las columnas
        float[] widths = new float[]{2.5f, 2.5f, 2.5f, 2.5f, 2.5f};

        //Genera el pdf
        exporter.setArchivosRuta(ARCHIVOS_RUTA);
        exporter.exportar(response, headers, obtenerDatosEsp(subTipoPrueba, codCurso), widths, ruta);
        generaDocumento(ruta, nombre, subTipoPrueba, codCurso);


    }

    public void generarExcelReprobados(String ruta, String nombre, Integer subTipoPrueba, String[] headers, Integer codCurso, Integer codPruebaDetalle) throws IOException, DataException {
        // Optional<Prueba> pp = pruebaRepository.findById(prueba);


        ExcelHelper.generarExcel(obtenerDatosEsp(subTipoPrueba, codCurso), ruta, headers);
        generaDocumento(ruta, nombre, subTipoPrueba, codCurso);

    }

    //obtener resultados dto por una prueba detalle, y pasarlo a una array de registros
    public ArrayList<ArrayList<String>> obtenerDatosII(Integer prueba) {
        List<ResultadosPruebasFisicasDatos> datos = repo1.getResultados(prueba);
        return entityToArrayListII(datos);
    }

    //pasar a array un registro
    public static String[] entityToStringArrayII(ResultadosPruebasFisicasDatos entity) {
        return new String[]{
                entity.getIdPostulante().toString(),
                entity.getCedula(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getResultado().toString(),
                entity.getResultadoTiempo() != null ? entity.getResultadoTiempo().toString() : "",
                entity.getNotaPromedioFinal().toString()};
    }

    //Para pasar a arrays lista de registros
    public static ArrayList<ArrayList<String>> entityToArrayListII(List<ResultadosPruebasFisicasDatos> datos) {
        ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();

        for (ResultadosPruebasFisicasDatos dato : datos) {
            arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArrayII(dato))));
        }
        return arrayMulti;
    }

    public ArrayList<ArrayList<String>> obtenerDatos(Integer prueba, Integer codCurso) {

        List<ResultadosPruebasDatos> datos;

        if (codCurso != null) {
            // llama a procedimiento cbdmq.get_approved_by_test_esp(p_sub_tipo_prueba bigint, p_cod_curso bigint)
            //TODO
            datos = pruebasRepository.get_approved_by_test_esp(prueba.longValue(), codCurso.longValue());
        } else {
            datos = repo2.get_approved_applicants(prueba);
        }

        return entityToArrayList(datos);
    }

    public ArrayList<ArrayList<String>> obtenerDatosEsp(Integer prueba, Integer codCurso) throws DataException {

        List<DatosInscripcionEsp> datos;

        if (prueba == null) {
            // llama a procedimiento cbdmq.get_approved_by_test_esp(p_sub_tipo_prueba bigint, p_cod_curso bigint)
            //TODO
            datos = inscripcionEspService.getAprobadosPruebas(codCurso);
        } else {
            datos = inscripcionEspService.getAprobadosPruebasSubtipoPrueba(codCurso, prueba);
        }

        return entityToArrayListEsp(datos);
    }
    public ArrayList<ArrayList<String>> obtenerDatosEspDesaprobados(Integer prueba, Integer codCurso) throws DataException {

        List<DatosInscripcionEsp> datos;

        if (prueba == null) {
            // llama a procedimiento cbdmq.get_approved_by_test_esp(p_sub_tipo_prueba bigint, p_cod_curso bigint)
            //TODO
            datos = inscripcionEspService.getDesAprobadosPruebas(codCurso);
        } else {
            datos = null;
        }

        return entityToArrayListEsp(datos);
    }

    public static String[] entityToStringArray(ResultadosPruebasDatos entity) {
        return new String[]{entity.getIdPostulante() != null ? entity.getIdPostulante().toString() : ""
        };
    }

    public static String[] entityToStringArrayEsp(DatosInscripcionEsp entity) {
        return new String[]{entity.getCodUnicoEstudiante().toString(), entity.getCorreoPersonal(),
                entity.getCedula(), entity.getNombre(),
                entity.getApellido()};
    }

    public static ArrayList<ArrayList<String>> entityToArrayList(List<ResultadosPruebasDatos> datos) {
        ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
        for (ResultadosPruebasDatos dato : datos) {

            arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArray(dato))));
        }
        return arrayMulti;
    }

    public static ArrayList<ArrayList<String>> entityToArrayListEsp(List<DatosInscripcionEsp> datos) {
        ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
        for (DatosInscripcionEsp dato : datos) {

            arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArrayEsp(dato))));
        }
        return arrayMulti;
    }


    private void generaDocumentoII(String ruta, String nombre, Integer prueba) {
        Documento documento = new Documento();
        documento.setEstado("ACTIVO");
        documento.setNombre(nombre);
        documento.setRuta(ruta);

        documento = documentoRepo.save(documento);

        DocumentoPrueba doc = new DocumentoPrueba();
        doc.setCodPruebaDetalle(prueba);
        doc.setCodDocumento(documento.getCodDocumento());
        // System.out.println("documento.getCodigo(): " + documento.getCodigo());

        saveDocumentoPrueba(doc);
    }

    @Transactional
    public void generaDocumento(String ruta, String nombre, Integer codSubtipo, Integer codCurso) throws DataException {


        Optional<PruebaDetalle> pruebaDetalleOpt = pruebaDetalleRepository.findByCodCursoEspecializacionAndCodSubtipoPrueba(
                codCurso,
                codSubtipo);
        Integer codPruebaDetalle = null;

        if (pruebaDetalleOpt.isPresent()) {
            codPruebaDetalle = pruebaDetalleOpt.get().getCodPruebaDetalle();
        }

        // busca documentos para la prueba

        List<DocumentoPrueba> listaDocPrueba = this.docPruebaRepo.findAllByCodPruebaDetalle(codPruebaDetalle);

        if (listaDocPrueba != null && listaDocPrueba.size() > 0) {

            // busca si existe un documento con el mismo nombre para la prueba
            List<Documento> docs = this.documentoRepo.findAllByNombre(nombre);

            // si hay documentos con el mismo nombre, busca el que corresponda a esa prueba
            // y ese periodo de formación o curso
            if (docs != null && docs.size() > 0) {

                List<Integer> listaCodDocumentoPrueba = listaDocPrueba.stream()
                        .map(DocumentoPrueba::getCodDocumento)
                        .collect(Collectors.toList());

                List<Integer> listaCodDocumento = docs.stream()
                        .map(Documento::getCodDocumento)
                        .collect(Collectors.toList());

                // intersección de las listas
                Set<Integer> resultado = listaCodDocumentoPrueba.stream()
                        .distinct()
                        .filter(listaCodDocumento::contains)
                        .collect(Collectors.toSet());

                if (resultado != null && resultado.size() > 0) {

                    final Integer codPrueba = codPruebaDetalle;

                    resultado.stream()
                            .forEach(codDoc -> {
                                // elimina de documentoPrueba

                                try {

                                    // si es curso, elimina del repo del curso, caso contrario elimina del repo de formación
                                    if (codCurso != null) {
                                        this.cursoDocumentoRepository.deleteByCodCursoEspecializacionAndCodDocumento(codCurso.longValue(), codDoc.longValue());
                                    } else {
                                        this.periodoAcademicoDocForRepository.deleteByCodPeriodoAcademicoAndCodDocumento(periodoAcademicoRepository.getPAActive(), codDoc);
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                this.docPruebaRepo.deleteByCodPruebaDetalleAndCodDocumento(codPrueba, codDoc);
                                this.documentoRepo.deleteById(codDoc);
                            });

                }

            }

        }

        // genera documento
        Documento documento = new Documento();
        documento.setEstado("ACTIVO");
        documento.setNombre(nombre);
        documento.setRuta(ruta);

        // si el nombre contiene la palabara aprobados, se coloca APROBADOS en campo descripción de documento
        if (nombre.toLowerCase().contains("aprobados")) {
            documento.setDescripcion("APROBADOS");
        }

        documento = documentoRepo.save(documento);

        // si es curso, guarda en repo del curso, caso contrario guarda en repo de formación
        if (codCurso != null) {
            CursoDocumento cursoDoc = new CursoDocumento();
            cursoDoc.setCodCursoEspecializacion(codCurso.longValue());
            cursoDoc.setCodDocumento(documento.getCodDocumento().longValue());
            cursoDocumentoRepository.save(cursoDoc);
        } else {
            PeriodoAcademicoDocumentoFor docPA = new PeriodoAcademicoDocumentoFor();
            docPA.setCodPeriodoAcademico(periodoAcademicoRepository.getPAActive());
            ;
            docPA.setCodDocumento(documento.getCodDocumento());
            periodoAcademicoDocForRepository.save(docPA);
        }
        if(codPruebaDetalle != null) {
            DocumentoPrueba doc = new DocumentoPrueba();
            doc.setCodPruebaDetalle(codPruebaDetalle);
            doc.setCodDocumento(documento.getCodDocumento());
            saveDocumentoPrueba(doc);
        }
    }

    private void saveDocumentoPrueba(DocumentoPrueba obj) {
        docPruebaRepo.save(obj);
    }

}
