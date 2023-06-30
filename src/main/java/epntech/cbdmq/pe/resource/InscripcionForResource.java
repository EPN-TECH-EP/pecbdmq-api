package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.ArchivoConst.NO_ADJUNTO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.InscripcionFor;
import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.admin.UsuarioDatoPersonal;
import epntech.cbdmq.pe.dominio.admin.ValidacionRequisitos;
import epntech.cbdmq.pe.dominio.util.InscripcionResult;
import epntech.cbdmq.pe.dominio.util.PostulanteDatos;
import epntech.cbdmq.pe.dominio.util.PostulanteUtil;
import epntech.cbdmq.pe.dominio.util.ValidaPinInscripcionFormacionUtil;
import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosLista;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.InscripcionForServiceImpl;
import epntech.cbdmq.pe.servicio.impl.PostulanteDatosServiceImpl;
import epntech.cbdmq.pe.servicio.impl.PostulanteServiceImpl;
import epntech.cbdmq.pe.servicio.impl.UsuarioDatoPersonalServiceImpl;
import epntech.cbdmq.pe.servicio.impl.ValidacionRequisitosForServiceImpl;
import epntech.cbdmq.pe.servicio.impl.ValidacionRequisitosServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping(path = "/inscripcionfor")
public class InscripcionForResource {

	@Autowired
	private InscripcionForServiceImpl objService;
	
	@Autowired
	private PostulanteServiceImpl objPostulanteService;
	
	@Autowired
	private UsuarioDatoPersonalServiceImpl objUDPService;
	
	@Autowired
	private PostulanteServiceImpl postulanteService;
	
	@Autowired
	private PostulanteDatosServiceImpl postulanteDatosService;
	
	@Autowired
	private ValidacionRequisitosServiceImpl validacionRequisitosService;
	
	@Autowired
	private ValidacionRequisitosForServiceImpl validacionRequisitosForService;

	@PostMapping("/crear")
	public ResponseEntity<?> crear(@RequestParam(name = "datosPersonales", required = true) String datosPersonales, @RequestParam(name = "documentos", required = true) List<MultipartFile> documentos) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException {

		//System.out.println("documentos.size(): " + documentos.size());
		List<MultipartFile> archivosAdjuntos = new ArrayList<>();
		for (MultipartFile documento : documentos) {
		    if (!documento.isEmpty()) {
		        archivosAdjuntos.add(documento);
		    }
		}
		if (archivosAdjuntos.isEmpty())
			return response(HttpStatus.BAD_REQUEST, NO_ADJUNTO);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		JsonNode jsonNode = objectMapper.readTree(datosPersonales);
		System.out.println("jsonNode: " + jsonNode);
		
		InscripcionFor inscripcion = objectMapper.readValue(datosPersonales, InscripcionFor.class);		
		
		Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            //System.out.println("Clave: " + key + ", Valor: " + value.asText());
            if(key.equals("fecha_nacimiento")) {
            	
            	ZonedDateTime parsedDate = ZonedDateTime.parse(value.asText());
            	
                LocalDateTime fecha = parsedDate.toLocalDateTime();
                inscripcion.setFechaNacimiento(fecha);
            }
        }

        //System.out.println("inscripcion.getCedula(): " + inscripcion.getCedula());
        InscripcionResult result = new InscripcionResult();
        result = objService.insertarInscripcionConDocumentos(inscripcion, documentos);
		//System.out.println("ins: " + ins);
		
