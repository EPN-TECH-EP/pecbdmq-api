package epntech.cbdmq.pe.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.servicio.impl.DocumentoServiceimpl;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/link")
public class LinkResource {

	@Autowired
	private DocumentoServiceimpl objService;

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") int id) {
		return response(HttpStatus.OK, objService.getById(id).get().getRuta());
	}
	
	@GetMapping("/descargar")
    public void descargarArchivo(HttpServletResponse response, @PathVariable("id") int id) {
        
        File archivo = new File(objService.getById(id).get().getRuta());
        
        try (InputStream inputStream = new FileInputStream(archivo)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + archivo.getName() + "\"");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
	
	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> downloadFile(HttpServletRequest request, @PathVariable("id") int id) throws IOException {

	    // Load file as Resource
	    Resource resource = new ClassPathResource(objService.getById(id).get().getRuta());

	    // Try to determine file's content type
	    String contentType = null;
	    try {
	      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	    } catch (IOException ex) {
	      //log error
	    }

	    // Fallback to the default content type if type could not be determined
	    if(contentType == null) {
	      contentType = "application/octet-stream";
	    }
	    
	    System.out.println("resource.getFilename(): " + resource.getFilename());

	    System.out.println(new ClassPathResource("\\pfb21.pdf").getFile().getAbsolutePath());
	    
	    return ResponseEntity.ok()
	        .contentType(MediaType.parseMediaType(contentType))
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	        .body(resource);
	  }
	
	
}
