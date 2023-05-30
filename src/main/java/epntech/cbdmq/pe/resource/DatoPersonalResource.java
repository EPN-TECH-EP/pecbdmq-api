package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
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
			datosGuardados.setCod_datos_personales(obj.getCod_datos_personales());
			datosGuardados.setApellido(obj.getApellido());
			datosGuardados.setCedula(obj.getCedula());
			datosGuardados.setCod_estacion(obj.getCod_estacion());
			datosGuardados.setCorreo_personal(obj.getCorreo_personal());
			datosGuardados.setEstado(obj.getEstado());
			datosGuardados.setFecha_nacimiento(obj.getFecha_nacimiento());
			datosGuardados.setNombre(obj.getNombre());
			datosGuardados.setNum_telef_convencional(obj.getNum_telef_convencional());
			datosGuardados.setTipo_sangre(obj.getTipo_sangre());
			datosGuardados.setValidacion_correo(obj.getValidacion_correo());
			datosGuardados.setCod_provincia_nacimiento(obj.getCod_provincia_nacimiento());
			datosGuardados.setCod_unidad_gestion(obj.getCod_unidad_gestion());
			datosGuardados.setSexo(obj.getSexo());
			datosGuardados.setNum_telef_celular(obj.getNum_telef_celular());
			datosGuardados.setReside_pais(obj.getReside_pais());
			datosGuardados.setCod_provincia_residencia(obj.getCod_provincia_residencia());
			datosGuardados.setCalle_principal_residencia(obj.getCalle_principal_residencia());
			datosGuardados.setCalle_secundaria_residencia(obj.getCalle_secundaria_residencia());
			datosGuardados.setNumero_casa(obj.getNumero_casa());
			datosGuardados.setColegio(obj.getColegio());
			datosGuardados.setTipo_nacionalidad(obj.getTipo_nacionalidad());
			datosGuardados.setTiene_merito_deportivo(obj.getTiene_merito_deportivo());
			datosGuardados.setTiene_merito_academico(obj.getTiene_merito_academico());
			datosGuardados.setNombre_titulo_segundonivel(obj.getNombre_titulo_segundonivel());
			datosGuardados.setPais_titulo_segundonivel(obj.getPais_titulo_segundonivel());
			datosGuardados.setCiudad_titulo_segundonivel(obj.getCiudad_titulo_segundonivel());
			datosGuardados.setMerito_deportivo_descripcion(obj.getMerito_deportivo_descripcion());
			datosGuardados.setMerito_academico_descripcion(obj.getMerito_academico_descripcion());
			datosGuardados.setPin_validacion_correo(obj.getPin_validacion_correo());
			datosGuardados.setCorreo_institucional(obj.getCorreo_institucional());
			datosGuardados.setCod_cargo(obj.getCod_cargo());
			datosGuardados.setCod_rango(obj.getCod_rango());
			datosGuardados.setCod_grado(obj.getCod_grado());
			datosGuardados.setCod_documento_imagen(obj.getCod_documento_imagen());
			datosGuardados.setCod_canton_nacimiento(obj.getCod_canton_nacimiento());
			datosGuardados.setCod_canton_residencia(obj.getCod_canton_residencia());			
			datosGuardados.setFecha_salida_institucion(obj.getFecha_salida_institucion());
			datosGuardados.setNivel_instruccion(obj.getNivel_instruccion());
			datosGuardados.setNombre_titulo_tercernivel(obj.getNombre_titulo_tercernivel());
			datosGuardados.setNombre_titulo_cuartonivel(obj.getNombre_titulo_cuartonivel());
			datosGuardados.setEs_vulnerable(obj.getEs_vulnerable());
			datosGuardados.setPais_titulo_cuartonivel(obj.getPais_titulo_cuartonivel());
			datosGuardados.setPais_titulo_tercernivel(obj.getPais_titulo_tercernivel());
			
			
			DatoPersonal datosActualizados = null;
			try {
				datosActualizados = objService.updateDatosPersonales(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/eliminar/{id}")
	public ResponseEntity<DatoPersonal> eliminarDatos(@PathVariable("id") Integer codigo,
			@RequestBody DatoPersonal obj) {
		return (ResponseEntity<DatoPersonal>) objService.getDatosPersonalesById(codigo).map(datosGuardados -> {
			datosGuardados.setEstado(obj.getEstado());

			DatoPersonal datosActualizados = null;
			try {
				datosActualizados = objService.updateDatosPersonales(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
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
	
	@PostMapping("/guardarImagen")
	public ResponseEntity<?> guardarArchivo(@RequestParam String proceso,
			@RequestParam Integer codigo,@RequestParam MultipartFile archivo) throws Exception {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.actualizarImagen( proceso,codigo,archivo));
		
		} catch (Exception e) {			
			
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
			
		}

	}
	
	
	@PutMapping("/actualizarImagen")
	public ResponseEntity<?> actualizarArchivo(@RequestParam String proceso,
			@RequestParam Integer codigo,@RequestParam MultipartFile archivo) throws Exception {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.guardarImagen( proceso,codigo,archivo));
		
		} catch (Exception e) {			
			
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
			
		}

	}
}
