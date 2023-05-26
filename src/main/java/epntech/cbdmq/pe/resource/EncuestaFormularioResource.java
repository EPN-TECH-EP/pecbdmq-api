package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.EncuestaFormulario;
import epntech.cbdmq.pe.servicio.impl.EncuestaFormularioServiceImpl;

@RestController
@RequestMapping("/encuestaFormulario")
public class EncuestaFormularioResource {

	
	@Autowired
	private EncuestaFormularioServiceImpl objService;
	
	
	@GetMapping("/listar")
    public List<EncuestaFormulario> listar() {
        return objService.getAll();
    }
 
}
