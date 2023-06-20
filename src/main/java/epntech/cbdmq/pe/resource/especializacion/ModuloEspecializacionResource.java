package epntech.cbdmq.pe.resource.especializacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.especializacion.ModuloEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.ModuloEspecializacionServiceImpl;

@RestController
@RequestMapping("/moduloEspecializacion")
public class ModuloEspecializacionResource {
	
	@Autowired
	private ModuloEspecializacionServiceImpl moduloEspecializacionServiceImpl;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody ModuloEspecializacion obj) throws DataException{
		return new ResponseEntity<>(moduloEspecializacionServiceImpl.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<ModuloEspecializacion> listar() {
		return moduloEspecializacionServiceImpl.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ModuloEspecializacion> obtenerPorId(@PathVariable("id") long codigo) {
		return moduloEspecializacionServiceImpl.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
