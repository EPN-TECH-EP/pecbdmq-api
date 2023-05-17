package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Canton;
import epntech.cbdmq.pe.dominio.admin.CantonProjection;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.CantonServiceImpl;

@RestController
@RequestMapping("/canton")
//@CrossOrigin(origins = "${cors.urls}")
public class CantonResource {
	
	@Autowired
	private CantonServiceImpl objService;
	
	
	@GetMapping("/listar")
	public List<Canton> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Canton> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/provincia/{id}")
	public List<CantonProjection> obtenerPorProvinciaId(@PathVariable("id") int codigo) {
		return objService.getAllByCodProvinciaId(codigo);
	}

}
