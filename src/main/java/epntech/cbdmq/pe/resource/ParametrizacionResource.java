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

import epntech.cbdmq.pe.dominio.admin.Parametrizacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.ParametrizacionServiceImpl;

@RestController
@RequestMapping("/parametrizacionfecha")
public class ParametrizacionResource {
		
	@Autowired
	private ParametrizacionServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Parametrizacion obj) throws DataException{
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.save(obj));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/listar")
	public List<Parametrizacion> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Parametrizacion> obtenerPorId(@PathVariable("id") Integer codigo) {
		return objService.getbyId(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Parametrizacion> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody Parametrizacion obj) throws DataException{
		return objService.getbyId(codigo).map(datosGuardados -> {
			datosGuardados.setCodparametriza(obj.getCodparametriza());
			datosGuardados.setFechainicioparam(obj.getFechainicioparam());
			datosGuardados.setFechafinparam(obj.getFechafinparam());
			datosGuardados.setHorainicioparam(obj.getHorainicioparam());
			datosGuardados.setHorafinparam(obj.getHorafinparam());
			datosGuardados.setObservacionparametriza(obj.getObservacionparametriza());
			
			
			Parametrizacion datosActualizados = null;
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
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") Integer codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}
	
	
}
