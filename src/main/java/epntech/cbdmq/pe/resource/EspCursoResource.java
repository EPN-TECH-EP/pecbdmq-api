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
import epntech.cbdmq.pe.dominio.admin.EspCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EspCursoServiceImpl;

@RestController
@RequestMapping("/espcurso")
public class EspCursoResource {

	
	@Autowired
	private EspCursoServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody EspCurso obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<EspCurso> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<EspCurso> obtenerPorId(@PathVariable("id") Integer codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<EspCurso> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody EspCurso obj) throws DataException{
		return (ResponseEntity<EspCurso>) objService.getById(codigo).map(datosGuardados -> {
			//datosGuardados.setCod_estudiante(obj.getCod_curso_especializacion());
			datosGuardados.setCod_instructor(obj.getCod_instructor());
			datosGuardados.setCod_unidad_gestion(obj.getCod_unidad_gestion());
			//datosGuardados.setCod_estudiante(obj.getCod_estudiante());
			datosGuardados.setCod_aula(obj.getCod_aula());
			datosGuardados.setNumero_cupo(obj.getNumero_cupo());
			datosGuardados.setFechainiciocurso(obj.getFechainiciocurso());
			datosGuardados.setFechafincurso(obj.getFechafincurso());
			datosGuardados.setFechainiciocarganota(obj.getFechainiciocarganota());
			datosGuardados.setFechafincarganota(obj.getFechafincarganota());
			datosGuardados.setNota_minima(obj.getNota_minima());
			datosGuardados.setAprueba(obj.getAprueba());
			datosGuardados.setEstado_proceso(obj.getEstado_proceso());
			datosGuardados.setEstado(obj.getEstado());
			datosGuardados.setCod_tipo_curso(obj.getCod_tipo_curso());
			
			EspCurso datosActualizados = null;
			try {
				datosActualizados = objService.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				
				//e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
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
}
