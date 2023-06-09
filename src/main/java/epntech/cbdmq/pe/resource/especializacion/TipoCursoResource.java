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
import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.TipoCursoServiceImpl;

@RestController
@RequestMapping("/tipoCurso")
public class TipoCursoResource {
	
	@Autowired
	private TipoCursoServiceImpl tipoCursoServiceImpl;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody TipoCurso obj) throws DataException{
		return new ResponseEntity<>(tipoCursoServiceImpl.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<TipoCurso> listar() {
		return tipoCursoServiceImpl.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoCurso> obtenerPorId(@PathVariable("id") long codigo) {
		return tipoCursoServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/{id}")
	public ResponseEntity<TipoCurso> actualizarDatos(@PathVariable("id") long codigo, @RequestBody TipoCurso obj) throws DataException{
		
	
		return (ResponseEntity<TipoCurso>) tipoCursoServiceImpl.getById(codigo).map(datosGuardados -> {
			datosGuardados.setNombreTipoCurso(obj.getNombreTipoCurso().toUpperCase());
			datosGuardados.setEstado(obj.getEstado());

			TipoCurso datosActualizados = null;
			try {
				datosActualizados = tipoCursoServiceImpl.update(datosGuardados);
			} catch (DataException e) {
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {

		tipoCursoServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

}
