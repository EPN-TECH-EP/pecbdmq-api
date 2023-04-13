package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.DatoPersonalServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.*;

@RestController
@RequestMapping("/datopersonal")
public class DatoPersonalResource {

	@Autowired
	private DatoPersonalServiceImpl objService;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardarDatosPersonales(@RequestBody DatoPersonal obj) throws DataException, MessagingException {
		return new ResponseEntity<>(objService.saveDatosPersonales(obj), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<DatoPersonal> listarDatos() {
		return objService.getAllDatosPersonales();
	}

	@GetMapping("/paginado")
	// public Page<DatosPersonales> listarDatos(Pageable pageable) throws Exception
	// {
	public ResponseEntity<?> listarDatos(Pageable pageable) {
		// return objService.getAllDatosPersonales(pageable);
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.getAllDatosPersonales(pageable));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, "Error. Por favor intente más tarde.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
		try {
			return objService.getDatosPersonalesById(codigo).map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, "Error. Por favor intente más tarde.");
		}
	}

	@GetMapping("/cedula/{cedula}")
	public ResponseEntity<DatoPersonal> obtenerPorCedula(@PathVariable("cedula") String cedula) {
		return objService.getByCedula(cedula).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<DatoPersonal> actualizarDatos(@PathVariable("id") Integer codigo,
			@RequestBody DatoPersonal obj) {
		return (ResponseEntity<DatoPersonal>) objService.getDatosPersonalesById(codigo).map(datosGuardados -> {
			datosGuardados.setCod_estacion(obj.getCod_estacion());
			datosGuardados.setCedula(obj.getCedula());
			datosGuardados.setNombre(obj.getNombre());
			datosGuardados.setApellido(obj.getApellido());
			datosGuardados.setFecha_nacimiento(obj.getFecha_nacimiento());
			datosGuardados.setCorreo_personal(obj.getCorreo_personal());
			datosGuardados.setValidacion_correo(obj.getValidacion_correo());
			//datosGuardados.setNum_telef(obj. getNum_telef());
			//datosGuardados.setCiudad(obj.getCiudad());
			datosGuardados.setTipo_sangre(obj.getTipo_sangre());
			//datosGuardados.setUnidad(obj.getUnidad());
			datosGuardados.setEstado(obj.getEstado());
			//datosGuardados.setProvincia(obj.getProvincia());

			DatoPersonal datosActualizados = null;
			try {
				datosActualizados = objService.updateDatosPersonales(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/eliminar/{id}")
	public ResponseEntity<DatoPersonal> eliminarDatos(@PathVariable("id") Integer codigo,
			@RequestBody DatoPersonal obj) {
		return objService.getDatosPersonalesById(codigo).map(datosGuardados -> {
			datosGuardados.setEstado(obj.getEstado());

			DatoPersonal datosActualizados = null;
			try {
				datosActualizados = objService.updateDatosPersonales(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@RequestMapping("/api")
	public String home(HttpServletRequest request) throws Exception {
		// Timeout simulation
		// Thread.sleep(random.nextInt(2000));

		return String.format("Servicio (%s)", request.getRequestURL());
		// return new ResponseEntity<>(request.getRequestURL(), HttpStatus.OK);
	}

	@GetMapping("/buscarpaginado")
	public ResponseEntity<?> search(@RequestParam String filtro, Pageable pageable) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.search(filtro, pageable));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
		objService.deleteById(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	@GetMapping("/codematch")
	public ResponseEntity<HttpResponse> getPasswordMatches(@RequestParam("id") int id, @RequestParam("codigo") String codigo)
			throws DataException {
		DatoPersonal dato;

		try {
			dato = objService.getDatosPersonalesById(id).get();
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
		}
			
		return response(HttpStatus.OK, Boolean.toString(objService.isPasswordMatches(codigo, dato.getValidacion_correo())));
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
}
