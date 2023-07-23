package epntech.cbdmq.pe.servicio.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.constante.FormacionConst;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoPrueba;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasFisicasDatos;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.*;
import epntech.cbdmq.pe.servicio.PostulanteService;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.ResultadoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.ResultadoPruebaService;

import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_RESULTADO_PRUEBAS;
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
    private ResultadoPruebasDatosRepository repo2;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;
    @Autowired
    private PeriodoAcademicoRepository periodoAcademicoRepository;


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
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    @Override
    public Optional<ResultadoPrueba> getById(Integer codigo) {
        // TODO Auto-generated method stub
        return repo.findById(codigo);
    }

    @Override
    public ResultadoPrueba update(ResultadoPrueba objActualizado) throws DataException {
        // TODO Auto-generated method stub
        return repo.save(objActualizado);
    }

    @Override
    public void delete(Integer codigo) {
        // TODO Auto-generated method stub
        repo.deleteById(codigo);
    }

    @Override
    public Boolean generarArchivoAprobados(HttpServletResponse response,Integer codSubtipoPrueba) throws DataException, DocumentException, IOException {
        String[] columnas = {"Id postulante"};

        String ruta = ARCHIVOS_RUTA + PATH_RESULTADO_PRUEBAS
                + periodoAcademicoRepository.getPAActive().toString()
                + "/";
        String pActive = periodoAcademicoRepository.getPAActive().toString();


        Optional<PruebaDetalle> pp = pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(codSubtipoPrueba,
                periodoAcademicoRepository.getPAActive());

        if (pp.get().getEstado().equalsIgnoreCase(EstadosConst.PRUEBAS_CIERRE)) {
            throw new DataException(ESTADO_INVALIDO);
        } else {
            String nombreArchivo = "Lista de aprobados "+pp.get().getDescripcionPrueba()+pActive;
            String nombre1=nombreArchivo+".pdf";
            String nombre2=nombreArchivo+".xlsx";

            this.generarPDF(response, ruta+nombre1, nombre1, codSubtipoPrueba, columnas);
            this.generarExcel(ruta+nombre2, nombre2, codSubtipoPrueba,columnas);
        }


        return true;
    }

    public void generarPDF(HttpServletResponse response, String ruta, String nombre, Integer subTipoPrueba, String[] headers)
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
                float[] widths = new float[]{2.5f};

                //Genera el pdf
                exporter.exportar(response, headers, obtenerDatos(subTipoPrueba), widths, ruta);

                generaDocumento(ruta, nombre, subTipoPrueba);

    }

    public void generarExcel(String ruta,String nombre, Integer subTipoPrueba, String[] headers) throws IOException, DataException {
        // Optional<Prueba> pp = pruebaRepository.findById(prueba);


                ExcelHelper.generarExcel(obtenerDatos(subTipoPrueba), ruta, headers);

                generaDocumento(ruta, nombre, subTipoPrueba);

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

    public ArrayList<ArrayList<String>> obtenerDatos(Integer prueba) {
        List<ResultadosPruebasDatos> datos = repo2.get_approved_applicants(prueba);
        return entityToArrayList(datos);
    }

    public static String[] entityToStringArray(ResultadosPruebasDatos entity) {
        return new String[]{entity.getIdPostulante() != null ? entity.getIdPostulante().toString() : ""
        };
    }

    public static String[] entityToStringArrayII(ResultadosPruebasDatos entity) {
        return new String[]{entity.getCodPostulante().toString(), entity.getIdPostulante().toString(),
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
    private void generaDocumento(String ruta, String nombre, Integer prueba) throws DataException {

        // busca la pruebaDetalle. Si no encuentra hay un error de consistencia de datos
        Integer codPruebaDetalle = null;


        Optional<PruebaDetalle> pruebaDetalleOpt = pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(
                prueba,
                periodoAcademicoRepository.getPAActive());

        if (pruebaDetalleOpt.isPresent()) {
            codPruebaDetalle = pruebaDetalleOpt.get().getCodPruebaDetalle();
        } else {
            throw new DataException(FormacionConst.PRUEBA_NO_EXISTE);
        }

        // busca documentos para la prueba
        List<DocumentoPrueba> listaDocPrueba = this.docPruebaRepo.findAllByCodPruebaDetalle(codPruebaDetalle);
        if (listaDocPrueba != null && listaDocPrueba.size() > 0) {

            // busca si existe un documento con el mismo nombre para la prueba
            List<Documento> docs = this.documentoRepo.findAllByNombre(nombre);

            // si hay documentos con el mismo nombre, busca el que corresponda a esa prueba
            // y ese periodo de formación
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

        documento = documentoRepo.save(documento);

        DocumentoPrueba doc = new DocumentoPrueba();
        doc.setCodPruebaDetalle(codPruebaDetalle);
        doc.setCodDocumento(documento.getCodDocumento());
        // System.out.println("documento.getCodigo(): " + documento.getCodigo());

        saveDocumentoPrueba(doc);
    }

    private void saveDocumentoPrueba(DocumentoPrueba obj) {
        docPruebaRepo.save(obj);
    }

}
