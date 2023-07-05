package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoModulo;
import epntech.cbdmq.pe.dominio.util.CursoModuloDatosEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.CursoModuloServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursoModulo")
public class CursoModuloResource {

	@Autowired
	private CursoModuloServiceImpl cursoModuloServiceImpl;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody @Valid CursoModulo cursoModulo)
			throws DataException {

		return new ResponseEntity<>(cursoModuloServiceImpl.save(cursoModulo), HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<?> actualizar(@RequestBody @Valid CursoModulo cursoModulo)
			throws DataException, IOException, ArchivoMuyGrandeExcepcion {

		return new ResponseEntity<>(cursoModuloServiceImpl.update(cursoModulo), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<CursoModuloDatosEspecializacion> listar() {
		return cursoModuloServiceImpl.listAll();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {
		cursoModuloServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoModulo> getById(@PathVariable("id") Long codigo) {
		return cursoModuloServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/listarByCurso/{id}")
	public List<CursoModulo> listarByCurso(@PathVariable("id") Long codigo) {
		return cursoModuloServiceImpl.getByCurso(codigo);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	//public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	public ResponseEntity<HttpResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		Map<String, String> errors = new HashMap<>();

		for (FieldError error : result.getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}

		//return ResponseEntity.badRequest().body(errors);
		return response(HttpStatus.BAD_REQUEST, errors.toString());
	}
}
