package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.EncuestaFormulario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EncuestaFormularioServiceImpl;

@RestController
@RequestMapping("/encuestaFormulario")
public class EncuestaFormularioResource {

	
	@Autowired
	private EncuestaFormularioServiceImpl objService;
	
	
	@GetMapping("/listar/{id}")
    public List<EncuestaFormulario> listar(@PathVariable("id") int codigo) {
        return objService.getAll(codigo);
    }
	
	@PostMapping("/crear")
	public EncuestaFormulario crearencuesta(@RequestBody EncuestaFormulario encuesta) throws DataException {
		
		
		objService.save(encuesta);
		return null;
		
	}
	
 
}
