package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.io.IOException;
import java.util.List;

import epntech.cbdmq.pe.dominio.admin.llamamiento.DatosSincronizados;
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
import org.springframework.web.multipart.MultipartFile;

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
	public ResponseEntity<?> guardarDatosPersonales(@RequestBody DatoPersonal obj) throws DataException, MessagingException, IOException {
		return new ResponseEntity<>(objService.saveDatosPersonales(obj), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<DatoPersonal> listarDatos() {
		return objService.getAllDatosPersonales();
	}
	@GetMapping("/listarSincronizados")
	public List<DatosSincronizados> listarDatosSincronizados() {
		return objService.getDatosSincronizados();
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
			datosGuardados.setCodDatosPersonales(obj.getCodDatosPersonales());
			datosGuardados.setApellido(obj.getApellido());
			datosGuardados.setCedula(obj.getCedula());
			datosGuardados.setCodEstacion(obj.getCodEstacion());
			datosGuardados.setCorreoPersonal(obj.getCorreoPersonal());
			datosGuardados.setEstado(obj.getEstado());
			datosGuardados.setFechaNacimiento(obj.getFechaNacimiento());
			datosGuardados.setNombre(obj.getNombre());
			datosGuardados.setNumTelefConvencional(obj.getNumTelefConvencional());
			datosGuardados.setTipoSangre(obj.getTipoSangre());
			datosGuardados.setValidacionCorreo(obj.getValidacionCorreo());
			datosGuardados.setCodProvinciaNacimiento(obj.getCodProvinciaNacimiento());
			datosGuardados.setCodUnidadGestion(obj.getCodUnidadGestion());
			datosGuardados.setSexo(obj.getSexo());
			datosGuardados.setNumTelefCelular(obj.getNumTelefCelular());
			datosGuardados.setResidePais(obj.getResidePais());
			datosGuardados.setCodProvinciaResidencia(obj.getCodProvinciaResidencia());
			datosGuardados.setCallePrincipalResidencia(obj.getCallePrincipalResidencia());
			datosGuardados.setCalleSecundariaResidencia(obj.getCalleSecundariaResidencia());
			datosGuardados.setNumeroCasa(obj.getNumeroCasa());
			datosGuardados.setColegio(obj.getColegio());
			datosGuardados.setTipoNacionalidad(obj.getTipoNacionalidad());
			datosGuardados.setTieneMeritoDeportivo(obj.getTieneMeritoDeportivo());
			datosGuardados.setTieneMeritoAcademico(obj.getTieneMeritoAcademico());
			datosGuardados.setNombreTituloSegundoNivel(obj.getNombreTituloSegundoNivel());
			datosGuardados.setPaisTituloSegundoNivel(obj.getPaisTituloSegundoNivel());
			datosGuardados.setCiudadTituloSegundoNivel(obj.getCiudadTituloSegundoNivel());
			datosGuardados.setMeritoDeportivoDescripcion(obj.getMeritoDeportivoDescripcion());
			datosGuardados.setMeritoAcademicoDescripcion(obj.getMeritoAcademicoDescripcion());
			datosGuardados.setPinValidacionCorreo(obj.getPinValidacionCorreo());
			datosGuardados.setCorreoInstitucional(obj.getCorreoInstitucional());
			datosGuardados.setCodCargo(obj.getCodCargo());
			datosGuardados.setCodRango(obj.getCodRango());
			datosGuardados.setCodGrado(obj.getCodGrado());
			datosGuardados.setCodDocumentoImagen(obj.getCodDocumentoImagen());
			datosGuardados.setCodCantonNacimiento(obj.getCodCantonNacimiento());
			datosGuardados.setCodCantonResidencia(obj.getCodCantonResidencia());
			datosGuardados.setFechaSalidaInstitucion(obj.getFechaSalidaInstitucion());
			datosGuardados.setNivelInstruccion(obj.getNivelInstruccion());
			datosGuardados.setNombreTituloTercerNivel(obj.getNombreTituloTercerNivel());
			datosGuardados.setNombreTituloCuartoNivel(obj.getNombreTituloCuartoNivel());
			datosGuardados.setEsVulnerable(obj.getEsVulnerable());
			datosGuardados.setPaisTituloCuartoNivel(obj.getPaisTituloCuartoNivel());
			datosGuardados.setPaisTituloTercerNivel(obj.getPaisTituloTercerNivel());
			
			
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
			
		return response(HttpStatus.OK, Boolean.toString(objService.isPasswordMatches(codigo, dato.getValidacionCorreo())));
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
