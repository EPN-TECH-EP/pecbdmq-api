package epntech.cbdmq.pe.resource;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/unidadgestion")
//@CrossOrigin(origins = "${cors.urls}")
public class UnidadGestionResource extends GestorExcepciones{

	@Autowired
	private UnidadGestionServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public UnidadGestion guardarUnidadGestion(@RequestBody UnidadGestion obj) throws DataException {
		return objService.saveUnidadGestion(obj);
	}
	
	@GetMapping("/listar")
	public List<UnidadGestion> listarDatos(){
		return objService.getAllUnidadGestion();
	}
	
	@GetMapping("/{id}")
	public Optional<UnidadGestion> listarById(@PathVariable("id") int codigo){
		return objService.getUnidadGestionById(codigo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UnidadGestion> actualizarDatos(@PathVariable("id") int codigo, @RequestBody UnidadGestion obj) throws DataException {
		UnidadGestion datosActualizados = objService.updateUnidadGestion(obj);
		return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
	}
	
	@PutMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") int codigo) {
		objService.deleteUnidadGestion(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
