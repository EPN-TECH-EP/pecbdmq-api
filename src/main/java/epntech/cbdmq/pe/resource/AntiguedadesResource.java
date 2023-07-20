package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;
import static epntech.cbdmq.pe.constante.ResponseMessage.ERROR_GENERAR_ARCHIVO;
import static epntech.cbdmq.pe.constante.ResponseMessage.EXITO_GENERAR_ARCHIVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.util.AntiguedadesDatos;
import epntech.cbdmq.pe.dominio.util.AntiguedadesFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.impl.AntiguedadesServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/antiguedades")
public class AntiguedadesResource {

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	
	@Autowired
	private AntiguedadesServiceImpl objService;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;
	@Autowired
	DocumentoRepository repository;

	@GetMapping("/listarFemenino")
	public Set<AntiguedadesDatos> listarFemenino() {
		return objService.getAntiguedadesFemenino();
	}

	@GetMapping("/listarMasculino")
	public Set<AntiguedadesDatos> listarMasculino() {
		return objService.getAntiguedadesMasculino();
	}

	/*genera los archivos con el resultado de antiguedades femenino y masculino del proceso de aspirantes aprobados*/
	@PostMapping("/generarArchivos")
	public ResponseEntity<?> generarArchivos(HttpServletResponse response, @RequestParam("nombre") String nombre, @RequestParam("tipoDocumento") Integer tipoDocumento) throws DataException, DocumentException {
		try {

			String nombreFemenino = "/" + nombre + "-Femenino";
			String nombreMasculino = "/" + nombre + "-Masculino";
			
			String rutaF = ARCHIVOS_RUTA + PATH_RESULTADO_ANTIGUEDADES + periodoAcademicoRepository.getPAActive().toString() + nombreFemenino;
			String rutaM = ARCHIVOS_RUTA + PATH_RESULTADO_ANTIGUEDADES + periodoAcademicoRepository.getPAActive().toString() + nombreMasculino;

			//Todo el tipo de documento pasado siempre sera el 58 que tiene que ver con las antiguedades
			objService.generarExcel(rutaF + ".xlsx", nombreFemenino + ".xlsx", 1, tipoDocumento);
			objService.generarExcel(rutaM + ".xlsx", nombreMasculino + ".xlsx", 0, tipoDocumento);
			objService.generarPDF(response, rutaF + ".pdf", nombreFemenino + ".pdf", 1, tipoDocumento);
			objService.generarPDF(response, rutaM + ".pdf", nombreMasculino + ".pdf", 0, tipoDocumento);
			
			return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
	}
	
	/*obtiene la lista de antiguedades del proceso de formaci�n acad�mica*/
	@GetMapping("/listarAntiguedadesFormacion")
	public Set<AntiguedadesFormacion> listarAntiguedadesFormacion() {
		return objService.getAntiguedadesFormacion();
	}
	
	/*genera los archivos con el resultado de antiguedades femenino y masculino del proceso de aspirantes aprobados*/
	@PostMapping("/generaArchivosAntiguedadesFormacion")
	public ResponseEntity<?> generaArchivosAntiguedadesFormacion(HttpServletResponse response, @RequestParam("tipoDocumento") Integer tipoDocumento) throws DataException, DocumentException {
		try {

			String nombre= ANTIGUEDADESFORMACION+periodoAcademicoRepository.getPAActive().toString();
			String ruta = ARCHIVOS_RUTA + PATH_RESULTADO_ANTIGUEDADES + periodoAcademicoRepository.getPAActive().toString() + "/" + nombre;

			objService.generarExcel(ruta + ".xlsx", nombre + ".xlsx", tipoDocumento);
			objService.generarPDF(response, ruta + ".pdf", nombre + ".pdf", tipoDocumento);
			
			return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
	}
	@GetMapping("/descargarAntiguedadesFormacionPDF")
	public ResponseEntity<?> descargarArchivo( HttpServletRequest request) throws FileNotFoundException {
		String nombre= ANTIGUEDADESFORMACION+periodoAcademicoRepository.getPAActive().toString()+".pdf";
		// Buscar el archivo en la base de datos
		Documento archivo = repository.findByNombre(nombre).orElse(null);
		if (archivo == null) {
			return response(HttpStatus.BAD_REQUEST, ARCHIVO_NO_EXISTE);
		}
		// Crear un objeto Resource para el archivo
		File file = new File(archivo.getRuta());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		// Obtener la extensi�n del archivo
		String contentType = "application/pdf";
		// Devolver una respuesta con el archivo adjunto y la URL de descarga
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getNombre() + "\"")
				.contentType(MediaType.parseMediaType(contentType))
				.body(resource);
	}
	
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
