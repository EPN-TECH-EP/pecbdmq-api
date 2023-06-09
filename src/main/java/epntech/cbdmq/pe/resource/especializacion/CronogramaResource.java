package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.NO_ADJUNTO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.especializacion.Cronograma;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.CronogramaServiceImpl;

@RestController
@RequestMapping("/cronograma")
public class CronogramaResource {

	@Autowired
	private CronogramaServiceImpl cronogramaServiceImpl;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestParam("archivo") MultipartFile archivo,
			@RequestParam("codTipoDocumento") Long codTipoDocumento)
			throws DataException, IOException, ArchivoMuyGrandeExcepcion {

		if (archivo.getSize() == 0)
			throw new DataException(NO_ADJUNTO);

		Cronograma cronograma = new Cronograma();
		cronograma = cronogramaServiceImpl.save(archivo, codTipoDocumento);

		return new ResponseEntity<>(cronograma, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable("id") Long codigo, @RequestParam("archivo") MultipartFile archivo, @RequestParam("estado") String estado)
			throws DataException, IOException, ArchivoMuyGrandeExcepcion {

		Cronograma cronograma = new Cronograma();
		cronograma = cronogramaServiceImpl.update(codigo, archivo, estado);

		return new ResponseEntity<>(cronograma, HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Cronograma> listar() {
		return cronogramaServiceImpl.listAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cronograma> getById(@PathVariable("id") Long codigo) {
		return cronogramaServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {
		cronogramaServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

}
