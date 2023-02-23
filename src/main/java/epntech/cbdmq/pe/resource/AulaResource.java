package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.AulaServiceImpl;

@RestController
@RequestMapping("/aula")
//@CrossOrigin(origins = "${cors.urls}")
public class AulaResource {
	
	@Autowired
	private AulaServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Aula obj) throws DataException{
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.save(obj));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/listar")
	public List<Aula> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Aula> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Aula> actualizarDatos(@PathVariable("id") int codigo, @RequestBody Aula obj) throws DataException{
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setNombre(obj.getNombre());
			datosGuardados.setCapacidad(obj.getCapacidad());
			datosGuardados.setTipo(obj.getTipo());
			datosGuardados.setPcs(obj.getPcs());
			datosGuardados.setImpresoras(obj.getImpresoras());
			datosGuardados.setInternet(obj.getInternet());
			datosGuardados.setProyectores(obj.getProyectores());
			datosGuardados.setInstructor(obj.getInstructor());
			datosGuardados.setSalaOcupada(obj.getSalaOcupada());

			Aula datosActualizados = null;
			try {
				datosActualizados = objService.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") int codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}

}
