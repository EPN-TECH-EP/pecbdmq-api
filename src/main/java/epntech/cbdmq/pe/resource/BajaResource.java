package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Baja;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.BajaServiceImpl;
import epntech.cbdmq.pe.servicio.impl.EstudianteServiceImpl;

@RestController
@RequestMapping("/baja")
public class BajaResource {
	 @Autowired
	    private BajaServiceImpl objServices;
	@Autowired
	private EstudianteServiceImpl estudianteServiceImpl;

	 @PostMapping("/crear")
	    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestParam Integer codTipoBaja, @RequestParam String descripcionBaja,
			@RequestParam Integer codEstudiante, @RequestParam(required = false) Integer codSancion,
			@RequestParam List<MultipartFile> archivos)
			throws DataException, ParseException, IOException, ArchivoMuyGrandeExcepcion {
		Date fechaActual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaFormateada = sdf.format(fechaActual);
		Date date = sdf.parse(fechaFormateada);

		Baja baja = new Baja();
		baja.setCodEstudiante(codEstudiante);
		baja.setCodSancion(codSancion);
		baja.setCodTipoBaja(codTipoBaja);
		baja.setDescripcionBaja(descripcionBaja);
		baja.setEstado("ACTIVO");
		baja.setFechaBajaActual(date);

		try {
			return new ResponseEntity<>(objServices.save(baja, archivos), HttpStatus.OK);
		} catch (IOException e) {
			return response(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	    }
	
	 @GetMapping("/listar")
	    public List<Baja> listar() {
	        return objServices.getAll();
	    }
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<Baja> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
	        return objServices.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	 
	 @PutMapping("/{id}")
	public ResponseEntity<Baja> actualizarDatos(@PathVariable("id") Integer codigo, @RequestParam Integer codTipoBaja,
			@RequestParam String descripcionBaja, @RequestParam Integer codEstudiante,
			@RequestParam(required = false) Integer codSancion, @RequestParam String estado,
			@RequestParam List<MultipartFile> archivos) {
	        return (ResponseEntity<Baja>) objServices.getById(codigo).map(datosGuardados -> {
	            
			datosGuardados.setDescripcionBaja(descripcionBaja);
			datosGuardados.setCodTipoBaja(codTipoBaja);
			datosGuardados.setCodEstudiante(codEstudiante);
			datosGuardados.setCodSancion(codSancion);
			datosGuardados.setEstado(estado);
			Baja datosActualizados = null;
				try {
				datosActualizados = objServices.update(datosGuardados, archivos);
				} catch (DataException e) {
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			} catch (IOException e) {
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			} catch (ArchivoMuyGrandeExcepcion e) {
					return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
				}
	            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
	        }).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	 
	 @DeleteMapping("/{id}")
		public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) {
			objServices.delete(codigo);
			return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
		}
	    
	/*El m�todo darDeBaja cambiar� el estado al estudiante, dej�ndolo en BAJA y no debe
	ser tomado en cuenta para los procesos*/
	@PostMapping("/darDeBaja/{codEstudiante}")
	public ResponseEntity<?> darDeBaja(@PathVariable("codEstudiante") Integer codEstudiante) throws DataException{
		try {
		Optional<Estudiante> estudiante = estudianteServiceImpl.getById(codEstudiante);
		if(estudiante.isPresent())
		{
			return new ResponseEntity<>(objServices.darDeBaja(estudiante.get()), HttpStatus.OK);
		}else
			return response(HttpStatus.BAD_REQUEST, REGISTRO_NO_EXISTE);
		}catch(DataException e) {
			return response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	    }
	
}
