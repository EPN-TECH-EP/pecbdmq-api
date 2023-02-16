package epntech.cbdmq.pe.resource;

import java.util.List;

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
import epntech.cbdmq.pe.servicio.impl.UnidadGestionServiceImpl;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/unidadgestion")
//@CrossOrigin(origins = "${cors.urls}")
public class UnidadGestionResource {

	@Autowired
	private UnidadGestionServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public UnidadGestion guardarUnidadGestion(@RequestBody UnidadGestion obj) {
		return objService.saveUnidadGestion(obj);
	}
	
	@GetMapping("/listar")
	public List<UnidadGestion> listarDatos(){
		return objService.getAllUnidadGestion();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UnidadGestion> actualizarDatos(@PathVariable("id") int codigo, @RequestBody UnidadGestion obj){
		return objService.getUnidadGestionById(codigo).map(datosGuardados -> {
			datosGuardados.setNombre(obj.getNombre());
			
			UnidadGestion datosActualizados = objService.updateUnidadGestion(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/eliminar/{id}")
	public ResponseEntity<UnidadGestion> eliminarDatos(@PathVariable("id") int codigo, @RequestBody UnidadGestion obj){
		return objService.getUnidadGestionById(codigo).map(datosGuardados -> {
			datosGuardados.setNombre(obj.getNombre());
			
			UnidadGestion datosActualizados = objService.updateUnidadGestion(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
