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

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.MateriaServiceImpl;

@RestController
@RequestMapping("/materia")
//@CrossOrigin(origins = "${cors.urls}")
public class MateriaResource {
	
	@Autowired
	private MateriaServiceImpl objService;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Materia obj) throws DataException{
		obj.setNombre(obj.getNombre().toUpperCase());
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Materia> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Materia> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Materia> actualizarDatos(@PathVariable("id") int codigo, @RequestBody Materia obj) throws DataException{
		return (ResponseEntity<Materia>) objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setNombre(obj.getNombre().toUpperCase());
			//datosGuardados.setNumHoras(obj.getNumHoras());
			datosGuardados.setTipoMateria(obj.getTipoMateria());
			//datosGuardados.setObservacionMateria(obj.getObservacionMateria());
			//datosGuardados.setPesoMateria(obj.getPesoMateria());
			//datosGuardados.setNotaMinima(obj.getNotaMinima());
			datosGuardados.setEstado(obj.getEstado());

			Materia datosActualizados = null;
			
			try {
				datosActualizados = objService.update(datosGuardados);
				} catch (DataException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
				}
				return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
			
			
		}).orElseGet(() -> ResponseEntity.notFound().build());
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
