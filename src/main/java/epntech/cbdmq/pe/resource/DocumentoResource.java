package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.servicio.DocumentoService;
import epntech.cbdmq.pe.servicio.UsuarioService;
import epntech.cbdmq.pe.servicio.impl.DocumentoServiceimpl;

@RestController
@RequestMapping("/documento")
public class DocumentoResource {

	@Autowired
	private DocumentoServiceimpl objService;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Value("${pecb.archivos.ruta}")
	private String directorioDocumentos;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Documento obj) throws DataException {
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<Documento> listar() {
		return objService.listAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Documento> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Documento> actualizarDatos(@PathVariable("id") int codigo, @RequestParam Integer tipo,
			@RequestParam String descripcion, @RequestParam String observacion, @RequestParam MultipartFile archivo)
			throws DataException, ArchivoMuyGrandeExcepcion {

		return (ResponseEntity<Documento>) objService.getById(codigo).map(datosGuardados -> {

			datosGuardados.setTipo(tipo);

			datosGuardados.setDescripcion(descripcion);

			datosGuardados.setObservaciones(observacion);

			Documento datosActualizados = null;
			try {
				datosActualizados = objService.update(datosGuardados, archivo);
			} catch (ArchivoMuyGrandeExcepcion | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage());
			}

			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}

	@PostMapping("/guardarArchivo")
	public List<DocumentoRuta> guardarArchivo(@RequestParam String proceso, @RequestParam String id,
			@RequestParam List<MultipartFile> archivo) throws Exception {
		List<DocumentoRuta> lista = new ArrayList<>();
		try {
			lista = objService.guardarArchivo(proceso, id, archivo);

		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("errorHeader", e.getMessage());

		}
		return lista;
	}

	@DeleteMapping(value = "/eliminardocumentoconvocatoria")
	public ResponseEntity<HttpResponse> eliminarArchivo(@RequestParam Integer convocatoria, @RequestParam Integer codDocumento)
			throws IOException {

		objService.eliminarArchivo(convocatoria,codDocumento);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	/*
	 * @GetMapping("/maxArchivo") public long tamañoMáximoArchivo() {
	 * 
	 * try { return documentoService.tamañoMáximoArchivo(); } catch (Exception e) {
	 * this.LOGGER.error(e.getMessage()); return -1; } }
	 */

}
