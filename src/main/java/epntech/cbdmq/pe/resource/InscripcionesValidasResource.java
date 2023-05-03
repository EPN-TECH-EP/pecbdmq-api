package epntech.cbdmq.pe.resource;

import java.util.List;

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

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.dominio.admin.InscripcionesValidas;
import epntech.cbdmq.pe.dominio.util.InscripcionesValidasUtil;

import epntech.cbdmq.pe.servicio.ExcelService;

import epntech.cbdmq.pe.servicio.impl.ConvocatoriaServicieImpl;
import epntech.cbdmq.pe.servicio.impl.InscripcionesValidasServiceImpl;


@RestController
@RequestMapping("/inscripcionvalida")
public class InscripcionesValidasResource {

	@Autowired
	private InscripcionesValidasServiceImpl objService;
	 @Autowired
	  ExcelService fileService;
	
	@GetMapping("/listar")
	public List<InscripcionesValidasUtil> listar() {
		return objService.getinscripcioneslistar();
	}
	
	@GetMapping("/descargaraprobados")
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
