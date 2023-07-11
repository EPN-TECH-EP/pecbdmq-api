package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalleEntity;
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
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Registra la pruebapara el período académico de formación activo")
	public ResponseEntity<?> guardar(@RequestBody PruebaDetalle obj) throws DataException {
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") int codigo) {
		Optional<PruebaDetalleEntity> pruebaDetalle = pruebaDetalleEntityRepository.findById(codigo);
		if(pruebaDetalle.isEmpty())
			return response(HttpStatus.BAD_REQUEST, REGISTRO_NO_EXISTE);
				
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
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

		PruebaDetalle datosActualizados = null;

		datosActualizados = objService.update(obj);

		return new ResponseEntity<>(datosActualizados, HttpStatus.OK);

		/*
		 * return (ResponseEntity<PruebaDetalle>)
		 * objService.getById(codigo).map(datosGuardados -> {
		 * datosGuardados.setDescripcionPrueba(obj.getDescripcionPrueba());
		 * datosGuardados.setFechaInicio(obj.getFechaInicio());
		 * datosGuardados.setFechaFin(obj.getFechaFin());
		 * datosGuardados.setHora(obj.getHora());
		 * datosGuardados.setEstado(obj.getEstado());
		 * datosGuardados.setPuntajeMinimo(obj.getPuntajeMinimo());
		 * datosGuardados.setPuntajeMaximo(obj.getPuntajeMaximo());
		 * datosGuardados.setTienePuntaje(obj.getTienePuntaje());
		 * 
		 * PruebaDetalle datosActualizados = null;
		 * 
		 * try { datosActualizados = objService.update(obj); } catch (DataException e) {
		 * // TODO Auto-generated catch block //e.printStackTrace(); return
		 * response(HttpStatus.BAD_REQUEST, e.getMessage().toString()); } return new
		 * ResponseEntity<>(datosActualizados, HttpStatus.OK);
		 * 
		 * }).orElseGet(() -> ResponseEntity.notFound().build());
		 */
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
	
	@GetMapping("/listarxCurso/{id}")
	public List<PruebaDetalleDatos> listarPorCurso(@PathVariable("id") long codigo) throws DataException {
		return this.objService.getByCurso(codigo);
	}
}