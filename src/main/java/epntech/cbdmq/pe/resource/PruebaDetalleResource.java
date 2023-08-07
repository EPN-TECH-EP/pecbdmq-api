package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

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
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleOrden;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PruebaDetalleEntityRepository;
import epntech.cbdmq.pe.servicio.impl.PruebaDetalleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/pruebadetalle")
public class PruebaDetalleResource {

	@Autowired
	private PruebaDetalleServiceImpl objService;
	@Autowired
	private PruebaDetalleEntityRepository pruebaDetalleEntityRepository;

	@PostMapping("/crear")
	@Operation(summary = "Registra la prueba para el período académico de formación activo")
	public ResponseEntity<?> guardar(@RequestBody PruebaDetalle obj) throws DataException {
		return new ResponseEntity<>(objService.save(obj), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") int codigo) {
		return new ResponseEntity<>(objService.getById(codigo), HttpStatus.OK);
	}

	@GetMapping("/tipoResultado/{id}")
	public String obtenerTipoResultado(@PathVariable("id") int codigo) {
		return objService.getTipoResultado(codigo);
	}

	@GetMapping("/listar")
	public List<PruebaDetalle> listar() {
		return objService.getAll();
	}

	@GetMapping("/listarConDatos")
	@Operation(summary = "Lista de todas las pruebas configuradas para el período académico de formación activo")
	public List<PruebaDetalleDatos> listarTodosConDatosSubTipoPrueba() {
		return this.objService.listarTodosConDatosSubTipoPrueba();
	}

	@PutMapping("/{id}")
	public ResponseEntity<PruebaDetalle> actualizarDatos(@PathVariable("id") int codigo, @RequestBody PruebaDetalle obj)
			throws DataException {
		obj.setCodPruebaDetalle(codigo);
		return new ResponseEntity<>(objService.update(obj), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
						message),
				httpStatus);
	}

	@PostMapping("/reordenar")
	public Boolean reordenar(@RequestBody List<PruebaDetalleOrden> listaOrden) throws DataException {
		return this.objService.reordenar(listaOrden);
	}

	// especialización
	@GetMapping("/listarConDatos/{codCurso}")
	@Operation(summary = "Lista de todas las pruebas configuradas para el curso con id")
	public List<PruebaDetalleDatos> listarTodosConDatosSubTipoPrueba(@PathVariable("codCurso") Integer codCurso) {
		return this.objService.listarTodosConDatosSubTipoPrueba(codCurso);
	}
}