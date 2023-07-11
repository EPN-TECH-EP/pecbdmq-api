package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SEND;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.InscripcionEspServiceImpl;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/pruebaEspecializacion")
public class PruebaEspecializacionResource {
	
	@Autowired
	private InscripcionEspServiceImpl inscripcionEspServiceImpl;
	
	@PostMapping("/notificar")
	public ResponseEntity<?> notificar(@RequestParam("subTipoPrueba") Long subTipoPrueba, @RequestParam("codCursoEspecializacion") Long codCursoEspecializacion)
			throws MessagingException, DataException, PSQLException {
		inscripcionEspServiceImpl.notificarPruebaAprobada(codCursoEspecializacion, subTipoPrueba);

		return response(HttpStatus.OK, EMAIL_SEND);
	
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
