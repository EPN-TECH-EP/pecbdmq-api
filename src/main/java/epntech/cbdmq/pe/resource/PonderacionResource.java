package epntech.cbdmq.pe.resource;

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
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoSemestreModulo;
import epntech.cbdmq.pe.dominio.admin.Ponderacion;
import epntech.cbdmq.pe.dominio.admin.PonderacionModulos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import epntech.cbdmq.pe.servicio.impl.PonderacionServiceImpl;


@RestController
@RequestMapping("/ponderacion")
public class PonderacionResource {

	@Autowired
	private PonderacionServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Ponderacion obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Ponderacion> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ponderacion> obtenerPorId(@PathVariable("id") Integer codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Ponderacion> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody Ponderacion obj) throws DataException{
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCod_modulo(obj.getCod_modulo());
			datosGuardados.setCod_periodo_academico(obj.getCod_periodo_academico());
			datosGuardados.setCod_componente_nota(obj.getCod_componente_nota());
			datosGuardados.setCod_tipo_nota(obj.getCod_tipo_nota());
			
			datosGuardados.setPorcentajefinalponderacion(obj.getPorcentajefinalponderacion());
			
			datosGuardados.setFechainiciovigencia(obj.getFechainiciovigencia());
			datosGuardados.setFechainiciovigencia(obj.getFechafinvigencia());
			datosGuardados.setEstado(obj.getEstado());
			
			
			Ponderacion datosActualizados = null;
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
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	 private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
	        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
	                message), httpStatus);
	    }
	 
	 @GetMapping("/listartodo")
		public List<PonderacionModulos> listarTodo() {
			return objService.getPonderacionModulos();
		}
	
	 
}
