package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoInstructor;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.CursoInstructorServiceImpl;

@RestController
@RequestMapping("/cursoInstructor")
public class CursoInstructorResource {

	@Autowired
	private CursoInstructorServiceImpl cursoInstructorServiceImpl;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody CursoInstructor obj){

		return new ResponseEntity<>(cursoInstructorServiceImpl.save(obj), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<CursoInstructor> listar() {
		return cursoInstructorServiceImpl.listAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoInstructor> obtenerPorId(@PathVariable("id") long codigo) {
		return cursoInstructorServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<CursoInstructor> actualizarDatos(@PathVariable("id") long codigo, @RequestBody CursoInstructor obj) throws DataException{
		
	
		return (ResponseEntity<CursoInstructor>) cursoInstructorServiceImpl.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCodCursoEspecializacion(obj.getCodCursoEspecializacion());
			datosGuardados.setCodInstructor(obj.getCodInstructor());
			datosGuardados.setCodTipoInstructor(obj.getCodTipoInstructor());
			datosGuardados.setDescripcion(obj.getDescripcion());
			datosGuardados.setEstado(obj.getEstado());

			CursoInstructor datosActualizados = null;
			datosActualizados = cursoInstructorServiceImpl.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {

		cursoInstructorServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
