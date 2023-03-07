package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Parroquia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.ParroquiaServiceImpl;

@RestController
@RequestMapping("/parroquia")
//@CrossOrigin(origins = "${cors.urls}")
public class ParroquiaResource {
	
	@Autowired
	private ParroquiaServiceImpl objService;
	
	
	@GetMapping("/listar")
	public List<Parroquia> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Parroquia> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/canton/{id}")
	public List<Parroquia> obtenerPorCantonId(@PathVariable("id") int codigo) {
		return objService.getAllByCodCantonId(codigo);
	}

}
