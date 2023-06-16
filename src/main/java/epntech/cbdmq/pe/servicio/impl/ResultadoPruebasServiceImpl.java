package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.FALLA_PROCESAR_EXCEL;
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
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.helper.ResultadoPruebasHelper;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoPruebaRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleRepository;
import epntech.cbdmq.pe.repositorio.admin.ResultadoPruebasDatosRepository;
import epntech.cbdmq.pe.repositorio.admin.ResultadoPruebasRepository;
import epntech.cbdmq.pe.servicio.ResultadoPruebasService;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ResultadoPruebasServiceImpl implements ResultadoPruebasService {

	@Autowired
	private ResultadoPruebasRepository repo;
	@Autowired
	private PruebaDetalleRepository pruebaDetalleRepository;
	@Autowired
	private ResultadoPruebasDatosRepository repo1;
	@Autowired
	private DocumentoRepository documentoRepo;
	@Autowired
	private DocumentoPruebaRepository docPruebaRepo;
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;
	

	@Override
	public void insertAll(List<ResultadoPruebas> obj) {
		repo.saveAll(obj);

	}

	@Override
	public ResultadoPruebas update(ResultadoPruebas objActualizado) {
		ResultadoPruebas resultadoPruebas = new ResultadoPruebas();
		Optional<PruebaDetalle> prueba = pruebaDetalleRepository.findById(objActualizado.getCodPruebaDetalle());
		if (prueba.isPresent()) {
			resultadoPruebas = repo.save(objActualizado);

			PruebaDetalle p = new PruebaDetalle();
			p = prueba.get();
			p.setEstado("REGISTRO");

			pruebaDetalleRepository.save(p);
		}

		return resultadoPruebas;
	}

	@Override
	public Optional<ResultadoPruebas> getByCodPostulanteAndCodPruebaDetalle(Integer CodPostulante, Integer codPrueba) {
		// TODO Auto-generated method stub
		return repo.findByCodPostulanteAndCodPruebaDetalle(CodPostulante, codPrueba);
	}

	@Override
	public void uploadFile(MultipartFile file) {
		try {

			List<ResultadoPruebas> datos = ResultadoPruebasHelper.excelToDatos(file.getInputStream());

			repo.saveAll(datos);
		} catch (IOException e) {
			throw new RuntimeException(FALLA_PROCESAR_EXCEL + " " + e.getMessage());
		}

	}

	@Override
	public ResultadoPruebas save(ResultadoPruebas obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public ByteArrayInputStream downloadFile() {
		

		ByteArrayInputStream in = ResultadoPruebasHelper.datosToExcel(null);
		return in;
	}

	@Override
	public void generarExcel(String filePath, String nombre, Integer subTipoPrueba) throws IOException, DataException {
		Optional<PruebaDetalle> pp = pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(subTipoPrueba, periodoAcademicoRepository.getPAActive());
		
		if(pp.get().getEstado().equalsIgnoreCase("CIERRE"))
		{
			throw new DataException(ESTADO_INVALIDO);
		}
		else {
			String[] HEADERs = { "Codigo", "id", "Cedula", "Nombre", "Apellido" };
			try {
				ExcelHelper.generarExcel(obtenerDatos(subTipoPrueba), filePath, HEADERs);
				
				generaDocumento(filePath, nombre, pp.get().getCodPruebaDetalle());
				
				
				
			}catch(IOException ex) {
				System.out.println("error: " + ex.getMessage());
			}
		}
		
		
	}

	@Override
	public List<ResultadosPruebasDatos> getResultados(Integer prueba) {
		// TODO Auto-generated method stub
		return repo1.getResultados(prueba);
	}

	@Override
	public void generarPDF(HttpServletResponse response, String filePath, String nombre, Integer subTipoPrueba) throws DocumentException, IOException, DataException {
		try {
			//Optional<Prueba> prueba;
			//prueba = pruebaRepository.findById(null);
			
			Optional<PruebaDetalle> pp = pruebaDetalleRepository.findByCodSubtipoPruebaAndCodPeriodoAcademico(subTipoPrueba, periodoAcademicoRepository.getPAActive());
			
			if(pp.get().getEstado().equalsIgnoreCase("CIERRE"))
			{
				throw new DataException(ESTADO_INVALIDO);
			}
			else {

				response.setContentType("application/pdf");

				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
				String fechaActual = dateFormatter.format(new Date());

				String cabecera = "Cuerpo-Bomberos";
				String valor = "attachment; filename=Datos" + fechaActual + ".pdf";

				response.addHeader(cabecera, valor);

				ExporterPdf exporter = new ExporterPdf();
				String[] columnas = { "Codigo", "id", "Cedula", "Nombre", "Apellido" };
				float[] widths = new float[] { 2f, 3f, 6f, 6f, 2.5f };

				exporter.exportar(response, columnas, obtenerDatos(subTipoPrueba), widths, filePath);
				
				generaDocumento(filePath, nombre, pp.get().getCodPruebaDetalle());
				
			}
			
			
		}catch(IOException ex) {
			System.out.println("error: " + ex.getMessage());
		}

	}
	
	public ArrayList<ArrayList<String>> obtenerDatos(Integer prueba) {
		List<ResultadosPruebasDatos> datos = repo1.get_approved_applicants(prueba);
		return entityToArrayList(datos);
	}

	public static String[] entityToStringArray(ResultadosPruebasDatos entity) {
		return new String[] { entity.getCodPostulante().toString(), entity.getIdPostulante().toString(), entity.getCedula(), entity.getNombre(),
				entity.getApellido() };
	}

	public static ArrayList<ArrayList<String>> entityToArrayList(List<ResultadosPruebasDatos> datos) {
		ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
		for (ResultadosPruebasDatos dato : datos) {

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
		//System.out.println("documento.getCodigo(): " + documento.getCodigo());
		
		saveDocumentoPrueba(doc);
	}
	
	private void saveDocumentoPrueba(DocumentoPrueba obj) {
		docPruebaRepo.save(obj);
	}
	
}
