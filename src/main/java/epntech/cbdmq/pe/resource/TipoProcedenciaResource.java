package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.TipoProcedencia;
import epntech.cbdmq.pe.servicio.impl.TipoProcedenciaServiceImpl;

@RestController
@RequestMapping("/tipoprocedencia")
//@CrossOrigin(origins = "${cors.urls}")
public class TipoProcedenciaResource {

	@Autowired
	private TipoProcedenciaServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardarTipoProcedencia(@RequestBody TipoProcedencia obj) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.save(obj));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}

	@GetMapping("/listar")
	public List<TipoProcedencia> listarDatos() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoProcedencia> obtenerUsuarioPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<TipoProcedencia> actualizarDatos(@PathVariable("id") int codigo, @RequestBody TipoProcedencia obj) {
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setNombre(obj.getNombre());

			TipoProcedencia datosActualizados = objService.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") int codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}
	
}
