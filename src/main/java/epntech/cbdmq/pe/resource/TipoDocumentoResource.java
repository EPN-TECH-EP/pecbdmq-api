package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.TipoDocumento;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.TipoDocumentoServiceImpl;

@RestController
@RequestMapping("/tipodocumento")
//@CrossOrigin(origins = "${cors.urls}")
public class TipoDocumentoResource {

	@Autowired 
	private TipoDocumentoServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody TipoDocumento obj) throws DataException{
		obj.setTipoDocumento(obj.getTipoDocumento().toUpperCase());
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<TipoDocumento> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoDocumento> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<TipoDocumento> actualizarDatos(@PathVariable("id") int codigo, @RequestBody TipoDocumento obj) throws DataException {
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setTipoDocumento(obj.getTipoDocumento().toUpperCase());
			datosGuardados.setEstado(obj.getEstado());

			TipoDocumento datosActualizados = null;
			try {
				datosActualizados = objService.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
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
