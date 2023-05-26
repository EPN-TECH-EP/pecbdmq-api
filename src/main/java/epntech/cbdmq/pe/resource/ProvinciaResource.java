package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.constante.ResponseMessage.CARGA_ARCHIVO_EXCEL;
import static epntech.cbdmq.pe.constante.ResponseMessage.CARGA_EXITOSA;
import static epntech.cbdmq.pe.constante.ResponseMessage.CARGA_NO_EXITOSA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Provincia;
import epntech.cbdmq.pe.dominio.admin.ProvinciaProjection;
import epntech.cbdmq.pe.dominio.util.Excel;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.helper.ProvinciaHelper;
import epntech.cbdmq.pe.servicio.ExcelService;
import epntech.cbdmq.pe.servicio.impl.ProvinciaServiceImpl;

@RestController
@RequestMapping("/provincia")
//@CrossOrigin(origins = "${cors.urls}")
public class ProvinciaResource {

	@Autowired
	private ProvinciaServiceImpl objService;


	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Provincia obj) throws DataException {
		obj.setNombre(obj.getNombre().toUpperCase());
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}

	/*@GetMapping("/listar")
	public List<Provincia> listar() {
		return objService.getAll();
	}*/
	
	@GetMapping("/listar")
	public List<ProvinciaProjection> listar() {
		return objService.findAllParentEntities();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Provincia> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

//	@SuppressWarnings("unchecked")
	@PutMapping("/{id}")
	public ResponseEntity<Provincia> actualizarDatos(@PathVariable("id") int codigo, @RequestBody Provincia obj)
			throws DataException {
		return (ResponseEntity<Provincia>) objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setNombre(obj.getNombre().toUpperCase());
			datosGuardados.setEstado(obj.getEstado());

			Provincia datosActualizados = null;
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
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
		objService.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}

	@PostMapping("/cargar")
	public ResponseEntity<?> uploadFile(@RequestParam("archivo") MultipartFile archivo) {

		if (ProvinciaHelper.hasExcelFormat(archivo)) {
			try {
				objService.saveExcel(archivo);
				
				return response(HttpStatus.OK, CARGA_EXITOSA);
			} catch (Exception e) {
				return response(HttpStatus.EXPECTATION_FAILED, CARGA_NO_EXITOSA);
			}
		}

		return response(HttpStatus.BAD_REQUEST, CARGA_ARCHIVO_EXCEL);
	}
}
