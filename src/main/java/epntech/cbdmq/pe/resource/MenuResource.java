package epntech.cbdmq.pe.resource;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Menu;
import epntech.cbdmq.pe.dominio.admin.MenuPermisos;
import epntech.cbdmq.pe.dominio.admin.Rol;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.AulaRepository;
import epntech.cbdmq.pe.repositorio.admin.MenuRepository;
import epntech.cbdmq.pe.servicio.admin.MenuService;

@RestController
@RequestMapping(path = { "/menu"})
public class MenuResource {
	
	private MenuService menuService;
	
	@Autowired
	private MenuRepository repo;
	@Autowired
	public MenuResource(MenuService menuService) {
		this.menuService = menuService;
	}
	
	@GetMapping("/lista/{idusuario}")
    public ResponseEntity<List<MenuPermisos>> findMenuByIdUsuario(@PathVariable("idusuario") String idUsuario) {
        List<MenuPermisos> listaMenu = this.menuService.findMenuByIdUsuario(idUsuario);
        return new ResponseEntity<List<MenuPermisos>>(listaMenu, OK);
    }
	
	@GetMapping("/listar")
	public ResponseEntity<List<Menu>> findAll() {
		List<Menu> listaMenu = this.menuService.getAll();
        return new ResponseEntity<List<Menu>>(listaMenu, OK);
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Menu obj) throws DataException{
		return new ResponseEntity<>(menuService.save(obj), HttpStatus.OK);
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<Menu> actualizarDatos(@RequestBody Menu obj) throws DataException{
		return new ResponseEntity<>(this.menuService.update(obj), HttpStatus.OK);
	}

	private Object response(HttpStatus badRequest, String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@GetMapping("/listarPrimerNivel")
	public ResponseEntity<List<Menu>> getAllMenuPrimerNivel() throws DataException {
		List<Menu> listaMenu = this.menuService.getAllMenuPrimerNivel();
        return new ResponseEntity<List<Menu>>(listaMenu, OK);
	}
	
	@GetMapping("listarHijos/{codMenuPadre}")
	public ResponseEntity<List<Menu>> findByMenuPadre(@PathVariable("codMenuPadre") Integer menuPadre) throws DataException 
	{
		List<Menu> listaMenu = this.menuService.findByMenuPadre(menuPadre);
        return new ResponseEntity<List<Menu>>(listaMenu, OK);
	}
	

}
