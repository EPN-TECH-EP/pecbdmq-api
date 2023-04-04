package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.Canton;
import epntech.cbdmq.pe.dominio.admin.NotificacionPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import epntech.cbdmq.pe.servicio.impl.NotificacionPruebaServiceImpl;
import jakarta.mail.MessagingException;


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
	
}
