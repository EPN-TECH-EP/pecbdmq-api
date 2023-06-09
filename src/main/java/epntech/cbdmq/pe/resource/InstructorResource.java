package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.EXITO;

import java.util.List;

import epntech.cbdmq.pe.dominio.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Instructor;
import epntech.cbdmq.pe.dominio.admin.InstructorMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.InstructorServiceImpl;

import static epntech.cbdmq.pe.constante.MensajesConst.NO_ENCUENTRA;

@RestController
@RequestMapping("/instructor")
public class InstructorResource {

	@Autowired
	private InstructorServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Instructor obj) throws DataException {
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Instructor> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Instructor> obtenerPorId(@PathVariable("id") Integer codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Instructor> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody Instructor obj)
			throws DataException {
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCodInstructor(obj.getCodInstructor());
			datosGuardados.setCodTipoProcedencia(obj.getCodTipoProcedencia());
			/*datosGuardados.setCod_tipo_instructor(obj.getCod_tipo_instructor());
			datosGuardados.setCod_periodo_academico(obj.getCod_tipo_instructor());
			datosGuardados.setCod_periodo_academico(obj.getCod_periodo_academico());*/
			Instructor datosActualizados = objService.update(datosGuardados);
			datosActualizados = objService.update(datosGuardados);
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") Integer codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente", HttpStatus.OK);
	}
	//TODO la clase instructor no esta acorde a la ultima BD
	@PostMapping("/ByUser")
	public Instructor listarInstructorByUsuario(@RequestParam("codUsuario")String codUsuario) throws DataException {
		Instructor instructor= objService.getInstructorByUser(codUsuario);
		if(instructor==null){
			throw new DataException(NO_ENCUENTRA);
		}
		return instructor;
	}
	
	@PostMapping("/asignarMaterias")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> asignarMaterias(@RequestBody List<InstructorMateria> lista) throws DataException{
		objService.saveAllMaterias(lista);
		return response(HttpStatus.OK, EXITO);
	    }
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
	@PostMapping("/listarFormHistorico")
	public List<FormacionInstructor> listarFormHistorico(@RequestParam("codInstructor") Integer codInstructor, Pageable pageable) {
		return objService.getFormHistoricos(codInstructor, pageable);
	}

	@PostMapping("/listarEspHistorico")
	public List<EspecializacionInstructor> listarEspHistorico(@RequestParam("codInstructor") Integer codInstructor, Pageable pageable) {
		return objService.getEspecializacionHistoricos(codInstructor, pageable);
	}

	@PostMapping("/listarProfHistorico")
	public List<ProfesionalizacionInstructor> listarProfHistorico(@RequestParam("codInstructor") Integer codInstructor, Pageable pageable) {
		return objService.getProfesionalizacionHistoricos(codInstructor, pageable);
	}
	
}
