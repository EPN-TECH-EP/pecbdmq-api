package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

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
import epntech.cbdmq.pe.dominio.admin.ResultadoPrueba;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.ResultadoPruebaServiceImpl;

@RestController
@RequestMapping("/resultadoprueba")
public class ResultadoPruebaResource {

	@Autowired
	private ResultadoPruebaServiceImpl objService;
	
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody ResultadoPrueba obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<ResultadoPrueba> listar() {
		return objService.getAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity<ResultadoPrueba> obtenerPorId(@PathVariable("id") Integer codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResultadoPrueba> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ResultadoPrueba obj) throws DataException{
		return (ResponseEntity<ResultadoPrueba>) objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCodResulPrueba(obj.getCodResulPrueba());
			datosGuardados.setCodFuncionario(obj.getCodFuncionario());
			datosGuardados.setCodEstudiante(obj.getCodEstudiante());
			datosGuardados.setCodModulo(obj.getCodModulo());
			datosGuardados.setCodPostulante(obj.getCodPostulante());
			datosGuardados.setCodPeriodoEvaluacion(obj.getCodPeriodoEvaluacion());
			datosGuardados.setCodPersonalOpe(obj.getCodPersonalOpe());
			datosGuardados.setCodPrueba(obj.getCodPrueba());
			datosGuardados.setCodParametrizaFisica(obj.getCodParametrizaFisica());
			datosGuardados.setCodTipoPrueba(obj.getCodTipoPrueba());
			datosGuardados.setResultado(obj.getResultado());
			datosGuardados.setCumplePrueba(obj.getCumplePrueba());
			
			datosGuardados.setEstado(obj.getEstado());
			ResultadoPrueba datosActualizados = null;
			try {
				datosActualizados = objService.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
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
