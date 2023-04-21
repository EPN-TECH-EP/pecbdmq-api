package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.*;
import static epntech.cbdmq.pe.constante.ArchivoConst.NO_ADJUNTO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.InscripcionFor;
import epntech.cbdmq.pe.dominio.admin.InscripcionResult;
import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.admin.TipoProcedencia;
import epntech.cbdmq.pe.dominio.admin.UsuarioDatoPersonal;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.InscripcionForServiceImpl;
import epntech.cbdmq.pe.servicio.impl.PostulanteServiceimpl;
import epntech.cbdmq.pe.servicio.impl.UsuarioDatoPersonalServiceImpl;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping(path = "/inscripcionfor")
public class InscripcionForResource {

	@Autowired
	private InscripcionForServiceImpl objService;
	
	@Autowired
	private PostulanteServiceimpl objPostulanteService;
	
	@Autowired
	private UsuarioDatoPersonalServiceImpl objUDPService;
	
	@Autowired
	private PostulanteServiceimpl postulanteService;

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
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            //System.out.println("Clave: " + key + ", Valor: " + value.asText());
            if(key.equals("fecha_nacimiento")) {
                Date fecha = dateFormat.parse(value.asText());
                inscripcion.setFecha_nacimiento(fecha);
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
			return response(HttpStatus.NOT_FOUND, "Error. Por favor intente más tarde.");
		}
	}
	
	@PutMapping("/generaPin")
	public ResponseEntity<InscripcionFor> getPin(@RequestParam("idDatoPersonal") Integer idDatoPersonal) throws DataException, MessagingException {
		return objService.getById(idDatoPersonal).map(datosGuardados -> {
			datosGuardados.setCodDatoPersonal(idDatoPersonal);

			InscripcionFor datosActualizados = new InscripcionFor();
			try {
				datosActualizados = objService.savePin(datosGuardados);
			} catch (DataException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/validaPin")
	public ResponseEntity<HttpResponse> validaPin(@RequestParam("pin") String pin, @RequestParam("idDatoPersonal") Integer idDatoPersonal, @RequestParam("idPostulante") Integer idPostulante)
			throws DataException, MessagingException {
		InscripcionFor dato;
		Postulante p;

		try {
			dato = objService.getById(idDatoPersonal).get();
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE + " - " + idDatoPersonal);
		}
		
		try {
			p = objPostulanteService.getById(idPostulante).get();
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE + " - " + idPostulante);
		}
			
		Postulante postulante = new Postulante();
		postulante.setCodPostulante(idPostulante);
		postulante.setCodDatoPersonal(idDatoPersonal);
		postulante.setEstado("ACTIVO");
		
		return response(HttpStatus.OK, objService.savePostulante(postulante, "F", pin, dato.getPin_validacion_correo(), dato.getCorreoPersonal()));
	}
	
	@PutMapping("/reenvioPin")
	public ResponseEntity<?> reenvioPin(@RequestBody InscripcionFor obj) throws DataException, MessagingException {
		try {
			objService.getById(obj.getCodDatoPersonal()).get();
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, REGISTRO_NO_EXISTE + " - " + obj.getCodDatoPersonal().toString());
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
	
	@PutMapping("/postulante")
	public Postulante asignarPostulante(@RequestBody Postulante postulante) throws DataException {
		return postulanteService.update(postulante);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}
}
