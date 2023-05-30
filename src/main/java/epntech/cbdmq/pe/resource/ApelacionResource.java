package epntech.cbdmq.pe.resource;

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
import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.ApelacionServiceImpl;

@RestController
@RequestMapping("/apelacion")
public class ApelacionResource {

	@Autowired
	private ApelacionServiceImpl objServices;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Apelacion obj) throws DataException, ParseException {
		return new ResponseEntity<>(objServices.save(obj), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<Apelacion> listar() {
		return objServices.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Apelacion> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
		return objServices.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Apelacion> actualizarDatos(@PathVariable("id") Integer id, @RequestBody Apelacion obj)
			throws DataException {
		return (ResponseEntity<Apelacion>) objServices.getById(id).map(datosGuardados -> {
			datosGuardados.setObservacionEstudiante(obj.getObservacionEstudiante());
			datosGuardados.setObservacionInstructor(obj.getObservacionInstructor());
			datosGuardados.setAprobacion(obj.getAprobacion());
			datosGuardados.setNotaNueva(obj.getNotaNueva());
			datosGuardados.setEstado(obj.getEstado());
			Apelacion datosActualizados = null;
			try {
				datosActualizados = objServices.update(datosGuardados);
				return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
			} catch (DataException e) {
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			}
			
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) {
		objServices.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@GetMapping("/listarByEstudiante/{id}")
	public List<Apelacion> listarByEstudiante(@PathVariable("id") Integer id) {
		return objServices.getByEstudiante(id);
	}
	
	@GetMapping("/listarByInstructor/{id}")
	public List<Apelacion> listarByInstructor(@PathVariable("id") Integer id) {
		return objServices.getByInstructor(id);
	}
	
	@GetMapping("/fechaApelacion/{cod_nota}")
	public Boolean fechaApelacion(@PathVariable("cod_nota") Integer cod_nota) throws DataException {
		return objServices.validaFechaApelacion(cod_nota);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}

}
