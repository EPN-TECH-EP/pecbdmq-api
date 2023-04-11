package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Estados;
import epntech.cbdmq.pe.dominio.admin.ModuloEstados;
import epntech.cbdmq.pe.dominio.admin.ModuloEstadosData;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.ModuloEstadosServiceImpl;

@RestController
@RequestMapping("/moduloestados")
public class ModuloEstadosResource {
	@Autowired
	private ModuloEstadosServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody ModuloEstados obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@PostMapping("/crearall")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardarAll(@RequestBody List<ModuloEstados> obj) throws DataException{
		return new ResponseEntity<>(objService.saveAll(obj), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarDatos(@PathVariable("id") int id, @RequestBody ModuloEstados obj) throws DataException {
		Optional<ModuloEstados> dato = objService.getById(id);

        if (dato.isPresent()) {
        	ModuloEstados datosActualizados = dato.get();
        	datosActualizados.setEstadoCatalogo(obj.getEstadoCatalogo());
        	datosActualizados.setModulo(obj.getModulo());
        	datosActualizados.setOrden(obj.getOrden());
        	datosActualizados.setEstado(obj.getEstado());

        	return new ResponseEntity<>(objService.save(datosActualizados), HttpStatus.OK);
        } else {
            return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
        }
	}
	
	@GetMapping("/listar")
	public List<ModuloEstados> listar() {
		return objService.getAll();
	}
	
	@GetMapping("/listartodo")
	public List<ModuloEstadosData> listarAll() {
		return objService.getAllData();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") int id) {
		Optional<ModuloEstados> dato = objService.getById(id);

        if (dato.isPresent()) {
        	return objService.getById(id).map(ResponseEntity::ok)
    				.orElseGet(() -> ResponseEntity.notFound().build());
        }else {
            return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
        }
		
	}

	@GetMapping("/moduloyestado")
	public ResponseEntity<?> obtenerPorModuloEstado(@RequestParam("modulo") int modulo, @RequestParam("estado") int estado) {
		
		List<ModuloEstados> result = new ArrayList<>();
		result = objService.getByModuloEstado(modulo, estado);
		
		if (!result.isEmpty())
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return response(HttpStatus.OK, REGISTRO_NO_EXISTE);
	}
	
	@GetMapping("/bymodulo")
	public ResponseEntity<?> obtenerPorModulo(@RequestParam("modulo") int modulo) {
		List<ModuloEstadosData> result = new ArrayList<>();
		result = objService.getByModulo(modulo);
		
		if (!result.isEmpty())
		return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return response(HttpStatus.OK, REGISTRO_NO_EXISTE);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
