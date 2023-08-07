package epntech.cbdmq.pe.resource.especializacion;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.especializacion.DelegadoEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.DelegadoUtilEsp;
import epntech.cbdmq.pe.servicio.especializacion.DelegadoEspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

@RestController
@RequestMapping("/delegadoEsp")
public class DelegadoEspResource {
	
	@Autowired
	private DelegadoEspService objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody DelegadoEsp obj){
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<DelegadoEsp> listar() {
		return objService.getAll();
	}

	
	@DeleteMapping("/{codDelegado}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("codDelegado") Long codDelegado) {
		objService.delete(codDelegado);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DelegadoEsp> obtenerUsuarioPorId(@PathVariable("id") Integer codUsuario) {
		return new ResponseEntity<>(objService.getByIdUsuario(codUsuario), HttpStatus.OK);
    }
	
	@GetMapping("/obtenerDelegado")
	public List<DelegadoUtilEsp>ObtenerTodo(){
		return objService.delegado();
	}

	@GetMapping("esDelegado/{id}")
	public Boolean userIsDelegado(@PathVariable("id") Integer codUsuario) {
		return objService.isUsuarioDelegado(codUsuario);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
				message), httpStatus);
	}

}
