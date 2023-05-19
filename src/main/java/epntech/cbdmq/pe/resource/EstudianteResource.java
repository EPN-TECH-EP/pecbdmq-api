package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_REGISTRADOS;

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
import epntech.cbdmq.pe.dominio.admin.Estudiante;
import epntech.cbdmq.pe.dominio.util.EstudianteDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EstudianteServiceImpl;

@RestController
@RequestMapping("/estudiante")
public class EstudianteResource {

	@Autowired
	private EstudianteServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Estudiante obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Estudiante> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Estudiante> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	/*@PutMapping("/{id}")
	public ResponseEntity<Estudiante> actualizarDatos(@PathVariable("id") int codigo, @RequestBody Estudiante obj) throws DataException{
		return (ResponseEntity<Estudiante>) objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCodDatosPersonales(obj.getCodDatosPersonales());
			datosGuardados.setCodModulo(obj.getCodModulo());
			datosGuardados.setGrado(obj.getGrado());
			datosGuardados.setResultadoEstudiante(obj.getResultadoEstudiante());
			datosGuardados.setIdEstudiante(obj.getIdEstudiante());
			datosGuardados.setEstado(obj.getEstado());

			Estudiante datosActualizados = null;
			datosActualizados = objService.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}*/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
			objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	/*@GetMapping("/paginado")
	public ResponseEntity<?> listarDatos(Pageable pageable) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.getAllEstudiante(pageable));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, "Error. Por favor intente más tarde.");
		}
	}*/
	
	/*@GetMapping("/listardatos")
	public List<EstudianteDatos> listarAll() {
		return objService.findAllEstudiante();
	}*/
	
	@PostMapping("/crearEstudiantes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardarAll(@RequestParam("modulo") Integer modulo) throws DataException{
		objService.saveEstudiantes(modulo);
		
		return response(HttpStatus.OK, DATOS_REGISTRADOS);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
	
}
