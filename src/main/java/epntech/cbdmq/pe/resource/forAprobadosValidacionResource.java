package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.util.InscripcionesValidasUtil;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.servicio.impl.DatoPersonalServiceImpl;
import epntech.cbdmq.pe.servicio.impl.ForAprobadoValidacionServiceImpl;
import epntech.cbdmq.pe.servicio.impl.InscripcionesValidasServiceImpl;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/AprobadosValidos")
public class forAprobadosValidacionResource {

	@Autowired
	private ForAprobadoValidacionServiceImpl objService;
	@Autowired
	private InscripcionesValidasServiceImpl objService2;
	
	
	@PostMapping("/guardarAprobados")
	public ResponseEntity<?> guardarArchivo(HttpServletResponse response, @RequestParam(value="nombre") String nombre ) throws Exception {
		
		try {
			objService.guardarDocumento(response, nombre);
			return response(HttpStatus.OK, "Ok");
		
		} catch (Exception e) {			
			
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
			
		}
	}
	
	
	
	


	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
	
	
	
	
}
