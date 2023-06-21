package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.JuntaFormacion;
import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCurso;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.JuntaFormacionServiceImpl;

@RestController
@RequestMapping("/juntaFormacion")
public class JuntaFormacionResource {
	@Autowired
	private JuntaFormacionServiceImpl juntaFormacionServiceImpl;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestParam("archivos") List<MultipartFile> archivos,
			@RequestParam("codTipoDocumento") Long codTipoDocumento) throws DataException, IOException, ArchivoMuyGrandeExcepcion {
		return new ResponseEntity<>(juntaFormacionServiceImpl.save(archivos, codTipoDocumento), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<JuntaFormacion> listar() {
		return juntaFormacionServiceImpl.getDocumentos();
	}
	
	@DeleteMapping("/eliminarDocumento/{id}")
	public ResponseEntity<HttpResponse> eliminarArchivo(@PathVariable("id") long codigo)
			throws IOException, DataException {

		juntaFormacionServiceImpl.delete(codigo);
		
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
