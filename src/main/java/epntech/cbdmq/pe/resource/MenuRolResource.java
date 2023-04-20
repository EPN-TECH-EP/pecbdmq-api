package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.constante.MensajesConst.ASIGNACION_EXITO;

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
import epntech.cbdmq.pe.dominio.admin.MenuRol;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.MenuRolService;
import epntech.cbdmq.pe.util.ResponseEntityUtil;

@RestController
@RequestMapping("/menuRol")

public class MenuRolResource {

	@Autowired
	private MenuRolService menuRolService;

	@GetMapping("/listar")
	public List<MenuRol> listar() {
		return this.menuRolService.getAll();
	}
	
	@GetMapping("/listar/{id}")
	public List<MenuRol> listarPorRol(@PathVariable("id") Long codRol) {
		return this.menuRolService.getAllByRol(codRol);
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody MenuRol obj) throws DataException {
		return new ResponseEntity<>(this.menuRolService.save(obj), HttpStatus.OK);
	}

	@PutMapping("/actualizar")
	public ResponseEntity<MenuRol> actualizarDatos(@RequestBody MenuRol obj) throws DataException {
		return new ResponseEntity<>(this.menuRolService.update(obj), HttpStatus.OK);
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<HttpResponse> eliminarDatos(@RequestBody MenuRol obj) throws DataException {
		this.menuRolService.delete(obj);
		return ResponseEntityUtil.response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	@PostMapping("/asignar")	
	public ResponseEntity<?> asignar(@RequestBody List<MenuRol> lista) throws DataException {
		this.menuRolService.deleteAndSave(lista);
		return ResponseEntityUtil.response(HttpStatus.OK, ASIGNACION_EXITO);
	}

}
