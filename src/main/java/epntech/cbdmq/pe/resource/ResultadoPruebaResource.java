package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.ResponseMessage.ERROR_GENERAR_ARCHIVO;
import static epntech.cbdmq.pe.constante.ResponseMessage.EXITO_GENERAR_ARCHIVO;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	@PostMapping("/generar")
	public ResponseEntity<?> generar(HttpServletResponse response, @RequestParam("subTipoPrueba") Integer subTipoPrueba)
			throws DocumentException, DataException {
		try {


			objService.generarArchivoAprobados(response, subTipoPrueba);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
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
