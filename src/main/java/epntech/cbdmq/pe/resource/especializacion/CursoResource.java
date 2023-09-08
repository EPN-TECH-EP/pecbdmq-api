package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import epntech.cbdmq.pe.dominio.util.ParamsValidacion;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

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
	public ResponseEntity<?> guardar(
			@RequestParam("datos") String datos,
			@RequestParam("documentos") List<MultipartFile> documentos/*,
			@RequestParam("codTipoDocumento") Long codTipoDocumento*/) throws JsonProcessingException, ParseException, MessagingException {
		return new ResponseEntity<>(cursoServiceImpl.save(datos, documentos/*, codTipoDocumento*/), HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<Curso> listar() {
		return cursoServiceImpl.listarAll();
	}

	@GetMapping("/listarPorEstado")
	public List<Curso> listarPorEstado(@RequestParam("estado") String estado) {
		return cursoServiceImpl.listarPorEstadoAll(estado);
	}

	@GetMapping("/listarPorInstructorEstado")
	public List<Curso> listarPorInstructorAndEstado(
			@RequestParam("codUsuario") Integer codUsuario,
			@RequestParam("estado") String estado) {
		return cursoServiceImpl.listarPorInstructorAndEstado(codUsuario, estado);
	}

	@GetMapping("/listarPorTipoCurso")
	public List<Curso> listarPorTipoCurso(@RequestParam Integer codigoTipoCurso) {
		return cursoServiceImpl.getByCodigoTipoCurso(codigoTipoCurso);
	}

	@GetMapping("/listarPorCatalogoCurso")
	public List<Curso> listarPorCatalogoCurso(@RequestParam Integer codigoCatalogoCurso) {
		return cursoServiceImpl.getByCodigoCatalogoCurso(codigoCatalogoCurso);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Curso> getById(@PathVariable("id") Long codigo) throws DataException {
		return new ResponseEntity<>(cursoServiceImpl.getById(codigo), HttpStatus.OK);
	}

	@PutMapping("/updateEstadoAprobadoValidado")
	public ResponseEntity<?> updateEstado(
			@RequestParam("codCursoEspecializacion") Long codCursoEspecializacion,
			@RequestParam("codDocumento") Long codDocumento,
			@RequestParam("estadoAprobado") Boolean estadoAprobado,
			@RequestParam("estadoValidado") Boolean estadoValidado,
			@RequestParam("observaciones") String observaciones) {
		return new ResponseEntity<>(cursoServiceImpl.updateEstadoAprobadoValidado(estadoAprobado, estadoValidado, observaciones, codCursoEspecializacion, codDocumento), HttpStatus.OK);
	}

	@PutMapping("/{id}/iniciar")
	public ResponseEntity<?> iniciarCurso(@PathVariable("id") Long codCursoEspecializacion) {
		return new ResponseEntity<>(cursoServiceImpl.iniciarCurso(codCursoEspecializacion), HttpStatus.OK);
	}

	@PutMapping("/updateEstadosProceso")
	public ResponseEntity<?> updateEstadoProceso(
			@RequestParam("codCursoEspecializacion") Long codCursoEspecializacion,
			@RequestParam("codCursoEstado") Long codCursoEstado) {
		return new ResponseEntity<>(cursoServiceImpl.updateEstadoProceso(codCursoEstado, codCursoEspecializacion), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Curso> updateDatos(@PathVariable("id") long codigo, @RequestBody Curso obj) throws MessagingException {
		obj.setCodCursoEspecializacion(codigo);
		return new ResponseEntity<>(cursoServiceImpl.update(obj), HttpStatus.OK);
	}
	@PutMapping("/{id}/estado")
	public ResponseEntity<Curso> updateEstado(@PathVariable("id") long codigo, @RequestBody String estado) {
		Curso curso = cursoServiceImpl.updateEstado(codigo, estado);
		return new ResponseEntity<>(curso, HttpStatus.OK);
	}
	@PutMapping("/validar/{id}")
	public ResponseEntity<Curso> updateEstadoAprobadoObservaciones(@PathVariable("id") long codigo,@RequestBody ParamsValidacion validacion) throws MessagingException {
		Curso curso = cursoServiceImpl.updateEstadoAprobadoObservaciones(codigo, validacion.getAprueba(),validacion.getObservacion(),validacion.getCodUsuarioAprueba());
		return new ResponseEntity<>(curso, HttpStatus.OK);
	}


	@PostMapping("/updateRequisitos/{id}")
	public ResponseEntity<Curso> updateRequisitos(
			@PathVariable("id") Long codCursoEspecializacion,
			@RequestBody List<Requisito> requisitos) {
		return new ResponseEntity<>(cursoServiceImpl.updateRequisitos(codCursoEspecializacion, requisitos), HttpStatus.OK);
	}

	@PostMapping("/updateDocumento/{id}")
	public ResponseEntity<?> updateDocumento(
			@PathVariable("id") Long codDocumento,
			@RequestParam(value = "archivo", required = true) MultipartFile archivo) throws IOException {
		return new ResponseEntity<>(cursoServiceImpl.updateDocumento(codDocumento, archivo), HttpStatus.OK);
	}

	@PostMapping("/uploadDocumentos")
	public ResponseEntity<Curso> uploadFiles(
			@RequestParam("codCursoEspecializacion") Long codCursoEspecializacion,
			@RequestParam("codTipoDocumento") Long codTipoDocumento,
			@RequestParam("archivos") List<MultipartFile> archivos) throws IOException, ArchivoMuyGrandeExcepcion {
		return new ResponseEntity<>(cursoServiceImpl.uploadDocumentos(codCursoEspecializacion, archivos, codTipoDocumento), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) {
		cursoServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	@GetMapping("/cumpleMinimoAprobadosPruebasCurso/{id}")
	public ResponseEntity<?> cumpleMinimoAprobados(@PathVariable("id") long codigo) {
		return response(HttpStatus.OK, cursoServiceImpl.cumpleMinimoAprobadosCurso(codigo).toString());
	}

	@DeleteMapping("/eliminarDocumento")
	public ResponseEntity<HttpResponse> eliminarArchivo(
			@RequestParam Long codCursoEspecializacion,
			@RequestParam Long codDocumento) throws IOException {
		cursoServiceImpl.deleteDocumento(codCursoEspecializacion, codDocumento);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
				message), httpStatus);
	}
}
