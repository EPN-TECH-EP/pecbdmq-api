package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;
import java.util.Optional;

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
import epntech.cbdmq.pe.dominio.admin.UnidadGestion;
import epntech.cbdmq.pe.excepcion.GestorExcepciones;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.UnidadGestionServiceImpl;

@RestController
@RequestMapping("/unidadgestion")
public class UnidadGestionResource extends GestorExcepciones {

	@Autowired
	private UnidadGestionServiceImpl objService;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public UnidadGestion guardarUnidadGestion(@RequestBody UnidadGestion obj) throws DataException {
		obj.setNombre(obj.getNombre().toUpperCase());
		return objService.saveUnidadGestion(obj);
	}

	@GetMapping("/listar")
	public List<UnidadGestion> listarDatos() {
		return objService.getAllUnidadGestion();
	}

	@GetMapping("/{id}")
	public Optional<UnidadGestion> listarById(@PathVariable("id") int codigo) {
		return objService.getUnidadGestionById(codigo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UnidadGestion> actualizarDatos(@PathVariable("id") int codigo, @RequestBody UnidadGestion obj)
			throws DataException {

		return (ResponseEntity<UnidadGestion>) objService.getUnidadGestionById(codigo).map(datosGuardados -> {
			datosGuardados.setNombre(obj.getNombre().toUpperCase());
			datosGuardados.setEstado(obj.getEstado());
			UnidadGestion datosActualizados = null;
			try {
				datosActualizados = objService.updateUnidadGestion(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
		objService.deleteUnidadGestion(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
						message),
				httpStatus);
	}
}
