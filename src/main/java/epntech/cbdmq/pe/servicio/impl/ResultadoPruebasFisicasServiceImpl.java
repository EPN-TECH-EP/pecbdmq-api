package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;
import static epntech.cbdmq.pe.constante.MensajesConst.ESTADO_INVALIDO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoPrueba;
import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.admin.Prueba;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.admin.ResultadoPruebasFisicas;
import epntech.cbdmq.pe.dominio.util.ResultadoPruebaFisicaUtil;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasFisicasDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.helper.ResultadoPruebasHelper;
import epntech.cbdmq.pe.repositorio.admin.DocumentoPruebaRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaRepository;
import epntech.cbdmq.pe.repositorio.admin.ResultadoPruebasFisicasDatosRepository;
import epntech.cbdmq.pe.repositorio.admin.ResultadoPruebasFisicasRepository;
import epntech.cbdmq.pe.servicio.PostulanteService;
import epntech.cbdmq.pe.servicio.ResultadoPruebasFisicasService;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import epntech.cbdmq.pe.dominio.util.ResultadoPruebaFisicaUtil;

@Service
public class ResultadoPruebasFisicasServiceImpl implements ResultadoPruebasFisicasService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ResultadoPruebasFisicasRepository repo;
    @Autowired
    private PruebaRepository pruebaRepository;
    @Autowired
    private ResultadoPruebasFisicasDatosRepository repo1;
    @Autowired
    private DocumentoRepository documentoRepo;
    @Autowired
    private DocumentoPruebaRepository docPruebaRepo;
    @Autowired
    private PruebaDetalleRepository pruebaDetalleRepository;
    @Autowired
    private PostulanteService postulanteService;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;
    @Autowired
    private PeriodoAcademicoRepository periodoAcademicoRepository;

    @Override
    public void insertAll(List<ResultadoPruebasFisicas> obj) {
        repo.saveAll(obj);

    }

    @Override
    public ResultadoPruebasFisicas update(ResultadoPruebasFisicas objActualizado) {
        ResultadoPruebasFisicas resultadoPruebas = new ResultadoPruebasFisicas();
        Optional<Prueba> prueba = pruebaRepository.findById(objActualizado.getCodPruebaDetalle());
        if (prueba.isPresent()) {
            resultadoPruebas = repo.save(objActualizado);

            Prueba p = new Prueba();
            p = prueba.get();
            p.setEstado("REGISTRO");

            pruebaRepository.save(p);
        }

        return resultadoPruebas;
    }

    @Override
    public void uploadFile(MultipartFile file, Integer codPruebaDetalle, Integer codFuncionario, String tipoResultado) throws DataException {
        try {


            List<ResultadoPruebaFisicaUtil> datosUtil = ResultadoPruebasHelper
                    .excelToDatosPruebasFisicasI(file.getInputStream(), tipoResultado);
            // Crear el HashMap para almacenar el último registro para cada idPostulante
            Map<String, ResultadoPruebaFisicaUtil> ultimoResultadoPorPostulante = new HashMap<>();

            // Iterar sobre datosUtil y actualizar el HashMap solo con los últimos registros para cada idPostulante
            for (ResultadoPruebaFisicaUtil resultadoPruebaFisicaUtil : datosUtil) {
                ultimoResultadoPorPostulante.put(resultadoPruebaFisicaUtil.getIdPostulante(), resultadoPruebaFisicaUtil);
            }
            // Filtrar los valores del HashMap para obtener la lista deseada
            List<ResultadoPruebaFisicaUtil> datosFiltrados = new ArrayList<>(ultimoResultadoPorPostulante.values());


            LOGGER.info(tipoResultado);

            List<ResultadoPruebasFisicas> datos = datosFiltrados.stream().map(resultadoPruebaFisicaUtil -> {
                ResultadoPruebasFisicas resultadoPruebasFisicas = new ResultadoPruebasFisicas();
                Optional<Postulante> postulante = postulanteService.getByIdPostulante(resultadoPruebaFisicaUtil.getIdPostulante());
                if (postulante.isEmpty()) {
                    throw new RuntimeException("El postulante con el id: " + resultadoPruebaFisicaUtil.getIdPostulante() + " no existe");
                }

                // busca registro existente
                Optional<ResultadoPruebasFisicas> resultadoPruebasFisicasOpt = this.getByCodPostulanteAndCodPruebaDetalle(Integer.valueOf(postulante.get().getCodPostulante().intValue()), codPruebaDetalle);

                if (resultadoPruebasFisicasOpt.isEmpty()) {
                    resultadoPruebasFisicas = new ResultadoPruebasFisicas();
                } else {
                    resultadoPruebasFisicas = resultadoPruebasFisicasOpt.get();
                }


                resultadoPruebasFisicas.setCodPostulante(postulante.get().getCodPostulante().intValue());
                resultadoPruebasFisicas.setResultado(resultadoPruebaFisicaUtil.getResultado());
                resultadoPruebasFisicas.setResultadoTiempo(resultadoPruebaFisicaUtil.getResultadoTiempo());
                resultadoPruebasFisicas.setCodPruebaDetalle(codPruebaDetalle);
                resultadoPruebasFisicas.setCodFuncionario(codFuncionario);
                resultadoPruebasFisicas.setEstado("ACTIVO");
                return resultadoPruebasFisicas;

            }).collect(Collectors.toList());
            repo.saveAll(datos);
        } catch (IOException e) {
            throw new RuntimeException(FALLA_PROCESAR_EXCEL + " " + e.getMessage());
        }

    }

    @Override
    public ResultadoPruebasFisicas save(ResultadoPruebasFisicas obj) {
        // TODO Auto-generated method stub
        return repo.save(obj);
    }

    @Override
    public ByteArrayInputStream downloadFile() {

        ByteArrayInputStream in = ResultadoPruebasHelper.datosToExcel(null);
        return in;
    }

    @Override
    public void generarExcel(String nombre, Integer prueba) throws IOException, DataException {
        // Optional<Prueba> pp = pruebaRepository.findById(prueba);

        Optional<PruebaDetalle> pp = pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(prueba,
                periodoAcademicoRepository.getPAActive());
        if (pp.get().getEstado().equalsIgnoreCase(EstadosConst.PRUEBAS_CIERRE)) {
            throw new DataException(ESTADO_INVALIDO);
        } else {
            String ruta = ARCHIVOS_RUTA + PATH_RESULTADO_PRUEBAS + periodoAcademicoRepository.getPAActive().toString()
                    + "/" + nombre;
            String[] HEADERs = {"Codigo", "Cedula", "Nombre", "Apellido", "Resultado", "Resultado Tiempo",
                    "Nota Promedio"};
            try {
                ExcelHelper.generarExcel(obtenerDatos(prueba), ruta, HEADERs);

                generaDocumento(ruta, nombre, pp.get().getCodPruebaDetalle());

				/*
				PruebaDetalle p = new PruebaDetalle();
				p = pp.get();
				p.setEstado("CIERRE");

				pruebaDetalleRepository.save(p);*/

            } catch (IOException ex) {
                System.out.println("error: " + ex.getMessage());
            }
        }

    }

    @Override
    public List<ResultadosPruebasFisicasDatos> getResultados(Integer prueba) {
        // TODO Auto-generated method stub
        return repo1.getResultados(prueba);
    }

    @Override
    public void generarPDF(HttpServletResponse response, String nombre, Integer prueba)
            throws DocumentException, IOException, DataException {

        try {
            Optional<PruebaDetalle> pp = pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(prueba,
                    periodoAcademicoRepository.getPAActive());

            if (pp.get().getEstado().equalsIgnoreCase(EstadosConst.PRUEBAS_CIERRE)) {
                throw new DataException(ESTADO_INVALIDO);
            } else {
                String ruta = ARCHIVOS_RUTA + PATH_RESULTADO_PRUEBAS
                        + periodoAcademicoRepository.getPAActive().toString()
                        + "/" + nombre;
                String[] columnas = {"Codigo", "Cedula", "Nombre", "Apellido", "Resultado", "Resultado Tiempo", "Nota Promedio"};

                response.setContentType("application/pdf");

                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String fechaActual = dateFormatter.format(new Date());

                String cabecera = "Cuerpo-Bomberos";
                String valor = "attachment; filename=Datos" + fechaActual + ".pdf";

                response.addHeader(cabecera, valor);

                ExporterPdf exporter = new ExporterPdf();
                //TODO los anchos de las columnas
                float[] widths = new float[]{2.5f, 3.5f, 6f, 6f, 2.5f, 2.5f, 2.5f};

                //Genera el pdf
                exporter.setArchivosRuta(ARCHIVOS_RUTA);
                exporter.exportar(response, columnas, obtenerDatos(prueba), widths, ruta);

                generaDocumento(ruta, nombre, pp.get().getCodPruebaDetalle());

                PruebaDetalle p = new PruebaDetalle();
                p = pp.get();
                p.setEstado("CIERRE");

                pruebaDetalleRepository.save(p);
            }

        } catch (IOException ex) {
            System.out.println("error: " + ex.getMessage());
        }

    }

    public ArrayList<ArrayList<String>> obtenerDatos(Integer prueba) {
        List<ResultadosPruebasFisicasDatos> datos = repo1.getResultados(prueba);
        System.out.println("Array de datos para excel" + entityToArrayList(datos));
        return entityToArrayList(datos);
    }

    public static String[] entityToStringArray(ResultadosPruebasFisicasDatos entity) {
        return new String[]{
                entity.getIdPostulante().toString(),
                entity.getCedula(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getResultado().toString(),
                entity.getResultadoTiempo() != null ? entity.getResultadoTiempo().toString() : "",
                entity.getNotaPromedioFinal().toString()};
    }

    public static ArrayList<ArrayList<String>> entityToArrayList(List<ResultadosPruebasFisicasDatos> datos) {
        ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();

        for (ResultadosPruebasFisicasDatos dato : datos) {
            arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArray(dato))));
        }
        return arrayMulti;
    }

    private void generaDocumento(String ruta, String nombre, Integer prueba) {
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

    private void saveDocumentoPrueba(DocumentoPrueba obj) {
        docPruebaRepo.save(obj);
    }

    @Override
    public void notificar(String mensaje) throws MessagingException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<ResultadoPruebasFisicas> getByCodPostulanteAndCodPruebaDetalle(Integer CodPostulante, Integer codPrueba) {
        // TODO Auto-generated method stub
        return repo.findByCodPostulanteAndCodPruebaDetalle(CodPostulante, codPrueba);
    }

}
