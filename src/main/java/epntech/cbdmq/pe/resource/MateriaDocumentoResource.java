package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.util.MateriaDocumento;
import epntech.cbdmq.pe.dominio.util.forAprobadosValidacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;
import epntech.cbdmq.pe.servicio.impl.DocumentoServiceimpl;
import epntech.cbdmq.pe.servicio.impl.MateriaDocumentoServiceImpl;

@RestController
@RequestMapping("/materiadocumento")
public class MateriaDocumentoResource {

	@Autowired
	private MateriaRepository repo2;
	@Autowired
	private MateriaDocumentoRepository repo;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private MateriaDocumentoServiceImpl objService;
	
	
	@PostMapping("/guardarArchivo")
	public List<DocumentoRuta> guardarArchivo(@RequestParam String proceso, @RequestParam Integer materia, @RequestParam List<MultipartFile> archivo) throws Exception {
		List<DocumentoRuta>lista= new ArrayList<>();
		lista= objService.guardarArchivo(proceso, materia, archivo);
			
			
		
		return lista;
	}
	
	
	@DeleteMapping("/eliminarDocumento")
	public ResponseEntity<HttpResponse> eliminarArchivo(@RequestParam Integer codMateria, @RequestParam Long codDocumento)
			throws IOException, DataException {

		objService.deleteDocumento(codMateria,codDocumento);
		
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
