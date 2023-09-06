package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import epntech.cbdmq.pe.dominio.util.EstacionTrabajoDto;
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
import epntech.cbdmq.pe.dominio.admin.EstacionTrabajo;
import epntech.cbdmq.pe.servicio.impl.EstacionTrabajoServiceImpl;

@RestController
@RequestMapping("/estacionTrabajo")
public class EstacionTrabajoResource {

	@Autowired
	private EstacionTrabajoServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody EstacionTrabajo obj) {
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<EstacionTrabajoDto> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstacionTrabajoDto> obtenerPorId(@PathVariable("id") int codigo) {
		return new ResponseEntity<>(objService.getById(codigo), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EstacionTrabajoDto> actualizarDatos(@PathVariable("id") int codigo, @RequestBody EstacionTrabajo obj) {
		obj.setCodigo(codigo);
		return new ResponseEntity<>(objService.update(obj), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
