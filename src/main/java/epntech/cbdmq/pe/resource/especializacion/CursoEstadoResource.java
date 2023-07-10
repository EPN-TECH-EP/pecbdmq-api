package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoEstado;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.CursoEstadoServiceImpl;

@RestController
@RequestMapping("/cursoEstado")
public class CursoEstadoResource {

	@Autowired
	private CursoEstadoServiceImpl cursoEstadoServiceImpl;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody CursoEstado obj) throws DataException{
		return new ResponseEntity<>(cursoEstadoServiceImpl.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<CursoEstado> listar() {
		return cursoEstadoServiceImpl.listarTodo();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoEstado> obtenerPorId(@PathVariable("id") long codigo) throws DataException {
		return cursoEstadoServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<CursoEstado> actualizarDatos(@PathVariable("id") long codigo, @RequestBody CursoEstado obj) throws DataException{
		return (ResponseEntity<CursoEstado>) cursoEstadoServiceImpl.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCodCatalogoEstados(obj.getCodCatalogoEstados());
			datosGuardados.setCodCursoEspecializacion(obj.getCodCursoEspecializacion());
			datosGuardados.setOrden(obj.getOrden());
			datosGuardados.setEstado(obj.getEstado());

			CursoEstado datosActualizados = null;
			try {
				datosActualizados = cursoEstadoServiceImpl.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				return response(HttpStatus.BAD_REQUEST, e.getMessage());
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {

		cursoEstadoServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
