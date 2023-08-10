package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.NO_ADJUNTO;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SEND;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.constante.ResponseMessage.ERROR_GENERAR_ARCHIVO;
import static epntech.cbdmq.pe.constante.ResponseMessage.EXITO_GENERAR_ARCHIVO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.util.DatosInscripcionEsp;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionDatosEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.ValidaRequisitos;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.dominio.util.InscritosEspecializacion;
import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosDatos;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.InscripcionEspServiceImpl;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/inscripcionEsp")
public class InscripcionEspResource {

	@Autowired
	private InscripcionEspServiceImpl inscripcionEspServiceImpl;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody InscripcionEsp inscripcionEsp) {

		return new ResponseEntity<>(inscripcionEspServiceImpl.save(inscripcionEsp), HttpStatus.OK);
	}
	@PostMapping("/crearFully")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(
			@RequestParam("datos") String datos,
			@RequestParam("documentos") List<MultipartFile> documentos) throws IOException, ParseException, MessagingException, DataException, ArchivoMuyGrandeExcepcion {
		return new ResponseEntity<>(inscripcionEspServiceImpl.saveFully(datos, documentos/*, codTipoDocumento*/), HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<InscripcionEsp> actualizarDatos(@RequestBody InscripcionEsp inscripcionEsp) {
		
		return new ResponseEntity<>(inscripcionEspServiceImpl.update(inscripcionEsp), HttpStatus.OK);
	}

	@GetMapping("/listarPorCurso/{codCurso}/usuario/{codUsuario}")
	public List<InscripcionDatosEspecializacion> listarPaginadoPorCursoAndUsuario(
			@PathVariable("codCurso") Long codCurso,
			@PathVariable("codUsuario") Long codUsuario,
			Pageable pageable) {
		return inscripcionEspServiceImpl.getByCursoAndUsuarioPaginado(codCurso, codUsuario, pageable);
	}

	@GetMapping("/listarPorCurso/{codCurso}")
	public List<InscripcionDatosEspecializacion> listarPaginadoPorCurso(
			@PathVariable("codCurso") Long codCurso,
			Pageable pageable) {
		return inscripcionEspServiceImpl.getAllByCursoPaginado(codCurso, pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InscripcionDatosEsp> obtenerPorId(@PathVariable("id") long codigo) {
		return inscripcionEspServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) {
		inscripcionEspServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@PostMapping("/uploadDocumentos")
	public ResponseEntity<List<Documento>> uploadFiles(@RequestParam("codInscripcion") Long codInscripcion, @RequestParam("codTipoDocumento") Long codTipoDocumento, @RequestParam("archivos") List<MultipartFile> archivos) throws IOException, ArchivoMuyGrandeExcepcion, DataException {
		
		if(archivos.get(0).getSize() == 0)
			throw new DataException(NO_ADJUNTO);
		
		return new ResponseEntity<>(inscripcionEspServiceImpl.uploadFiles(codInscripcion, codTipoDocumento, archivos), HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminarDocumento")
	public ResponseEntity<HttpResponse> eliminarArchivo(@RequestParam Long codInscripcion, @RequestParam Long codDocumento)
			throws IOException, DataException {
		inscripcionEspServiceImpl.deleteDocumento(codInscripcion, codDocumento);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@PostMapping("/notificar")
	public ResponseEntity<?> notificar(@RequestParam("codInscripcion") Long codInscripcion)
			throws MessagingException, DataException, PSQLException {
		inscripcionEspServiceImpl.notificarInscripcion(codInscripcion);
		return response(HttpStatus.OK, EMAIL_SEND);
	}

	@GetMapping("/cumpleMinimoInscritosCurso/{id}")
	public ResponseEntity<?> cumpleMinimoInscritos(@PathVariable("id") long codigo) {
		return response(HttpStatus.OK, inscripcionEspServiceImpl.cumplePorcentajeMinimoInscritosCurso(codigo).toString());
	}

	@GetMapping("/porCurso/{id}")
	public List<InscripcionDatosEspecializacion> obtenerPorCurso(@PathVariable("id") long codigo) throws DataException {
		return inscripcionEspServiceImpl.getByCurso(codigo);
	}
	@GetMapping("/porCurso&Estado")
	public Set<InscripcionDatosEspecializacion> obtenerPorCursoEstado(@RequestParam("id") long codigo, @RequestParam("estado") String estado) throws DataException {
		return inscripcionEspServiceImpl.getByCursoEstado(codigo,estado);
	}
	@GetMapping("/aprobadosPruebasPorCurso")
	public List<DatosInscripcionEsp> aprobadosPruebasPorCurso(@RequestParam("id") Integer codigo) throws DataException {
		return inscripcionEspServiceImpl.getAprobadosPruebas(codigo);
	}
	
	@PostMapping("/validacionRequisitos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> validacionRequisitos(@RequestBody List<ValidaRequisitos> validaRequisitos) {
		return new ResponseEntity<>(inscripcionEspServiceImpl.saveValidacionRequisito(validaRequisitos), HttpStatus.OK);
	}

	@GetMapping("/requisitos/{idInscripcion}")
	public List<ValidacionRequisitosDatos> getRequisitos(@PathVariable("idInscripcion") Long codInscripcion) {
		return inscripcionEspServiceImpl.getRequisitos(codInscripcion);
	}
	
	@PutMapping("/validacionRequisitos")
	public ResponseEntity<?> updateValidacionRequisitos(@RequestBody List<ValidaRequisitos> validaRequisitos) {
		inscripcionEspServiceImpl.updateValidacionRequisito(validaRequisitos);
		return response(HttpStatus.OK, EMAIL_SEND);
	}
	
	@GetMapping("/inscripcionesValidas/{id}")
	public List<InscritosEspecializacion> listarInscripcionesValidas(@PathVariable("id") long codigo) {
		return inscripcionEspServiceImpl.getInscritosValidosCurso(codigo);
	}
	
	@PostMapping("/notificarPrueba")
	public ResponseEntity<?> notificarPrueba(@RequestParam("codCursoEspecializacion") Long codCursoEspecializacion, @RequestParam("subTipoPrueba") Long subTipoPrueba) {
		inscripcionEspServiceImpl.notificarPrueba(codCursoEspecializacion, subTipoPrueba);
		return response(HttpStatus.OK, EMAIL_SEND);
	}

	@PutMapping("/{id}/asignarDelegado/{idUsuario}")
	public ResponseEntity<?> asignarDelegado(
			@PathVariable("id") long codigo,
			@PathVariable("idUsuario") long idUsuario) {
		return new ResponseEntity<>(inscripcionEspServiceImpl.updateDelegado(codigo, idUsuario), HttpStatus.OK);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
	@GetMapping("/informacion/{cedula}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> getData(@PathVariable("cedula") String cedula) throws Exception{

		try {
			return new ResponseEntity<>(inscripcionEspServiceImpl.confirmacionInscripcion(cedula), HttpStatus.OK);
		}catch(Exception ex) {

			return response(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	@PutMapping("/colocarCorreo")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> getData(@RequestBody DatoPersonal datoPersonal) throws Exception{

		try {
			return new ResponseEntity<>(inscripcionEspServiceImpl.colocarCorreoCiudadano(datoPersonal), HttpStatus.OK);
		}catch(Exception ex) {

			return response(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}


}
