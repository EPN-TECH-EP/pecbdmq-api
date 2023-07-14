package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.ArchivoConst.DOCUMENTO_NO_CUMPLE_FORMATO;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SEND;
import static epntech.cbdmq.pe.constante.MensajesConst.ESTADO_INCORRECTO;
import static epntech.cbdmq.pe.constante.ResponseMessage.CARGA_ARCHIVO_EXCEL;
import static epntech.cbdmq.pe.constante.ResponseMessage.*;
import static epntech.cbdmq.pe.constante.MensajesConst.ERROR_REGISTRO;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_RESULTADO_PRUEBAS;
import static epntech.cbdmq.pe.constante.EstadosConst.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Prueba;
import epntech.cbdmq.pe.dominio.admin.ResultadoPruebasFisicas;
import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.impl.PostulantesValidosServiceImpl;
import epntech.cbdmq.pe.servicio.impl.PruebaServiceImpl;
import epntech.cbdmq.pe.servicio.impl.ResultadoPruebasFisicasServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/pruebasFisicas")
public class ResultadoPruebasFisicasResource {

	@Autowired
	private PostulantesValidosServiceImpl objService;
	@Autowired
	private ResultadoPruebasFisicasServiceImpl resultadoPruebasServiceImpl;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@GetMapping("/postulantesValidos")
	public List<PostulantesValidos> listar() {
		return objService.getPostulantesValidos();
	}


	/*@PutMapping("/update")
	// public ResponseEntity<?> update(@RequestParam("codPostulante") Integer
	// codPostulante, @RequestParam("resultado") Integer resultado,
	// @RequestParam("cumple") Boolean cumple, @RequestParam("codModulo") Integer
	// codModulo, @RequestParam("codPrueba") Integer codPrueba) throws DataException
	// {
	public ResponseEntity<?> update(@RequestBody ResultadoPruebas obj) {
		return (ResponseEntity<ResultadoPruebasFisicas>) resultadoPruebasServiceImpl
				.getByCodPostulanteAndPrueba(obj.getCodPostulante(), obj.getCodPrueba()).map(datosGuardados -> {
					datosGuardados.setNotaPromedioFinal(obj.getNotaPromedioFinal());
					datosGuardados.setCumplePrueba(obj.getCumplePrueba());

					ResultadoPruebas datosActualizados = null;
					datosActualizados = resultadoPruebasServiceImpl.update(datosGuardados);
					return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
				}).orElseGet(() -> ResponseEntity.notFound().build());
	}*/

	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestBody ResultadoPruebasFisicas obj) {

		return new ResponseEntity<>(resultadoPruebasServiceImpl.save(obj), HttpStatus.OK);

	}

	@PostMapping("/cargarPlantilla")
	public ResponseEntity<?> uploadFile(@RequestParam("archivo") MultipartFile archivo,@RequestParam("codPruebaDetalle") Integer codPruebaDetalle,@RequestParam("codFuncionario") Integer codFuncionario,@RequestParam("tipoResultado") String tipoResultado){

		if (ExcelHelper.hasExcelFormat(archivo)) {
			try {
				resultadoPruebasServiceImpl.uploadFile(archivo,codPruebaDetalle,codFuncionario,tipoResultado);

				return response(HttpStatus.OK, CARGA_EXITOSA);
			} catch (Exception e) {
				return response(HttpStatus.EXPECTATION_FAILED, DOCUMENTO_NO_CUMPLE_FORMATO);
			}
		}

		return response(HttpStatus.BAD_REQUEST, CARGA_ARCHIVO_EXCEL);
	}

	@GetMapping("/descargar")
	public ResponseEntity<?> downloadFile() {
		String filename = "datos.xlsx";
		InputStreamResource file = new InputStreamResource(resultadoPruebasServiceImpl.downloadFile());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	@PostMapping("/generarExcel")
	public ResponseEntity<?> generarExcel(@RequestParam("nombre") String nombre, @RequestParam("subTipoPrueba") Integer subTipoPrueba) throws DataException {
		try {
			String ruta = ARCHIVOS_RUTA + PATH_RESULTADO_PRUEBAS + periodoAcademicoRepository.getPAActive().toString()
					+ "/" + nombre;

			resultadoPruebasServiceImpl.generarExcel(ruta, nombre, subTipoPrueba);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
	}

	@PostMapping("/generarPDF")
	public ResponseEntity<?> generarPDF(HttpServletResponse response ,@RequestParam("nombre") String nombre, @RequestParam("subTipoPrueba") Integer subTipoPrueba)
			throws DocumentException, IOException, DataException {

		try {
			String[] columnas = { "Codigo", "Cedula", "Nombre", "Apellido", "Resultado", "Resultado Tiempo", "Nota Promedio" };
			resultadoPruebasServiceImpl.generarPDF(response, nombre, subTipoPrueba, columnas);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
			return response(HttpStatus.BAD_REQUEST, ERROR_GENERAR_ARCHIVO);
		}
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
}
