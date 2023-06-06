package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Baja;
import epntech.cbdmq.pe.dominio.admin.Delegado;
import epntech.cbdmq.pe.dominio.util.DelegadoPK;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.DelegadoServiceImpl;

@RestController
@RequestMapping("/delegado")
//@CrossOrigin(origins = "${cors.urls}")
public class DelegadoResource {
	
	@Autowired
	private DelegadoServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Delegado obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Delegado> listar() {
		return objService.getAll();
	}

	
	@DeleteMapping()
	public ResponseEntity<HttpResponse> eliminarDatos(@RequestBody Delegado obj) throws DataException {
			objService.delete(obj.getCodUsuario(), obj.getCodPeriodoAcademico());
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Delegado> obtenerUsuarioPorId(@PathVariable("id") Integer codUsuario) throws DataException {
        return objService.getByIdUsuario(codUsuario).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
	
	
	

}
