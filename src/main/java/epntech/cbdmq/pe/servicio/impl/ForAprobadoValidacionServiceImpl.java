package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_INSCRIPCION_VAL;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.util.InscripcionesValidasUtil;
import epntech.cbdmq.pe.dominio.util.forAprobadosValidacion;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.ForAprobadovalidacionRepository;
import epntech.cbdmq.pe.repositorio.admin.InscripcionesValidasRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.forAprobadosValidacionService;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ForAprobadoValidacionServiceImpl implements forAprobadosValidacionService  {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")	
	public DataSize TAMAÑO_MÁXIMO;
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Autowired
	private PeriodoAcademicoRepository repo;
	
	@Autowired
	private ForAprobadovalidacionRepository AprobadosRepository;
	
	@Autowired
	private InscripcionesValidasRepository inscripcionesRepo;
	
	@Autowired
	private ExporterPdf pdf;
	@Override
	public void guardarDocumento(HttpServletResponse response,String nombre)
			throws IOException, ArchivoMuyGrandeExcepcion, DocumentException {
		
		response.setContentType("application/pdf");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Cuerpo-Bomberos";
		String valor = "attachment; filename=Materias" + fechaActual + ".pdf";

		response.addHeader(cabecera, valor);

		ExporterPdf exporter = new ExporterPdf();
		String[] columnas = { "Codigo del listado de inscritos " };
		float[] widths = new float[] { 2f};
		String rutapdf= ARCHIVOS_RUTA+PATH_PROCESO_INSCRIPCION_VAL + repo.getPeriodoAcademicoActivo().getCodigo()+"/"+nombre+".pdf";
		String rutaexel= ARCHIVOS_RUTA+PATH_PROCESO_INSCRIPCION_VAL + repo.getPeriodoAcademicoActivo().getCodigo()+"/"+nombre+".xlsx";
		
		pdf.exportar(response, columnas, obtenerDatos(), widths, rutapdf);
		generaDocumento(rutapdf, "listdo.pdf", repo.getPeriodoAcademicoActivo().getCodigo());
		
		String[] HEADERs = { "Codigo" };
		try {
			ExcelHelper.generarExcel(obtenerDatos(), rutaexel, HEADERs);
			
			generaDocumento(rutaexel, "listdo.xlsx", repo.getPeriodoAcademicoActivo().getCodigo());
			
		}catch(IOException ex) {
			System.out.println("error: " + ex.getMessage());
		}
		
	}
        
	public ArrayList<ArrayList<String>> obtenerDatos() {
		List<InscripcionesValidasUtil> inscripciones = inscripcionesRepo.getinscripcioneslistar();
		
		return datosToArrayList(inscripciones);
	}

	public static String[] datoToStringArray(InscripcionesValidasUtil dato) {
		
		return new String[] { dato.getIdPostulante().toString() };
	}

	public static ArrayList<ArrayList<String>> datosToArrayList(List<InscripcionesValidasUtil> datos) {
		ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
		for (InscripcionesValidasUtil dato : datos) {
			
			arrayMulti.add(new ArrayList<String>(Arrays.asList(datoToStringArray(dato))));
		}
		return arrayMulti;
	}
	
private String ruta(String proceso, Integer codigo) {
		
		String resultado = null;
		
		
			resultado=ARCHIVOS_RUTA+proceso+"/"+ codigo+"/";	


		return resultado;
	}

private void generaDocumento(String ruta, String nombre, Integer periodo) {
	Documento documento = new Documento();
	documento.setEstado("ACTIVO");
	documento.setNombre(nombre);
	documento.setRuta(ruta);
	
	documento = documentoRepository.save(documento);
	
	forAprobadosValidacion doc = new forAprobadosValidacion(); 
	doc.setCodPeriodoAcademico(periodo);
	doc.setCodDocumento(documento.getCodigo());
	//System.out.println("documento.getCodigo(): " + documento.getCodigo());
	
	AprobadosRepository.save(doc);
}
}
