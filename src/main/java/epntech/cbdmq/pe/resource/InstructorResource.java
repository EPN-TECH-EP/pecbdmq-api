package epntech.cbdmq.pe.resource;

import java.util.List;

import epntech.cbdmq.pe.dominio.util.EstudianteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Instructor;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.InstructorServiceImpl;

import static epntech.cbdmq.pe.constante.MensajesConst.NO_ENCUENTRA;

@RestController
@RequestMapping("/instructor")
public class InstructorResource {

	@Autowired
	private InstructorServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Instructor obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Instructor> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Instructor> obtenerPorId(@PathVariable("id") Integer codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Instructor> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody Instructor obj) throws DataException{
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCod_instructor(obj.getCod_instructor());
			datosGuardados.setCod_tipo_procedencia(obj.getCod_tipo_procedencia());
			datosGuardados.setCod_tipo_instructor(obj.getCod_tipo_instructor());
			datosGuardados.setCod_periodo_academico(obj.getCod_tipo_instructor());
			datosGuardados.setCod_periodo_academico(obj.getCod_periodo_academico());
			Instructor datosActualizados = objService.update(datosGuardados);
			datosActualizados = objService.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") Integer codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}
	//TODO la clase instructor no esta acorde a la ultima BD
	@PostMapping("/ByUser")
	public Instructor listarInstructorByUsuario(@RequestParam("codUsuario")String codUsuario) throws DataException {
		Instructor instructor= objService.getInstructorByUser(codUsuario);
		if(instructor==null){
			throw new DataException(NO_ENCUENTRA);
		}
		return instructor;
	}
	
	 private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
	        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
	                message), httpStatus);
	    }
	
	
}
