package epntech.cbdmq.pe.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.servicio.impl.DocumentoServiceimpl;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/link")

public class DescargarDocumentoResourse {

	
	
	
	 
	/* @GetMapping("/{id}")
	 public ResponseEntity<?> descargarArchivo(@PathVariable Long id, HttpServletRequest request) throws FileNotFoundException {
	     // Buscar el archivo en la base de datos
	     Documento archivo = Repository.findById(id).orElse(null);
	     if (archivo == null) {
	         return ResponseEntity.notFound().build();
	     }
	     // Crear un objeto Resource para el archivo
	     File file = new File(archivo.getRuta());
	     InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	     // Construir la URL completa de descarga del archivo
	     String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
	     String downloadUrl = baseUrl + "/archivo/" + archivo.getId();
	     // Devolver una respuesta con el archivo adjunto y la URL de descarga
	     return ResponseEntity.ok()
	             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getNombre() + "\"")
	             .contentType(MediaType.parseMediaType(archivo.getTipo()))
	             .body(resource)
	             .header("download-url", downloadUrl);
	 }*/
	 
	 /*
	 @Value("${server.base.url}")
	    private String serverBaseUrl;

	    @GetMapping("/file-link")
	    public String getFileLink(@RequestParam String filePath) {
	        // Generar la URL completa al archivo
	        String fileUrl = serverBaseUrl + "/" + filePath;
	        return fileUrl;
	    }*/
	
	
	/*@GetMapping("/descargar")
    public void descargarArchivo(HttpServletResponse response) {
        
        File archivo = new File("/ruta/al/archivo/nombre_archivo.pdf");
        
        try (FileInputStream inputStream = new FileInputStream(archivo)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + archivo.getName() + "\"");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
	
	@Autowired
	private DocumentoServiceimpl objService;

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") int id) {
		return response(HttpStatus.OK, objService.getById(id).get().getRuta());
	}
	
	/*@GetMapping("/descargar")
    public void descargarArchivo(HttpServletResponse response) {
        
        File archivo = new File("C:/pfb21.pdf");
        
        try (FileInputStream inputStream = new FileInputStream(archivo)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + archivo.getName() + "\"");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
	
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus , String message){
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
		
	}
	 
}
