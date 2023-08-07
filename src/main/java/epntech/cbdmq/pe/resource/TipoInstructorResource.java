package epntech.cbdmq.pe.resource;

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

import epntech.cbdmq.pe.dominio.admin.TipoInstructor;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.TipoInstructorServiceImpl;


@RestController
@RequestMapping("/tipoInstructor")
public class TipoInstructorResource {
	@Autowired
	private TipoInstructorServiceImpl objServices;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody TipoInstructor obj) {
		return new ResponseEntity<>(objServices.save(obj), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<TipoInstructor> listar() {
		return objServices.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoInstructor> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
		return new ResponseEntity<>(objServices.getById(codigo), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody TipoInstructor obj) throws DataException {
		obj.setCodigo(codigo);
		return new ResponseEntity<>(objServices.update(obj), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") Integer codigo) {
		objServices.delete(codigo);
		return ResponseEntity.ok(REGISTRO_ELIMINADO_EXITO);
	}
	
}
