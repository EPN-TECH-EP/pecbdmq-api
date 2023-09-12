package epntech.cbdmq.pe.resource;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.excepcion.GestorExcepciones;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.formacion.ResultadoPruebasTodoServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.servicio.impl.ResultadoPruebaServiceImpl;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.ResponseMessage.ERROR_GENERAR_ARCHIVO;
import static epntech.cbdmq.pe.constante.ResponseMessage.EXITO_GENERAR_ARCHIVO;
import static epntech.cbdmq.pe.util.ResponseEntityUtil.response;

@RestController
@RequestMapping("/resultadoPruebaTodo")
@Tag(name = "Resultado Prueba", description = "Servicio para métodos de resultados de pruebas de formación (indistinto si son físicas o no)")
public class ResultadoPruebaTodoResource {

	@Autowired
	private ResultadoPruebaServiceImpl objService;

	@Autowired
	private ResultadoPruebasTodoServiceImpl resultadoPruebasTodoServiceImpl;

	@GetMapping("/resultadosPaginado")
	public ResponseEntity<?> getResultados(Pageable pageable, @RequestParam("subTipoPrueba") Integer subTipoPrueba) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(resultadoPruebasTodoServiceImpl.getResultados(pageable, subTipoPrueba));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}

	// genera los documentos pdf y excel de los aprobados por prueba de formación
	@PostMapping("/generar")
	public ResponseEntity<?> generar(HttpServletResponse response, @RequestParam("subTipoPrueba") Integer subTipoPrueba)
			throws DocumentException, DataException {
		try {
			objService.generarArchivoAprobados(response, subTipoPrueba, null);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
	}
	@PostMapping("/generarReprobados")
	public ResponseEntity<?> generarReprobados(HttpServletResponse response, @RequestParam("subTipoPrueba") Integer subTipoPrueba)
			throws DocumentException, DataException {
		try {
			objService.generarArchivoReprobados(response, subTipoPrueba, null);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
	}

	// generaExcel de resultados por prueba

	@GetMapping("descargarExcel")
	public ResponseEntity<?> descargarExcel(@RequestParam Integer id, @RequestParam String nombre, HttpServletRequest request) throws IOException {


		ByteArrayOutputStream fos = this.resultadoPruebasTodoServiceImpl.generarExcel(id);

		String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

		// Devolver una respuesta con el archivo adjunto y la URL de descarga
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombre + ".xlsx\"")
				.contentType(MediaType.parseMediaType(contentType))
				.body(fos.toByteArray());
	}


	//////////////////////
	// cursos
	//////////////////////

	// genera los documentos pdf y excel de los aprobados por prueba de formación
	@PostMapping("/generarParaCurso")
	public ResponseEntity<?> generar(HttpServletResponse response,
									 @RequestParam(value = "subTipoPrueba", required = false) Integer subTipoPrueba,
									 @RequestParam("codCurso") Integer codCurso)
			throws DocumentException, DataException {
		try {
			objService.generarArchivoAprobados(response, subTipoPrueba, codCurso);
		} catch (IOException e) {
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
	}


	@GetMapping("/resultadosPaginadoCurso")
	public ResponseEntity<?> getResultadosCurso(Pageable pageable, @RequestParam("subTipoPrueba") Integer subTipoPrueba, @RequestParam("codCurso") Integer codCurso) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(resultadoPruebasTodoServiceImpl.getResultados(pageable, subTipoPrueba, codCurso));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, GestorExcepciones.ERROR_INTERNO_SERVIDOR);
		}
	}

	@GetMapping("descargarExcelCurso")
	public ResponseEntity<?> descargarExcelCurso(@RequestParam Integer id, @RequestParam String nombre, @RequestParam Integer codCurso, HttpServletRequest request) throws IOException {


		ByteArrayOutputStream fos = this.resultadoPruebasTodoServiceImpl.generarExcelCurso(id, codCurso);

		String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

		// Devolver una respuesta con el archivo adjunto y la URL de descarga
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombre + ".xlsx\"")
				.contentType(MediaType.parseMediaType(contentType))
				.body(fos.toByteArray());
	}

	// generaPdf de resultados por prueba

	
	/*@PostMapping("/crear")
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
*/
	
	
	
}
