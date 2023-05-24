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
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
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
		return (ResponseEntity<Parametrizacion>) objService.getbyId(codigo).map(datosGuardados -> {
			datosGuardados.setCodparametriza(obj.getCodparametriza());
			datosGuardados.setFechainicioparam(obj.getFechainicioparam());
			datosGuardados.setFechafinparam(obj.getFechafinparam());
			datosGuardados.setHorainicioparam(obj.getHorainicioparam());
			datosGuardados.setHorafinparam(obj.getHorafinparam());
			datosGuardados.setObservacionparametriza(obj.getObservacionparametriza());
			datosGuardados.setEstado(obj.getEstado());
			
			Parametrizacion datosActualizados = null;
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
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
	
}
