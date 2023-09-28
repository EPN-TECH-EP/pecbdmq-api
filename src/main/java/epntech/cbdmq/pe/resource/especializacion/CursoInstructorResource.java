package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import epntech.cbdmq.pe.dto.especializacion.CursoInstructorRequest;
import epntech.cbdmq.pe.dto.especializacion.CursoInstructorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
import epntech.cbdmq.pe.servicio.impl.especializacion.CursoInstructorServiceImpl;

@RestController
@Validated
@RequestMapping("/cursoInstructor")
public class CursoInstructorResource {

	@Autowired
	private CursoInstructorServiceImpl cursoInstructorServiceImpl;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@Valid @RequestBody CursoInstructorRequest obj) {
		return new ResponseEntity<>(cursoInstructorServiceImpl.save(obj), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<CursoInstructor> listar() {
		return cursoInstructorServiceImpl.listAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") long codigo) {
		return new ResponseEntity<>(cursoInstructorServiceImpl.getById(codigo), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarDatos(@PathVariable("id") long codigo, @Valid @RequestBody CursoInstructorRequest obj) {
		obj.setCodInstructorCurso(codigo);
		return new ResponseEntity<>(cursoInstructorServiceImpl.update(obj), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) {
		cursoInstructorServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	@GetMapping("/listarInstructoresCurso/{idCurso}")
	public List<CursoInstructorResponse> listarInstructoresCurso(@PathVariable("idCurso") long codigo) {
		return cursoInstructorServiceImpl.listInstructoresCurso(codigo);
	}

	@GetMapping("/listarCursosInstructor/{idInstructor}")
	public List<InstructoresCurso> listarCursosInstructor(@PathVariable("idInstructor") long codigo) {
		return cursoInstructorServiceImpl.listCursosInstructor(codigo);
	}
	@GetMapping("/getCoordinadorCurso/{idCurso}")
	public CursoInstructorResponse getInstructorCoordinadorCurso(@PathVariable("idCurso") long codigo) {
		return cursoInstructorServiceImpl.getInstructorCoordinadorCurso(codigo);
	}

	@GetMapping("/byUser")
	public List<CursoInstructor> userEsInstructor(@RequestParam("codUsuario") Integer codUsuario) {
		return cursoInstructorServiceImpl.getByusuario(codUsuario);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}