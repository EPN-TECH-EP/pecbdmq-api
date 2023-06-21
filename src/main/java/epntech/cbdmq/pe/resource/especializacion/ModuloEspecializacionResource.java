package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.text.ParseException;
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
import epntech.cbdmq.pe.dominio.admin.especializacion.ModuloEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.ModuloEspecializacionServiceImpl;

@RestController
@RequestMapping("/moduloEspecializacion")
public class ModuloEspecializacionResource {
	
	@Autowired
	private ModuloEspecializacionServiceImpl moduloEspecializacionServiceImpl;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody ModuloEspecializacion obj) throws DataException{
		return new ResponseEntity<>(moduloEspecializacionServiceImpl.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<ModuloEspecializacion> listar() {
		return moduloEspecializacionServiceImpl.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ModuloEspecializacion> obtenerPorId(@PathVariable("id") long codigo) {
		return moduloEspecializacionServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ModuloEspecializacion> updateDatos(@PathVariable("id") long codigo, @RequestBody ModuloEspecializacion obj) throws DataException, ParseException{
	
		return (ResponseEntity<ModuloEspecializacion>) moduloEspecializacionServiceImpl.getById(codigo).map(datosGuardados -> {
			datosGuardados.setNombreEspModulo(obj.getNombreEspModulo());
			datosGuardados.setEstado(obj.getEstado());

			ModuloEspecializacion datosActualizados = null;
			try {
				datosActualizados = moduloEspecializacionServiceImpl.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				return response(HttpStatus.BAD_REQUEST, e.getMessage());
			}
			
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {
		moduloEspecializacionServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
