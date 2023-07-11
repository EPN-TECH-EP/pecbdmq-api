package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.NO_ADJUNTO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.CursoServiceImpl;

@RestController
@RequestMapping("/curso")
public class CursoResource {

	@Autowired
	private CursoServiceImpl cursoServiceImpl;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestParam("datos") String datos, @RequestParam("documentos") List<MultipartFile> documentos, @RequestParam("codTipoDocumento") Long codTipoDocumento) throws DataException, JsonMappingException, JsonProcessingException, ParseException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		JsonNode jsonNode = objectMapper.readTree(datos);
		System.out.println("jsonNode: " + jsonNode);
		
		Curso cursoDatos = objectMapper.readValue(datos, Curso.class);
		
		Curso curso = new Curso();
        
        Set<Requisito> requisitos = cursoDatos.getRequisitos();
        
        Set<Requisito> reqs = new HashSet<>();
		for (Requisito r : requisitos) {
			Requisito requisito = new Requisito();
			requisito.setCodigoRequisito(r.getCodigoRequisito());
			reqs.add(requisito);
		}
		
		
		curso = cursoServiceImpl.save(cursoDatos, requisitos, documentos, codTipoDocumento);
		
        return new ResponseEntity<>(curso, HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Curso> listar() {
		return cursoServiceImpl.listarAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Curso> getById(@PathVariable("id") Long codigo) throws DataException {
		return cursoServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/updateEstadosAprobadoValidado")
	public ResponseEntity<?> updateEstado(@RequestParam("codCursoEspecializacion") Long codCursoEspecializacion, @RequestParam("codDocumento") Long codDocumento, @RequestParam("estadoAprobado") Boolean estadoAprobado, @RequestParam("estadoValidado") Boolean estadoValidado, @RequestParam("observaciones") String observaciones){
		return new ResponseEntity<>(cursoServiceImpl.updateEstadoAprobadoValidado(estadoAprobado, estadoValidado, observaciones, codCursoEspecializacion, codDocumento), HttpStatus.OK);
	}
	
	@PutMapping("/updateEstadosProceso")
	public ResponseEntity<?> updateEstadoProceso(@RequestParam("codCursoEspecializacion") Long codCursoEspecializacion, @RequestParam("codCursoEstado") Long codCursoEstado) throws DataException{
		return new ResponseEntity<>(cursoServiceImpl.updateEstadoProceso(codCursoEstado, codCursoEspecializacion), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Curso> updateDatos(@PathVariable("id") long codigo, @RequestBody Curso obj) throws DataException, ParseException{
	
		return (ResponseEntity<Curso>) cursoServiceImpl.getById(codigo).map(datosGuardados -> {
			datosGuardados.setCodAula(obj.getCodAula());
			datosGuardados.setNumeroCupo(obj.getNumeroCupo());
			datosGuardados.setFechaInicioCargaNota(obj.getFechaInicioCargaNota());
			datosGuardados.setFechaFinCargaNota(obj.getFechaFinCargaNota());
			datosGuardados.setNotaMinima(obj.getNotaMinima());
			datosGuardados.setCodCatalogoCursos(obj.getCodCatalogoCursos());
			datosGuardados.setCodTipoCurso(obj.getCodTipoCurso());
			datosGuardados.setEmailNotificacion(obj.getEmailNotificacion());
			datosGuardados.setEstado(obj.getEstado());
			datosGuardados.setTieneModulos(obj.getTieneModulos());

			Curso datosActualizados = null;
			try {
				datosActualizados = cursoServiceImpl.update(datosGuardados);
			} catch (DataException e) {
				return response(HttpStatus.BAD_REQUEST, e.getMessage());
			}
			
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping("/updateRequisitos/{id}")
	public ResponseEntity<Curso> updateRequisitos(@PathVariable("id") Long codCursoEspecializacion, @RequestBody List<Requisito> requisitos) {
		return new ResponseEntity<>(cursoServiceImpl.updateRequisitos(codCursoEspecializacion, requisitos), HttpStatus.OK); 
	}
	
	@PostMapping("/updateDocumento/{id}")
	public ResponseEntity<?> updateDocumento(@PathVariable("id") Long codDocumento, @RequestParam("archivo") MultipartFile archivo) throws IOException {
		return new ResponseEntity<>(cursoServiceImpl.updateDocumento(codDocumento, archivo), HttpStatus.OK); 
	}
	
	@PostMapping("/uploadDocumentos")
	public ResponseEntity<Curso> uploadFiles(@RequestParam("codCursoEspecializacion") Long codCursoEspecializacion, @RequestParam("codTipoDocumento") Long codTipoDocumento, @RequestParam("archivos") List<MultipartFile> archivos) throws IOException, ArchivoMuyGrandeExcepcion, DataException {
		
		if(archivos.get(0).getSize() == 0)
			throw new DataException(NO_ADJUNTO);
		
		return cursoServiceImpl.uploadDocumentos(codCursoEspecializacion, archivos, codTipoDocumento).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {
		cursoServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@GetMapping("/cumpleMinimoAprobadosPruebasCurso/{id}")
	public ResponseEntity<?> cumpleMinimoAprobados(@PathVariable("id") long codigo) {
		return response(HttpStatus.OK, cursoServiceImpl.cumpleMinimoAprobadosCurso(codigo).toString());
	}
	
	@DeleteMapping("/eliminarDocumento")
	public ResponseEntity<HttpResponse> eliminarArchivo(@RequestParam Long codCursoEspecializacion, @RequestParam Long codDocumento)
			throws IOException, DataException {

		cursoServiceImpl.deleteDocumento(codCursoEspecializacion, codDocumento);
		
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
