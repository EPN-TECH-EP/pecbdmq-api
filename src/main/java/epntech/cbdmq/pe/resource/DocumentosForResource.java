package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoDocRepository;
import epntech.cbdmq.pe.servicio.impl.DocumentoServiceimpl;
import epntech.cbdmq.pe.servicio.impl.PerDocumentosForServiceImpl;

@RestController
@RequestMapping("/documentofor")
public class DocumentosForResource {

	
	@Autowired
	private DocumentoServiceimpl objService;
	
	@Autowired
	private PerDocumentosForServiceImpl objServices;
	
	
	
	@GetMapping("/documentos")
	public Set<Documento> listarDocumentos() {
		return objServices.getDocumentos();
	}
	
	@PutMapping(value = "/actualizardocumentoFormacion/{id}")
	public void actualizarDocumentoFor(@PathVariable("id") Integer codDocumento, MultipartFile archivo) throws IOException, DataException {
		objServices.actualizarArchivoFromacion(codDocumento, archivo);
	}
	
	@DeleteMapping(value = "/eliminardocumentoFormacion/{id}")
	public ResponseEntity<HttpResponse> eliminarArchivo( @PathVariable("id") Integer codDocumento)
			throws IOException, DataException {

        
		/*try {
			objService.eliminarArchivo(convocatoria,codDocumento);
			return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);*/


		try {
			objServices.eliminarArchivoFromacion(codDocumento);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);

		}catch(IOException e){
			return response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
}
