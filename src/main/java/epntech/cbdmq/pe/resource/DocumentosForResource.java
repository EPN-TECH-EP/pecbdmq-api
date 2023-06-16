package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.EXITO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.DocumentoServiceimpl;
import epntech.cbdmq.pe.servicio.impl.PerDocumentosForServiceImpl;
import epntech.cbdmq.pe.servicio.impl.PeriodoAcademicoServiceimpl;

@RestController
@RequestMapping("/documentofor")
public class DocumentosForResource {

	@Autowired
	private DocumentoServiceimpl objService;

	@Autowired
	private PerDocumentosForServiceImpl objServices;

	@Autowired
	private PeriodoAcademicoServiceimpl objService2;

	@GetMapping("/documentos")
	public Set<Documento> listarDocumentos() {
		return objServices.getDocumentos();
	}

	@PostMapping("/crearDocs")
	public ResponseEntity<?> guardarArchivo(@RequestParam List<MultipartFile> archivos,
			@RequestParam String descripcion, @RequestParam String observacion) throws Exception {
		objService2.cargarDocs(archivos, descripcion, observacion);
		return response(HttpStatus.OK, EXITO);
	}

	@PutMapping(value = "/actualizardocumentoFormacion/{id}")
	public void actualizarDocumentoFor(@PathVariable("id") Integer codDocumento, MultipartFile archivo)
			throws IOException, DataException, ArchivoMuyGrandeExcepcion {
		objServices.actualizarArchivoFormacion(codDocumento, archivo);
	}

	@DeleteMapping(value = "/eliminardocumentoFormacion/{id}")
	public ResponseEntity<HttpResponse> eliminarArchivo(@PathVariable("id") Integer codDocumento)
			throws IOException, DataException {

		try {
			objServices.eliminarArchivoFormacion(codDocumento);
			return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);

		} catch (IOException e) {
			return response(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
}
