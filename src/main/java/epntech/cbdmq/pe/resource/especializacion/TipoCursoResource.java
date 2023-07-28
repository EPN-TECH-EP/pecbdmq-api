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
import epntech.cbdmq.pe.servicio.impl.especializacion.TipoCursoServiceImpl;

@RestController
@RequestMapping("/tipoCurso")
public class TipoCursoResource {
	
	@Autowired
	private TipoCursoServiceImpl tipoCursoServiceImpl;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody TipoCurso obj) {
		return new ResponseEntity<>(tipoCursoServiceImpl.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<TipoCurso> listar() {
		return tipoCursoServiceImpl.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoCurso> obtenerPorId(@PathVariable("id") long codigo) {
		return new ResponseEntity<>(tipoCursoServiceImpl.getById(codigo), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/{id}")
	public ResponseEntity<TipoCurso> actualizarDatos(@PathVariable("id") long codigo, @RequestBody TipoCurso obj) {
		obj.setCodTipoCurso(codigo);
		return new ResponseEntity<>(tipoCursoServiceImpl.update(obj), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) {
		tipoCursoServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

}
