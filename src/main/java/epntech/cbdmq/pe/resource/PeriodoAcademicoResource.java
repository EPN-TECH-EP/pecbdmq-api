package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.servicio.impl.PeriodoAcademicoServiceimpl;

@RestController
@RequestMapping("/periodoacademico")
//@CrossOrigin(origins = "${cors.urls}")
public class PeriodoAcademicoResource {
	
	@Autowired
	private PeriodoAcademicoServiceimpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody PeriodoAcademico obj){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.save(obj));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/listar")
	public List<PeriodoAcademico> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PeriodoAcademico> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<PeriodoAcademico> actualizarDatos(@PathVariable("id") int codigo, @RequestBody PeriodoAcademico obj) {
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setModulo(obj.getModulo());
			datosGuardados.setSemestre(obj.getSemestre());
			datosGuardados.setFechaInicio(obj.getFechaInicio());
			datosGuardados.setFechaFin(obj.getFechaFin());

			PeriodoAcademico datosActualizados = objService.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") int codigo) {
		objService.deleteById(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}
}
