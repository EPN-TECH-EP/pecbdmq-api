package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.query.sqm.tree.update.SqmUpdateStatement;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoSemestreModulo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.PeriodoAcademicoServiceimpl;

@RestController
@RequestMapping("/periodoacademico")
//@CrossOrigin(origins = "${cors.urls}")
public class PeriodoAcademicoResource {
	
	@Autowired
	private PeriodoAcademicoServiceimpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody PeriodoAcademico obj) throws DataException, ParseException{
		Date date = new Date();
		SimpleDateFormat formato1 = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha = formato1.parse(formato1.format(date));
		
		obj.setFechaInicio(fecha);
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
		
	}
	
	@GetMapping("/listar")
	public List<PeriodoAcademico> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PeriodoAcademico> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/{id}")
	public ResponseEntity<PeriodoAcademico> actualizarDatos(@PathVariable("id") int codigo, @RequestBody PeriodoAcademico obj) throws DataException {
		return (ResponseEntity<PeriodoAcademico>) objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setModuloEstados(obj.getModuloEstados());
			datosGuardados.setFechaInicio(obj.getFechaInicio());
			datosGuardados.setFechaFin(obj.getFechaFin());
			datosGuardados.setDescripcion(obj.getDescripcion());
			datosGuardados.setEstado(obj.getEstado());

			PeriodoAcademico datosActualizados = null;
			try {
				datosActualizados = objService.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
		objService.deleteById(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
	
	@GetMapping("/listartodo")
	public List<PeriodoAcademicoSemestreModulo> listarTodo() {
		return objService.getAllPeriodoAcademico();
	}
	
	@GetMapping("/validaestado")
	public ResponseEntity<HttpResponse> getEstado() {
		String result = objService.getEstado();
		
		if (result == null) {
			result = "SIN PERIODO";
		}
		
		return response(HttpStatus.OK, result);
	}
	
	@GetMapping("/siguienteEstado")
	public ResponseEntity<HttpResponse> nextState(@RequestParam("id") Integer id, @RequestParam("proceso") String proceso) {
		String result = objService.updateNextState(id, proceso).toString();
				
		return response(HttpStatus.OK, result);
	}
	
}
