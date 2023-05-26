package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_RESULTADO_ANTIGUEDADES;
import static epntech.cbdmq.pe.constante.ResponseMessage.ERROR_GENERAR_ARCHIVO;
import static epntech.cbdmq.pe.constante.ResponseMessage.EXITO_GENERAR_ARCHIVO;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.util.AntiguedadesDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.impl.AntiguedadesServiceImpl;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/antiguedades")
public class AntiguedadesResource {

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	
	@Autowired
	private AntiguedadesServiceImpl objService;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;

	@GetMapping("/listarFemenino")
	public Set<AntiguedadesDatos> listarFemenino() {
		return objService.getAntiguedadesFemenino();
	}

	@GetMapping("/listarMasculino")
	public Set<AntiguedadesDatos> listarMasculino() {
		return objService.getAntiguedadesMasculino();
	}

	@GetMapping("/generarArchivos")
	public ResponseEntity<?> generarArchivos(HttpServletResponse response, @RequestParam("nombre") String nombre, @RequestParam("tipoDocumento") Integer tipoDocumento) throws DataException, DocumentException {
		try {

			String nombreFemenino = "/" + nombre + "-Femenino";
			String nombreMasculino = "/" + nombre + "-Masculino";
			
			String rutaF = ARCHIVOS_RUTA + PATH_RESULTADO_ANTIGUEDADES + periodoAcademicoRepository.getPAActive().toString() + nombreFemenino;
			String rutaM = ARCHIVOS_RUTA + PATH_RESULTADO_ANTIGUEDADES + periodoAcademicoRepository.getPAActive().toString() + nombreMasculino;

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
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
