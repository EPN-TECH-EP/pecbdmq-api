package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.NO_ADJUNTO;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SEND;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCurso;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.ConvocatoriaCursoServiceImpl;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/convocatoriaCurso")
public class ConvocatoriaCursoResource {

	@Autowired
	private ConvocatoriaCursoServiceImpl convocatoriaCursoServiceImpl;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestParam(required = true) String datos, @RequestParam(required = true) List<MultipartFile> archivos)
			throws DataException, IOException, ArchivoMuyGrandeExcepcion {

		if (archivos.get(0).getSize() == 0)
			throw new DataException(NO_ADJUNTO);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		//JsonNode jsonNode = objectMapper.readTree(datos);
		//System.out.println("jsonNode: " + jsonNode);
		
		ConvocatoriaCurso convocatoriaCurso = new ConvocatoriaCurso();
		convocatoriaCurso = objectMapper.readValue(datos, ConvocatoriaCurso.class);
		
		convocatoriaCurso = convocatoriaCursoServiceImpl.save(convocatoriaCurso, archivos);

		return new ResponseEntity<>(convocatoriaCurso, HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<ConvocatoriaCurso> listar() {
		return convocatoriaCursoServiceImpl.listAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ConvocatoriaCurso> obtenerPorId(@PathVariable("id") long codigo) throws DataException {
		return convocatoriaCursoServiceImpl.getByID(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/byCurso/{id}")
	public ResponseEntity<ConvocatoriaCurso> obtenerPorCurso(@PathVariable("id") long codigo) throws DataException {
		return convocatoriaCursoServiceImpl.getByCurso(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {
		convocatoriaCursoServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarDatos(@PathVariable("id") long codigo, @RequestBody ConvocatoriaCurso obj) throws DataException{
		
		return (ResponseEntity<ConvocatoriaCurso>) convocatoriaCursoServiceImpl.getByID(codigo).map(datosGuardados -> {
			datosGuardados.setFechaInicioConvocatoria(obj.getFechaInicioConvocatoria());
			datosGuardados.setFechaFinConvocatoria(obj.getFechaFinConvocatoria());
			datosGuardados.setHoraInicioConvocatoria(obj.getHoraInicioConvocatoria());
			datosGuardados.setHoraFinConvocatoria(obj.getHoraFinConvocatoria());
			datosGuardados.setEstado(obj.getEstado());

			ConvocatoriaCurso datosActualizados = new ConvocatoriaCurso();
			
			try {
				datosActualizados = convocatoriaCursoServiceImpl.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				
				return response(HttpStatus.BAD_REQUEST, e.getMessage());
			}
			
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/eliminarDocumento")
	public ResponseEntity<HttpResponse> eliminarArchivo(@RequestParam Long codConvocatoria, @RequestParam Long codDocumento)
			throws IOException, DataException {

		convocatoriaCursoServiceImpl.deleteDocumento(codConvocatoria,codDocumento);
		
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@PostMapping("/notificar")
	public ResponseEntity<?> notificar(@RequestParam("codConvocatoria") Long codConvocatoria)
			throws MessagingException, DataException, PSQLException {
		
		//mensaje = "Estimad@, la convocatoria al curso %s inicia Desde: %tF, %tT Hasta: %tF, %tT ";
		convocatoriaCursoServiceImpl.notificar(codConvocatoria);
		
		return response(HttpStatus.OK, EMAIL_SEND);
	}
	
	/*valida si existe una convocatoria de un curso activa en base a la fecha y hora actual*/
	@GetMapping("/validaConvocatoriaCursoActiva/{id}")
	public ResponseEntity<?> validaConvocatoriaCursoActiva(@PathVariable("id") long codigo) throws DataException {
		return response(HttpStatus.OK, convocatoriaCursoServiceImpl.validaConvocatoriaCursoActiva(codigo).toString());
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
