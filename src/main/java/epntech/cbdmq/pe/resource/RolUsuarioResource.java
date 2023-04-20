package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.ASIGNACION_EXITO;
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
import epntech.cbdmq.pe.dominio.admin.RolUsuario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.RolUsuarioService;
import epntech.cbdmq.pe.util.ResponseEntityUtil;

@RestController
@RequestMapping("/rolUsuario")
public class RolUsuarioResource {
	@Autowired
	private RolUsuarioService rolUsuarioService;

	@GetMapping("/listar")
	public List<RolUsuario> listar() {
		return this.rolUsuarioService.getAll();
	}
	
	@GetMapping("/listar/{id}")
	public List<RolUsuario> listarPorRol(@PathVariable("id") Long codUsuario) {
		return this.rolUsuarioService.getAllByUsuario(codUsuario);
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody RolUsuario obj) throws DataException {
		return new ResponseEntity<>(this.rolUsuarioService.save(obj), HttpStatus.OK);
	}

	@PutMapping("/actualizar")
	public ResponseEntity<RolUsuario> actualizarDatos(@RequestBody RolUsuario obj) throws DataException {
		return new ResponseEntity<>(this.rolUsuarioService.update(obj), HttpStatus.OK);
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<HttpResponse> eliminarDatos(@RequestBody RolUsuario obj) throws DataException {
		this.rolUsuarioService.delete(obj);
		return ResponseEntityUtil.response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@PostMapping("/asignar")	
	public ResponseEntity<?> asignar(@RequestBody List<RolUsuario> lista) throws DataException {
		this.rolUsuarioService.deleteAndSave(lista);
		return ResponseEntityUtil.response(HttpStatus.OK, ASIGNACION_EXITO);
	}
}