        return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
		try {
			return objService.getInscripcionById(codigo).map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, "Error: No existen datos de inscripción.");
		}
	}
	
	@PostMapping("/generaPin/{codPostulante}")
	public ResponseEntity<InscripcionFor> getPin(@PathVariable("codPostulante") Long codPostulante) throws DataException, MessagingException {
		
		//return (ResponseEntity<InscripcionFor>) objService.getById(idDatoPersonal).map(datosGuardados -> {
		return (ResponseEntity<InscripcionFor>) objPostulanteService.getById(codPostulante).map(datosGuardados -> {
			
			
			InscripcionFor inscripcion = objService.getById(datosGuardados.getCodDatoPersonal()).get();
			
			//datosGuardados.setCodDatoPersonal(idDatoPersonal);

			InscripcionFor datosActualizados = new InscripcionFor();
			try {
				datosActualizados = objService.savePin(inscripcion);
			} catch (DataException | MessagingException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
			}
			
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping("/validaPin")
	// @RequestParam("pin") String pin, @RequestParam("idDatoPersonal") Integer idDatoPersonal, @RequestParam("idPostulante") Long idPostulante
	public ResponseEntity<HttpResponse> validaPin(@RequestBody ValidaPinInscripcionFormacionUtil validaPin)
			throws DataException, MessagingException {
		InscripcionFor dato;
		Postulante p;

		try {
			dato = objService.getById(validaPin.getIdDatoPersonal()).get();
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
		}
		
		try {
			p = objPostulanteService.getById(validaPin.getIdPostulante()).get();
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
		}
			
		//Postulante postulante = new Postulante();
		p.setCodPostulante(validaPin.getIdPostulante());
		p.setCodDatoPersonal(validaPin.getIdDatoPersonal());
		//postulante.setEstado("PENDIENTE");
		
		return response(HttpStatus.OK, objService.savePostulante(p, "F", validaPin.getPin(), dato.getPinValidacionCorreo(), dato.getCorreoPersonal()));
	}
	
	@PostMapping("/reenvioPin")
	public ResponseEntity<?> reenvioPin(@RequestBody InscripcionFor obj) throws DataException, MessagingException {
		try {
			objService.getById(obj.getCodDatoPersonal()).get();
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE);
		}
		
		return objService.getById(obj.getCodDatoPersonal()).map(datosGuardados -> {
			datosGuardados.setCodDatoPersonal(obj.getCodDatoPersonal());
			datosGuardados.setCorreoPersonal(obj.getCorreoPersonal());

			InscripcionFor datosActualizados = new InscripcionFor();
			try {
				datosActualizados = objService.reenvioPin(datosGuardados);
			} catch (DataException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage());
			}
			
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/usuarios")
	public Set<UsuarioDatoPersonal> getUsuarios() {
		return objUDPService.getUsuarios();
	}
	
	@GetMapping("/usuario/{cedula}")
	public UsuarioDatoPersonal getUsuario(@PathVariable("cedula") String cedula) throws DataException {
		return objUDPService.getByCedula(cedula);
	}
	
	@GetMapping("/postulantes/{usuario}")
	public List<Postulante> getPostulantes(@PathVariable("usuario") Integer usuario) {
		return postulanteService.getPostulantes(usuario);
	}
	
	@GetMapping("/postulantesPaginado/{usuario}")
	public List<PostulanteUtil> getPostulantesPaginado(@PathVariable("usuario") Integer usuario, Pageable pageable) {
		return postulanteService.getPostulantesPaginado(usuario, pageable);
	}
	
	@GetMapping("/postulantesAsignadosPaginado/{usuario}")
	public List<Postulante> getPostulantesAsignados(@PathVariable("usuario") Integer usuario, Pageable pageable) {
		return postulanteService.getPostulantesAsignadosPaginado(usuario, pageable);
	}
	
	@Operation(summary = "Lista de las inscripciones asignadas. FE: ReasignaciónInscripcion")
	@GetMapping("/postulantesAllPaginado")
	public List<PostulanteUtil> getPostulantesAllPaginado(Pageable pageable) {
		return postulanteService.getPostulantesAllPaginadoTodo(pageable);
	}
	
	@PutMapping("/postulanteAsignar")
	public Postulante asignarPostulante(@RequestBody Postulante postulante) throws DataException {
		return postulanteService.update(postulante);
	}
	
	@GetMapping("/datos/{postulante}")
	public Optional<PostulanteDatos> getDatos(@PathVariable("postulante") Integer postulante) throws DataException {
		return postulanteDatosService.getDatos(postulante);
	}
	
	@GetMapping("/requisitos/{postulante}")
	public List<ValidacionRequisitosLista> getRequisitos(@PathVariable("postulante") Integer postulante) throws DataException {
		return validacionRequisitosService.getRequisitos(postulante);
	}
	
	@PutMapping("/requisitosUpdate")
	public List<ValidacionRequisitos> requisitosUpdate(@RequestBody List<ValidacionRequisitos> requisitos) throws DataException {
		
		return validacionRequisitosForService.update(requisitos);
	}
	
	@GetMapping("/generarMuestra")
	public List<Postulante> generarMuestra() throws DataException {
		return postulanteService.getMuestra();
	}
	@GetMapping("/getMuestra")
	public List<Postulante> getMuestra() throws DataException {
		return postulanteService.getPostulantesMuestraPA();
	}
	
	@PutMapping("/asignarMuestra")
	public Postulante asignarMuestra(@RequestBody Postulante postulante) throws DataException {
		return postulanteService.updateEstadoMuestra(postulante);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
	
	@GetMapping("/validafechas")
	public Boolean getFecha() throws DataException, ParseException {
		return objService.validaFechas();
	}
	
	@PostMapping("/validaEdad")
	public Boolean validaEdad(@RequestBody String fecha) throws DataException, ParseException {
		
		LocalDate fechaNacimiento = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/uuuu"));
		
		return objService.validaEdad(fechaNacimiento);
	}
	
	
	@GetMapping("/inscripcionPorCedula/{cedula}")
	public Boolean findByCedula(@PathVariable("cedula") String cedula) throws DataException, ParseException {
		return objService.findByCedula(cedula);
	} 
}
