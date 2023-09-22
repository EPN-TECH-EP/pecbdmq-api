package epntech.cbdmq.pe.resource;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.excepcion.GestorExcepciones;
import epntech.cbdmq.pe.servicio.impl.PostulantesValidosServiceImpl;

import java.io.IOException;

import static epntech.cbdmq.pe.constante.ResponseMessage.ERROR_GENERAR_ARCHIVO;
import static epntech.cbdmq.pe.constante.ResponseMessage.EXITO_GENERAR_ARCHIVO;
import static epntech.cbdmq.pe.util.ResponseEntityUtil.response;

@RestController
@RequestMapping("/postulantesValidos")
public class PostulantesValidosResource {
	
	@Autowired
	private PostulantesValidosServiceImpl service;

	// listar postulantes válidos por filtro con GET
	// implementar public List<PostulantesValidos> getPostulantesValidosFiltro(String tipoFiltro, String valorFiltro)
	// en PostulantesValidosServiceImpl.java

	@GetMapping("/postulantesValidosFiltro")
	public ResponseEntity<?> listarAllValidoFiltro(String tipoFiltro, String valorFiltro) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getPostulantesValidosFiltro(tipoFiltro, valorFiltro));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}
	
	@GetMapping("/postulantesValidos")
	public ResponseEntity<?> listarAll() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getAllPostulantesValidos());
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}
	@GetMapping("/resultadoPostulantes")
	public ResponseEntity<?> listarPostulantes() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getPospulantesAprobadosReprobados());
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}
	
	@GetMapping("/postulantesValidosPaginado")
	public ResponseEntity<?> listarAllValidoPaginado(Pageable pageable) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getAllPostulantesValidosPaginado(pageable));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}
	
	@GetMapping("/postulantesValidosPaginadoOrderApellido")
	public ResponseEntity<?> listarAllValidoPaginadoOrderApellido(Pageable pageable) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getAllPostulantesValidosPaginadoOrderApellido(pageable));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}


	// listar todos los postulantes para seguimiento de inscripciones
	@GetMapping("/postulantesTodoFiltro")
	public ResponseEntity<?> listarAllFiltro(String tipoFiltro, String valorFiltro) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getPostulantesTodoFiltro(tipoFiltro, valorFiltro));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}

	@GetMapping("/postulantesTodoPaginado")
	public ResponseEntity<?> listarAllPaginado(Pageable pageable) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getAllPostulantesTodoPaginado(pageable));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}

	@GetMapping("/postulantesTodoPaginadoOrderApellido")
	public ResponseEntity<?> listarAllPaginadoOrderApellido(Pageable pageable) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getAllPostulantesTodoPaginadoOrderApellido(pageable));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}
	@PostMapping("/generarArchivoAprobados")
	public ResponseEntity<?> generarAprobados(HttpServletResponse response)
			throws DocumentException, DataException {
		try {
			service.generarArchivosAprobados(response);
		} catch (IOException e) {
			e.printStackTrace();
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
	}
	@PostMapping("/generarArchivoReprobados")
	public ResponseEntity<?> generarReprobados(HttpServletResponse response)
			throws DocumentException, DataException {
		try {
			service.generarArchivosReprobados(response);
		} catch (IOException e) {
			e.printStackTrace();
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
	}
	@PostMapping("/generar")
	public ResponseEntity<?> generar(HttpServletResponse response)
			throws DocumentException, DataException {
		try {
			service.generarArchivosAprobados(response);
			service.generarArchivosReprobados(response);
		} catch (IOException e) {
			e.printStackTrace();
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
	}
}
