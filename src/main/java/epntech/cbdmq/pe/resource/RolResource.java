package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Rol;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.RolServiceImpl;
import epntech.cbdmq.pe.util.ResponseEntityUtil;

@RestController
@RequestMapping("/rol")

public class RolResource {
	
	@Autowired
	private RolServiceImpl rolService;
	
	@GetMapping("/listar")
	public List<Rol> listar(){
		return this.rolService.getAll();
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Rol obj) throws DataException{
		return new ResponseEntity<>(rolService.save(obj), HttpStatus.OK);
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<Rol> actualizarDatos(@RequestBody Rol obj) throws DataException{
		return new ResponseEntity<>(this.rolService.update(obj), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Long codigo) throws DataException {
		this.rolService.delete(codigo);
		return ResponseEntityUtil.response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	

}
