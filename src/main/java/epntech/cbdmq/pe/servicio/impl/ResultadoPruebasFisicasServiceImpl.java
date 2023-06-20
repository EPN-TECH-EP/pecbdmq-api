package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.FALLA_PROCESAR_EXCEL;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_RESULTADO_PRUEBAS;
import static epntech.cbdmq.pe.constante.MensajesConst.ESTADO_INVALIDO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoPrueba;
import epntech.cbdmq.pe.dominio.admin.Prueba;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.admin.ResultadoPruebasFisicas;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasFisicasDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.helper.ResultadoPruebasHelper;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoPruebaRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaRepository;
import epntech.cbdmq.pe.repositorio.admin.ResultadoPruebasFisicasDatosRepository;
import epntech.cbdmq.pe.repositorio.admin.ResultadoPruebasFisicasRepository;
import epntech.cbdmq.pe.servicio.ResultadoPruebasFisicasService;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ResultadoPruebasFisicasServiceImpl implements ResultadoPruebasFisicasService {

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
		Optional<Prueba> prueba = pruebaRepository.findById(objActualizado.getCodPrueba());
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
	public void uploadFile(MultipartFile file) {
		try {

			List<ResultadoPruebasFisicas> datos = ResultadoPruebasHelper.excelToDatosPruebasFisicas(file.getInputStream());

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
	public void generarExcel(String filePath, String nombre, Integer prueba) throws IOException, DataException {
		//Optional<Prueba> pp = pruebaRepository.findById(prueba);
		
		Optional<PruebaDetalle> pp = pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(prueba, periodoAcademicoRepository.getPAActive());
		
		if(pp.get().getEstado().equalsIgnoreCase("CIERRE"))
		{
			throw new DataException(ESTADO_INVALIDO);
		}
		else {
			String[] HEADERs = { "Codigo", "Cedula", "Nombre", "Apellido", "Resultado", "Resultado Tiempo", "Nota Promedio" };
			try {
				ExcelHelper.generarExcel(obtenerDatos(prueba), filePath, HEADERs);
				
				generaDocumento(filePath, nombre, pp.get().getCodPruebaDetalle());
				
				PruebaDetalle p = new PruebaDetalle();
				p = pp.get();
				p.setEstado("CIERRE");
				
				pruebaDetalleRepository.save(p);
				
			}catch(IOException ex) {
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
	public void generarPDF(HttpServletResponse response, String nombre, Integer prueba, String[] columnas) throws DocumentException, IOException, DataException {
		try {
			Optional<PruebaDetalle> pp = pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(prueba, periodoAcademicoRepository.getPAActive());
			if(pp.get().getEstado().equalsIgnoreCase("CIERRE"))
			{
				throw new DataException(ESTADO_INVALIDO);
			}
			else {
				String ruta = ARCHIVOS_RUTA + PATH_RESULTADO_PRUEBAS + periodoAcademicoRepository.getPAActive().toString()
						+ "/" + nombre;

				response.setContentType("application/pdf");

				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
				String fechaActual = dateFormatter.format(new Date());

				String cabecera = "Cuerpo-Bomberos";
				String valor = "attachment; filename=Datos" + fechaActual + ".pdf";

				response.addHeader(cabecera, valor);

				ExporterPdf exporter = new ExporterPdf();
				
				float[] widths = new float[] { 2.5f, 3.5f, 6f, 6f, 2.5f, 2.5f, 2.5f };

				exporter.exportar(response, columnas, obtenerDatos(prueba), widths, ruta);
				
				generaDocumento(ruta, nombre, pp.get().getCodPruebaDetalle());
				
				PruebaDetalle p = new PruebaDetalle();
				p = pp.get();
				p.setEstado("CIERRE");
				
				pruebaDetalleRepository.save(p);
			}
			
		}catch(IOException ex) {
			System.out.println("error: " + ex.getMessage());
		}

	}
	
	public ArrayList<ArrayList<String>> obtenerDatos(Integer prueba) {
		List<ResultadosPruebasFisicasDatos> datos = repo1.getResultados(prueba);
		return entityToArrayList(datos);
	}

	public static String[] entityToStringArray(ResultadosPruebasFisicasDatos entity) {
		return new String[] { entity.getIdPostulante().toString(), entity.getCedula(), entity.getNombre(),
				entity.getApellido(), entity.getResultado().toString(), entity.getResultadoTiempo().toString(), entity.getNotaPromedioFinal().toString() };
	}

	public static ArrayList<ArrayList<String>> entityToArrayList(List<ResultadosPruebasFisicasDatos> datos) {
		ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
	
		for (ResultadosPruebasFisicasDatos dato : datos) {
			System.out.println("entityToStringArray(dato): " + entityToStringArray(dato));
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
		doc.setCodDocumento(documento.getCodigo());
		//System.out.println("documento.getCodigo(): " + documento.getCodigo());
		
		saveDocumentoPrueba(doc);
	}
	
	private void saveDocumentoPrueba(DocumentoPrueba obj) {
		docPruebaRepo.save(obj);
	}

	@Override
	public void notificar(String mensaje) throws MessagingException {
		// TODO Auto-generated method stub
		
	}
	
}
