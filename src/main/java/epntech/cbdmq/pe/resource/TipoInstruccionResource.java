package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.admin.TipoInstruccion;
import epntech.cbdmq.pe.servicio.impl.TipoInstruccionServiceImpl;

@RestController
@RequestMapping("/tipoinstruccion")
//@CrossOrigin(origins = "${cors.urls}")
public class TipoInstruccionResource {

	@Autowired 
	private TipoInstruccionServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody TipoInstruccion obj){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.save(obj));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/listar")
	public List<TipoInstruccion> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoInstruccion> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<TipoInstruccion> actualizarDatos(@PathVariable("id") int codigo, @RequestBody TipoInstruccion obj) {
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setTipoInstruccion(obj.getTipoInstruccion());

			TipoInstruccion datosActualizados = objService.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") int codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}
}
