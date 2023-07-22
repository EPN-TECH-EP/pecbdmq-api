package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import epntech.cbdmq.pe.dominio.admin.Sanciones;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.SancionesServiceImpl;



@RestController
@RequestMapping("/sanciones")
public class SancionesResource {

	@Autowired
    private SancionesServiceImpl objServices;
	
	@PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestParam Integer codEstudiante, @RequestParam String observacionSancion, @RequestParam Integer codInstructor, @RequestParam Integer codFaltaPeriodo, @RequestParam MultipartFile archivo) throws DataException, IOException, ArchivoMuyGrandeExcepcion, ParseException {
		Sanciones obj = new Sanciones();
		Date fechaActual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaFormateada = sdf.format(fechaActual);
		Date date = sdf.parse(fechaFormateada);
		
		obj.setCodEstudiante(codEstudiante);
		obj.setFechaSancion(date);
		obj.setObservacionSancion(observacionSancion);
		obj.setEstado("ACTIVO");
		obj.setCodInstructor(codInstructor);
		obj.setCodFaltaPeriodo(codFaltaPeriodo);
		
		return new ResponseEntity<>(objServices.save(obj, archivo), HttpStatus.OK);
    }
	
	 @GetMapping("/listar")
	    public List<Sanciones> listar() {
	        return objServices.getAll();
	    }
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<Sanciones> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
	        return objServices.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	 //TODO no vale actualizar
	 @PutMapping("/{id}")
	    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestParam Integer codEstudiante, @RequestParam String observacionSancion, @RequestParam Integer codInstructor, @RequestParam Integer codFaltaPeriodo, @RequestParam String estado, @RequestParam MultipartFile archivo) {
	        return objServices.getById(codigo).map(datosGuardados -> {
				Date fechaActual = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fechaFormateada = sdf.format(fechaActual);
				try {
					Date date = sdf.parse(fechaFormateada);
					datosGuardados.setFechaSancion(date);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				datosGuardados.setCodEstudiante(codEstudiante);
	            datosGuardados.setObservacionSancion(observacionSancion);
	            datosGuardados.setCodInstructor(codInstructor);
	            datosGuardados.setCodFaltaPeriodo(codFaltaPeriodo);
	            datosGuardados.setEstado(estado);
	            Sanciones datosActualizados;
				try {
					datosActualizados = objServices.update(datosGuardados, archivo);
				} catch (DataException | ArchivoMuyGrandeExcepcion | IOException e) {
					return response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
	            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
	        }).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	 
	 
	 @DeleteMapping("/{id}")
		public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) {
			objServices.delete(codigo);
			return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
		}
	    
	    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
	        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
	                message), httpStatus);
	    }
}
