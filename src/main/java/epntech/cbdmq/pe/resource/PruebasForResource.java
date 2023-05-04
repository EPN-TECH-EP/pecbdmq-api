package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SEND;
import static epntech.cbdmq.pe.constante.MensajesConst.ESTADO_INCORRECTO;
import static epntech.cbdmq.pe.constante.ResponseMessage.CARGA_ARCHIVO_EXCEL;
import static epntech.cbdmq.pe.constante.ResponseMessage.CARGA_EXITOSA;
import static epntech.cbdmq.pe.constante.ResponseMessage.CARGA_NO_EXITOSA;
import static epntech.cbdmq.pe.constante.MensajesConst.ERROR_REGISTRO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Prueba;
import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.servicio.impl.PostulantesValidosServiceImpl;
import epntech.cbdmq.pe.servicio.impl.PruebaServiceImpl;
import epntech.cbdmq.pe.servicio.impl.ResultadoPruebasServiceImpl;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/pruebasFor")
public class PruebasForResource {

	@Autowired
	private PostulantesValidosServiceImpl objService;
	@Autowired
	private PruebaServiceImpl pruebaServiceImpl;
	@Autowired
	private ResultadoPruebasServiceImpl resultadoPruebasServiceImpl;

	@GetMapping("/postulantesValidos")
	public List<PostulantesValidos> listar() {
		return objService.getPostulantesValidos();
	}

	@PostMapping("/notificar")
	public ResponseEntity<?> notificar(@RequestParam("mensaje") String mensaje, @RequestParam("modulo") Integer modulo,
			@RequestParam("prueba") Integer prueba) throws MessagingException, DataException {
		Optional<Prueba> pp = pruebaServiceImpl.getById(prueba);
		if (pp.isPresent() && (pp.get().getEstado().equalsIgnoreCase("ACTIVO")
				|| pp.get().getEstado().equalsIgnoreCase("INICIO"))) {
			objService.notificar(mensaje);
			// objService.onInitResultado(objService.getPostulantesValidos(), modulo,
			// prueba);

			Prueba p = new Prueba();
			p = pp.get();
			p.setEstado("NOTIFICACIÓN");
			pruebaServiceImpl.update(p);

			return response(HttpStatus.OK, EMAIL_SEND);
		} else
			return response(HttpStatus.BAD_REQUEST, ESTADO_INCORRECTO);
	}

	@GetMapping("/paginado")
	public ResponseEntity<?> listarDatos(Pageable pageable) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.getAllPaginado(pageable));
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
				.getByCodPostulanteAndPrueba(obj.getCodPostulante(), obj.getCodPrueba()).map(datosGuardados -> {
					datosGuardados.setResultado(obj.getResultado());
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
	public ResponseEntity<?> uploadFile(@RequestParam("archivo") MultipartFile archivo) {

		if (ExcelHelper.hasExcelFormat(archivo)) {
			try {
				resultadoPruebasServiceImpl.uploadFile(archivo);

				return response(HttpStatus.OK, CARGA_EXITOSA);
			} catch (Exception e) {
				return response(HttpStatus.EXPECTATION_FAILED, CARGA_NO_EXITOSA);
			}
		}

		return response(HttpStatus.BAD_REQUEST, CARGA_ARCHIVO_EXCEL);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
}
