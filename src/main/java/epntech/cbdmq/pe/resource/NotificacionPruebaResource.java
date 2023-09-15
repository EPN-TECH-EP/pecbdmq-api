package epntech.cbdmq.pe.resource;

import java.text.ParseException;
import java.util.List;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.excepcion.dominio.EmailNoEncontradoExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.UsuarioNoEncontradoExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.admin.Canton;
import epntech.cbdmq.pe.dominio.admin.NotificacionPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import epntech.cbdmq.pe.servicio.impl.NotificacionPruebaServiceImpl;
import jakarta.mail.MessagingException;

import static epntech.cbdmq.pe.resource.UsuarioResource.EMAIL_ENVIADO;
import static epntech.cbdmq.pe.util.ResponseEntityUtil.response;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/notificacionprueba")
public class NotificacionPruebaResource {

	@Autowired
	private NotificacionPruebaServiceImpl objService;
	
	@GetMapping("/listar")
	public List<NotificacionPrueba> listar() {
		return objService.getAll();
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody NotificacionPrueba obj) throws DataException, MessagingException {
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}

	@GetMapping("/aprobadosPorPrueba")
	public ResponseEntity<HttpResponse> resetPassword(@RequestParam Integer codSubTipoPrueba,
													  @RequestParam Boolean esUltimo)
			throws MessagingException, EmailNoEncontradoExcepcion, UsuarioNoEncontradoExcepcion, DataException, ParseException {
		objService.enviarNotificacion(codSubTipoPrueba, null,esUltimo);
		return response(OK, "Notificacion enviada a cada uno de los postulantes aprobados");
	}


	//////////////////////////  CURSOS  //////////////////////////
	@GetMapping("/aprobadosPorPruebaCurso")
	public ResponseEntity<HttpResponse> resetPassword(
			@RequestParam Integer codSubTipoPrueba,
			@RequestParam Integer codCurso,
			@RequestParam Boolean esUltimo
			)
			throws MessagingException, EmailNoEncontradoExcepcion, UsuarioNoEncontradoExcepcion, DataException, ParseException {
		objService.enviarNotificacion(codSubTipoPrueba, codCurso,esUltimo);
		return response(OK, "Notificacion enviada a cada uno de los postulantes aprobados");
	}
	
}
