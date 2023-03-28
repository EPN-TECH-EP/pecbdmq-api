package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.ZIP_EXITO;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.util.Compress;

@RestController
@RequestMapping("/compress")
public class CompressResource {

	@Autowired
	private Compress compress;
	
	@PostMapping("/zip")
	public ResponseEntity<HttpResponse> zipFolder(@RequestParam("ruta") String ruta) throws DataException, IOException{
		try {
			compress.zip(ruta);
			return response(HttpStatus.OK, ZIP_EXITO);
		}
		catch(IOException e){
			return response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
	}
	
	@GetMapping("/size")
	public ResponseEntity<HttpResponse> folderSize(@RequestParam("ruta") String ruta) throws DataException, IOException{
		return response(HttpStatus.OK, String.valueOf(compress.getFolderSize(ruta)));
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
