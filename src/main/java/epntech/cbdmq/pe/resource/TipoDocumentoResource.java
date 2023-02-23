package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.admin.TipoDocumento;
import epntech.cbdmq.pe.servicio.impl.TipoDocumentoServiceImpl;

@RestController
@RequestMapping("/tipodocumento")
//@CrossOrigin(origins = "${cors.urls}")
public class TipoDocumentoResource {

	@Autowired 
	private TipoDocumentoServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody TipoDocumento obj){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.save(obj));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/listar")
	public List<TipoDocumento> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoDocumento> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<TipoDocumento> actualizarDatos(@PathVariable("id") int codigo, @RequestBody TipoDocumento obj) {
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setTipoDocumento(obj.getTipoDocumento());

			TipoDocumento datosActualizados = objService.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") int codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}
}
