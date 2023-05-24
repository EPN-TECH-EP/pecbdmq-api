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

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.PruebaDetalleServiceImpl;

@RestController
@RequestMapping("/pruebadetalle")
public class PruebaDetalleResource {

	
	@Autowired
	private PruebaDetalleServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody PruebaDetalle obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PruebaDetalle> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	@GetMapping("/listar")
	public List<PruebaDetalle> listar() {
		return objService.getAll();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PruebaDetalle> actualizarDatos(@PathVariable("id") int codigo, @RequestBody PruebaDetalle obj) throws DataException{
		return (ResponseEntity<PruebaDetalle>) objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setDescripcionPrueba(obj.getDescripcionPrueba());
			datosGuardados.setFechaInicio(obj.getFechaInicio());
			datosGuardados.setFechaFin(obj.getFechaFin());
			datosGuardados.setHora(obj.getHora());
			datosGuardados.setEstado(obj.getEstado());
			/*datosGuardados.setPuntaje_minimo(obj.getPuntaje_minimo());
			datosGuardados.setPuntaje_maximo(obj.getPuntaje_maximo());
			datosGuardados.setTiene_puntaje(obj.getTiene_puntaje());*/
			
			
			PruebaDetalle datosActualizados = null;
			
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
