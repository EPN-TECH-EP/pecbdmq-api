package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.NO_ADJUNTO;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SEND;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.List;

import org.postgresql.util.PSQLException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.InscripcionEspServiceImpl;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/inscripcionEsp")
public class InscripcionEspResource {

	@Autowired
	private InscripcionEspServiceImpl inscripcionEspServiceImpl;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody InscripcionEsp inscripcionEsp) throws DataException {

		return new ResponseEntity<>(inscripcionEspServiceImpl.save(inscripcionEsp), HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<InscripcionEsp> actualizarDatos(@RequestBody InscripcionEsp inscripcionEsp) throws DataException{
		
		return new ResponseEntity<>(inscripcionEspServiceImpl.update(inscripcionEsp), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<InscripcionDatosEspecializacion> listar() {
		return inscripcionEspServiceImpl.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<InscripcionDatosEspecializacion> obtenerPorId(@PathVariable("id") long codigo) throws DataException {
		return inscripcionEspServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {
		inscripcionEspServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@PostMapping("/uploadDocumentos")
	public ResponseEntity<List<Documento>> uploadFiles(@RequestParam("codInscripcion") Long codInscripcion, @RequestParam("codTipoDocumento") Long codTipoDocumento, @RequestParam("archivos") List<MultipartFile> archivos) throws IOException, ArchivoMuyGrandeExcepcion, DataException {
		
		if(archivos.get(0).getSize() == 0)
			throw new DataException(NO_ADJUNTO);
		
		return new ResponseEntity<>(inscripcionEspServiceImpl.uploadFiles(codInscripcion, codTipoDocumento, archivos), HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminarDocumento")
	public ResponseEntity<HttpResponse> eliminarArchivo(@RequestParam Long codInscripcion, @RequestParam Long codDocumento)
			throws IOException, DataException {

		inscripcionEspServiceImpl.deleteDocumento(codInscripcion, codDocumento);
		
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@PostMapping("/notificar")
	public ResponseEntity<?> notificar(@RequestParam("codInscripcion") Long codInscripcion)
			throws MessagingException, DataException, PSQLException {

		
		inscripcionEspServiceImpl.notificarInscripcion(codInscripcion);

		return response(HttpStatus.OK, EMAIL_SEND);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
