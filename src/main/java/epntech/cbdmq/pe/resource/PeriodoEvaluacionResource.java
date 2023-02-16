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

import epntech.cbdmq.pe.dominio.admin.PeriodoEvaluacion;
import epntech.cbdmq.pe.servicio.impl.PeriodoEvaluacionServiceImpl;

@RestController
@RequestMapping("/periodoevaluacion")
//@CrossOrigin(origins = "${cors.urls}")
public class PeriodoEvaluacionResource {

	@Autowired
	private PeriodoEvaluacionServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody PeriodoEvaluacion obj){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.save(obj));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/listar")
	public List<PeriodoEvaluacion> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PeriodoEvaluacion> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<PeriodoEvaluacion> actualizarDatos(@PathVariable("id") int codigo, @RequestBody PeriodoEvaluacion obj) {
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCodPeriodoEvaluacion(obj.getCodModulo());
			datosGuardados.setFechaInicio(obj.getFechaInicio());
			datosGuardados.setFechaFin(obj.getFechaFin());

			PeriodoEvaluacion datosActualizados = objService.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") int codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}

}
