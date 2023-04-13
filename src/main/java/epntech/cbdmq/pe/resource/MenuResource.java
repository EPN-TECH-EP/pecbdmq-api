package epntech.cbdmq.pe.resource;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.Menu;
import epntech.cbdmq.pe.dominio.admin.MenuPermisos;
import epntech.cbdmq.pe.servicio.admin.MenuService;

@RestController
@RequestMapping(path = { "/menu"})
public class MenuResource {
	
	private MenuService menuService;
	
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

}
