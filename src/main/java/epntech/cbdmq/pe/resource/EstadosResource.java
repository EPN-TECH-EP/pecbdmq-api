
package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.List;
import java.util.Optional;

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
import epntech.cbdmq.pe.dominio.admin.Estados;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EstadosServiceImpl;

@RestController
@RequestMapping("/estados")
public class EstadosResource {

	@Autowired
	private EstadosServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Estados obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Estados> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Estados> obtenerPorId(@PathVariable("id") int codigo) throws DataException {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarDatos(@PathVariable("id") int id, @RequestBody Estados obj) throws DataException {
		Optional<Estados> dato = objService.getById(id);

        if (dato.isPresent()) {
        	Estados datosActualizados = dato.get();
        	datosActualizados.setNombre(obj.getNombre());
        	datosActualizados.setEstado(obj.getEstado());

        	return new ResponseEntity<>(objService.save(datosActualizados), HttpStatus.OK);
        } else {
            return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
        }
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}

