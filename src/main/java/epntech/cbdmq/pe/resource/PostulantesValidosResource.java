package epntech.cbdmq.pe.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.excepcion.GestorExcepciones;
import epntech.cbdmq.pe.servicio.impl.PostulantesValidosServiceImpl;

@RestController
@RequestMapping("/postulantesValidos")
public class PostulantesValidosResource {
	
	@Autowired
	private PostulantesValidosServiceImpl service;

	// listar postulantes válidos por filtro con GET
	// implementar public List<PostulantesValidos> getPostulantesValidosFiltro(String tipoFiltro, String valorFiltro)
	// en PostulantesValidosServiceImpl.java

	@GetMapping("/postulantesValidosFiltro")
	public ResponseEntity<?> listarAllFiltro(String tipoFiltro, String valorFiltro) {
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
	
	@GetMapping("/postulantesValidosPaginado")
	public ResponseEntity<?> listarAllPaginado(Pageable pageable) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getAllPostulantesValidosPaginado(pageable));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}
	
	@GetMapping("/postulantesValidosPaginadoOrderApellido")
	public ResponseEntity<?> listarAllPaginadoOrderApellido(Pageable pageable) {
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
}
