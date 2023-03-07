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
import epntech.cbdmq.pe.dominio.admin.Notas;
import epntech.cbdmq.pe.dominio.admin.Parametrizacion;

import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.NotasServiceImpl;


@RestController
@RequestMapping("/nota")
public class NotaResource {
	
	@Autowired
	private NotasServiceImpl objService;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Notas obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Notas> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Notas> obtenerPorId(@PathVariable("id") Integer codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Notas> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody Notas obj) throws DataException{
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCod_nota_formacion(obj.getCod_nota_formacion());
			datosGuardados.setCod_instructor(obj.getCod_instructor());
			datosGuardados.setCod_curso_especializacion(obj.getCod_curso_especializacion());
			datosGuardados.setCod_ponderacion(obj.getCod_ponderacion());
			datosGuardados.setCod_estudiante(obj.getCod_estudiante());
			datosGuardados.setCod_materia(obj.getCod_materia());
			datosGuardados.setAporteexamen(obj.getAporteexamen());
			datosGuardados.setNotafinalformacion(obj.getNotafinalformacion());
			datosGuardados.setFechacreanota(obj.getFechacreanota());
			datosGuardados.setHoracreanota(obj.getHoracreanota());			
			datosGuardados.setUsuariomodnota(obj.getUsuariocreanota());
			datosGuardados.setFechamodnota(obj.getFechamodnota());
			datosGuardados.setHoramodnota(obj.getHoramodnota());
			datosGuardados.setEstado(obj.getEstado());
			
			Notas datosActualizados = null;
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
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) throws DataException {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
	
}
