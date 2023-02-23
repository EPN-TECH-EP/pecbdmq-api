package epntech.cbdmq.pe.resource;

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

import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.servicio.impl.ConvocatoriaServicieImpl;

@RestController
@RequestMapping("/convocatoria")
public class ConvocatoriaResource {

	@Autowired
	private ConvocatoriaServicieImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Convocatoria obj){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.saveData(obj));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/listar")
	public List<Convocatoria> listar() {
		return objService.getAllData();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Convocatoria> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getByIdData(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Convocatoria> actualizarDatos(@PathVariable("id") int codigo, @RequestBody Convocatoria obj) {
		return objService.getByIdData(codigo).map(datosGuardados -> {
			datosGuardados.setCodModulo(obj.getCodModulo());
			datosGuardados.setCodPeriodoAcademico(obj.getCodPeriodoAcademico());
			datosGuardados.setCodPeriodoEvaluacion(obj.getCodPeriodoEvaluacion());
			datosGuardados.setNombre(obj.getNombre());
			datosGuardados.setEstado(obj.getEstado());
			datosGuardados.setFechaInicioConvocatoria(obj.getFechaInicioConvocatoria());
			datosGuardados.setFechaFinConvocatoria(obj.getFechaFinConvocatoria());
			datosGuardados.setHoraInicioConvocatoria(obj.getHoraInicioConvocatoria());
			datosGuardados.setHoraFinConvocatoria(obj.getHoraFinConvocatoria());

			Convocatoria datosActualizados = objService.updateData(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") int codigo) {
		objService.deleteData(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}
}
