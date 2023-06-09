package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.EXITO;

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
import epntech.cbdmq.pe.dominio.admin.Instructor;
import epntech.cbdmq.pe.dominio.admin.InstructorMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.InstructorServiceImpl;

@RestController
@RequestMapping("/instructor")
public class InstructorResource {

	@Autowired
	private InstructorServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Instructor obj) throws DataException {
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Instructor> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Instructor> obtenerPorId(@PathVariable("id") Integer codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Instructor> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody Instructor obj)
			throws DataException {
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCodInstructor(obj.getCodInstructor());
			datosGuardados.setCodTipoProcedencia(obj.getCodTipoProcedencia());
			/*datosGuardados.setCod_tipo_instructor(obj.getCod_tipo_instructor());
			datosGuardados.setCod_periodo_academico(obj.getCod_tipo_instructor());
			datosGuardados.setCod_periodo_academico(obj.getCod_periodo_academico());*/
			Instructor datosActualizados = objService.update(datosGuardados);
			datosActualizados = objService.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") Integer codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente", HttpStatus.OK);
	}
	
	@PostMapping("/asignarMaterias")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> asignarMaterias(@RequestBody List<InstructorMateria> lista) throws DataException{
		objService.saveAllMaterias(lista);
		return response(HttpStatus.OK, EXITO);
	    }
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
	
}
