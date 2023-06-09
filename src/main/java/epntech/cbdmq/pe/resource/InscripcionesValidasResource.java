package epntech.cbdmq.pe.resource;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.util.InscripcionesValidasUtil;
import epntech.cbdmq.pe.repositorio.admin.InscripcionesValidasRepository;
import epntech.cbdmq.pe.servicio.ExelInscripcionValidaService;
import epntech.cbdmq.pe.servicio.impl.InscripcionesValidasServiceImpl;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/inscripcionvalida")
public class InscripcionesValidasResource {
	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	@Autowired
	ExelInscripcionValidaService fileService;

	@Autowired
	private InscripcionesValidasServiceImpl objService;
	
	@Autowired
	private InscripcionesValidasRepository repo;
	
	@GetMapping("/listar")
	public List<InscripcionesValidasUtil> listar() {
		return objService.getinscripcioneslistar();
	}
	
	@GetMapping("/exportarinscritos")
	public void exportarListadoDeInscritosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Cuerpo-Bomberos";
		String valor = "attachment; filename=Materias" + fechaActual + ".pdf";

		response.addHeader(cabecera, valor);

		ExporterPdf exporter = new ExporterPdf();
		String[] columnas = { "Codigo Postulantes aprobados CBDMQ"};
		float[] widths = new float[] { 2f };
		
		exporter.exportar(response, columnas, obtenerInscritos(), widths);
	}

	public ArrayList<ArrayList<String>> obtenerInscritos() {
		List<InscripcionesValidasUtil> inscripcionesvalidas = repo.getinscripcioneslistar();
		return inscripcionesToArrayList(inscripcionesvalidas);
	}

	public static String[] inscripcionToStringArray(InscripcionesValidasUtil inscripcion) {
		return new String[] { inscripcion.getIdPostulante()};
	}

	public static ArrayList<ArrayList<String>> inscripcionesToArrayList(List<InscripcionesValidasUtil> inscripcion) {
		ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
		for (InscripcionesValidasUtil inscripciones : inscripcion) {
			
			arrayMulti.add(new ArrayList<String>(Arrays.asList(inscripcionToStringArray(inscripciones))));
		}
		return arrayMulti;
	}
	
	
	  @GetMapping("/descargar")
	  public ResponseEntity<Resource> getFile() {
	    String filename = "datos.xlsx";
	    InputStreamResource file = new InputStreamResource(fileService.load());

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }

	  private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
	      return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
	              message), httpStatus);
	  }
	


}
