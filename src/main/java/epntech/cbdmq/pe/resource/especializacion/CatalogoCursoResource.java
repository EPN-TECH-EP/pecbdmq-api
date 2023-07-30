package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.CatalogoCursoServiceImpl;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/catalogoCurso")
public class CatalogoCursoResource {

	@Autowired
	private CatalogoCursoServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@Valid @RequestBody CatalogoCurso obj) throws DataException {
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	 
	@GetMapping("/listar")
	public List<CatalogoCurso> listar() {
		return objService.getAll();
	}

	@GetMapping("/listarPorTipoCurso")
	public List<CatalogoCurso> listar(@RequestParam Integer codigoTipoCurso) {
		return objService.getByCodigoTipoCurso(codigoTipoCurso);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CatalogoCurso> obtenerPorId(@PathVariable("id") Integer codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CatalogoCurso> actualizarDatos(@PathVariable("id") Integer codigo,
			@Valid @RequestBody CatalogoCurso obj) {
		obj.setCodCatalogoCursos(codigo);
		return new ResponseEntity<>(objService.update(obj), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) throws DataException {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}

}
