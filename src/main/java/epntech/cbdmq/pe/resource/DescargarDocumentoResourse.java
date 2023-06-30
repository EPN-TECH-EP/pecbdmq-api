package epntech.cbdmq.pe.resource;


import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_NO_EXISTE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import jakarta.servlet.http.HttpServletRequest;

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
			return response(HttpStatus.BAD_REQUEST, ARCHIVO_NO_EXISTE);
		}
		// Crear un objeto Resource para el archivo
		File file = new File(archivo.getRuta());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		// Obtener la extensión del archivo
		Path filePath = Paths.get(archivo.getNombre());
		String extension = filePath.getFileName().toString();
		if (extension.lastIndexOf('.') != -1) {
			extension = extension.substring(extension.lastIndexOf('.') + 1);
		}
		// Determinar el tipo de contenido según la extensión del archivo
		String contentType;
		if ("pdf".equalsIgnoreCase(extension)) {
			contentType = "application/pdf";
		} else if ("xlsx".equalsIgnoreCase(extension)||"xls".equalsIgnoreCase(extension)) {
			contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else if ("doc".equalsIgnoreCase(extension) || "docx".equalsIgnoreCase(extension)) {
			contentType = "application/msword";
		} else {
			// Tipo de archivo desconocido
			return response(HttpStatus.BAD_REQUEST, "Tipo de archivo no compatible");
		}
		// Construir la URL completa de descarga del archivo
		String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
		String downloadUrl = baseUrl + "/archivo/" + archivo.getCodDocumento();
		// Devolver una respuesta con el archivo adjunto y la URL de descarga
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getNombre() + "\"")
				.contentType(MediaType.parseMediaType(contentType))
				.body(resource);
	}




	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus , String message){
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
				message), httpStatus);

	}
}


