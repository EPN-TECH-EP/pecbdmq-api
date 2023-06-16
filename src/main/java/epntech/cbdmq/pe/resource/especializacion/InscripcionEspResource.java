package epntech.cbdmq.pe.resource.especializacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.InscripcionEspServiceImpl;

@RestController
@RequestMapping("/inscripcionEsp")
public class InscripcionEspResource {

	@Autowired
	private InscripcionEspServiceImpl inscripcionEspServiceImpl;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody InscripcionEsp inscripcionEsp) throws DataException {

		return new ResponseEntity<>(inscripcionEspServiceImpl.save(inscripcionEsp), HttpStatus.OK);
	}
}
