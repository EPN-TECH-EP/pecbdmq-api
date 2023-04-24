package epntech.cbdmq.pe.resource;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.MediaType;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.servicio.impl.DocumentoServiceimpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/link")

public class DescargarDocumentoResourse {

	@Autowired
	DocumentoRepository Repository;
	
	
	 
	 @GetMapping("/{id}")
	 public ResponseEntity<?> descargarArchivo(@PathVariable Integer id, HttpServletRequest request) throws FileNotFoundException {
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
	     String downloadUrl = baseUrl + "/archivo/" + archivo.getCodigo();
	     // Devolver una respuesta con el archivo adjunto y la URL de descarga
	     return ResponseEntity.ok()
	             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getRuta() + "\"")
	             .contentType(MediaType.parseMediaType("application/pdf"))
	             
	             .body(resource);
	 }
	 
	
	
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus , String message){
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
		
	}
}
	 

