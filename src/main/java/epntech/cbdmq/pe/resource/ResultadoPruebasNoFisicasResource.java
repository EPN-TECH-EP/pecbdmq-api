package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.ArchivoConst.DOCUMENTO_NO_CUMPLE_FORMATO;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SEND;
import static epntech.cbdmq.pe.constante.MensajesConst.*;
import static epntech.cbdmq.pe.constante.ResponseMessage.*;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_RESULTADO_PRUEBAS;
import static epntech.cbdmq.pe.constante.EstadosConst.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.postgresql.util.PSQLException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.impl.PostulantesValidosServiceImpl;
import epntech.cbdmq.pe.servicio.impl.PruebaDetalleServiceImpl;
import epntech.cbdmq.pe.servicio.impl.ResultadoPruebasServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/pruebasNoFisicas")
public class ResultadoPruebasNoFisicasResource {

	@Autowired
	private PostulantesValidosServiceImpl objService;
	@Autowired
	private ResultadoPruebasServiceImpl resultadoPruebasServiceImpl;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;
	@Autowired
	private PruebaDetalleServiceImpl pruebaDetalleServiceImpl;

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@GetMapping("/postulantesValidos")
	public List<PostulantesValidos> listar() {
		return objService.getPostulantesValidos();
	}

	@PostMapping("/notificar")
	public ResponseEntity<?> notificar(@RequestParam("mensaje") String mensaje,
			@RequestParam("subTipoPrueba") Integer subTipoPrueba)
			throws MessagingException, DataException, PSQLException {

		Optional<PruebaDetalle> pp = pruebaDetalleServiceImpl.getBySubtipoAndPA(subTipoPrueba,
				periodoAcademicoRepository.getPAActive());
		if (pp.isPresent() && (pp.get().getEstado().equalsIgnoreCase("ACTIVO")
				|| pp.get().getEstado().equalsIgnoreCase("INICIO"))) {

			try {
				objService.notificar(mensaje, pp.get().getDescripcionPrueba(), pp.get().getFechaInicio(),
						pp.get().getFechaFin(), pp.get().getHora(), pp.get().getCodSubtipoPrueba());

				PruebaDetalle p = new PruebaDetalle();
				p = pp.get();
				p.setEstado(NOTIFICACION);
				pruebaDetalleServiceImpl.update(p);

				return response(HttpStatus.OK, EMAIL_SEND);

			} catch (Exception ex) {
				return response(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
			}
		} else
			return response(HttpStatus.BAD_REQUEST, ESTADO_INCORRECTO);
	}

	@GetMapping("/paginado")
	public ResponseEntity<?> listarDatos(Pageable pageable, @RequestParam("subTipoPrueba") Integer subTipoPrueba) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.getAllPaginado(pageable, subTipoPrueba));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, ERROR_REGISTRO);
		}
	}

	@PutMapping("/update")
	// public ResponseEntity<?> update(@RequestParam("codPostulante") Integer
	// codPostulante, @RequestParam("resultado") Integer resultado,
	// @RequestParam("cumple") Boolean cumple, @RequestParam("codModulo") Integer
	// codModulo, @RequestParam("codPrueba") Integer codPrueba) throws DataException
	// {
	public ResponseEntity<?> update(@RequestBody ResultadoPruebas obj) {
		return (ResponseEntity<ResultadoPruebas>) resultadoPruebasServiceImpl
				.getByCodPostulanteAndCodPruebaDetalle(obj.getCodPostulante(), obj.getCodPruebaDetalle())
				.map(datosGuardados -> {
					datosGuardados.setNotaPromedioFinal(obj.getNotaPromedioFinal());
					datosGuardados.setCumplePrueba(obj.getCumplePrueba());

					ResultadoPruebas datosActualizados = null;
					datosActualizados = resultadoPruebasServiceImpl.update(datosGuardados);
					return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
				}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestBody ResultadoPruebas obj) {

		return new ResponseEntity<>(resultadoPruebasServiceImpl.save(obj), HttpStatus.OK);

	}

	@PostMapping("/cargarPlantilla")
	public ResponseEntity<?> uploadFile(@RequestParam("archivo") MultipartFile archivo,@RequestParam("codPruebaDetalle") Integer codPruebaDetalle,@RequestParam("codFuncionario") Integer codFuncionario,@RequestParam("tipoResultado") String tipoResultado) {

		if (ExcelHelper.hasExcelFormat(archivo)) {
			try {
				resultadoPruebasServiceImpl.uploadFile(archivo,codPruebaDetalle,codFuncionario,tipoResultado);

				return response(HttpStatus.OK, CARGA_EXITOSA);
			} catch (Exception e) {
				return response(HttpStatus.EXPECTATION_FAILED,  DOCUMENTO_NO_CUMPLE_FORMATO);
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

	@PostMapping("/generarArchivos")
	public ResponseEntity<?> generarArchivos(HttpServletResponse response, @RequestParam("nombre") String nombre,
			@RequestParam("subTipoPrueba") Integer subTipoPrueba) throws DataException, DocumentException {
		
		  try { Optional<PruebaDetalle> pp =
		  pruebaDetalleServiceImpl.getBySubtipoAndPA(subTipoPrueba,
		  periodoAcademicoRepository.getPAActive());
		  
		  if (pp.get().getEstado().equalsIgnoreCase("CIERRE")) { throw new
		  DataException(ESTADO_INVALIDO); } else { String ruta = ARCHIVOS_RUTA +
		  PATH_RESULTADO_PRUEBAS + periodoAcademicoRepository.getPAActive().toString()
		  + "/" + nombre;
		  
		  resultadoPruebasServiceImpl.generarExcel(ruta + ".xlsx", nombre,
		  subTipoPrueba); resultadoPruebasServiceImpl.generarPDF(response, ruta +
		  ".pdf", nombre, subTipoPrueba);
		  
		  PruebaDetalle p = new PruebaDetalle(); p = pp.get(); p.setEstado("CIERRE");
		  
		  pruebaDetalleServiceImpl.save(p); }
		  
		  } catch (IOException e) { e.printStackTrace(); System.out.println("error: " +
		  e.getMessage()); return response(HttpStatus.BAD_REQUEST,
		  ERROR_GENERAR_ARCHIVO); }
		 
		return response(HttpStatus.OK, EXITO_GENERAR_ARCHIVO);
	}

	@PostMapping("/generarExcel")
	public ResponseEntity<?> generarExcel(@RequestParam("nombre") String nombre,
			@RequestParam("subTipoPrueba") Integer subTipoPrueba)
			throws DataException {
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
	public ResponseEntity<?> generarPDF(HttpServletResponse response, @RequestParam("nombre") String nombre,
			@RequestParam("subTipoPrueba") Integer subTipoPrueba) throws DocumentException, IOException, DataException {

		try {
			String ruta = ARCHIVOS_RUTA + PATH_RESULTADO_PRUEBAS + periodoAcademicoRepository.getPAActive().toString()
					+ "/" + nombre;

			resultadoPruebasServiceImpl.generarPDF(response, ruta, nombre, subTipoPrueba);

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
