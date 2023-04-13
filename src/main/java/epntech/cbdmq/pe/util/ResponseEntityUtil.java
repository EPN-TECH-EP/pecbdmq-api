package epntech.cbdmq.pe.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import epntech.cbdmq.pe.dominio.HttpResponse;

public class ResponseEntityUtil {
	
	public static ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

}
