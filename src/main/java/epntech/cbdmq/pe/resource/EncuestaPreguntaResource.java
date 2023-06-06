package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.EncuestaPregunta;
import epntech.cbdmq.pe.dominio.admin.EncuestaPreguntaRespuesta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EncuestaPreguntaRespuestaServiceImpl;
import epntech.cbdmq.pe.servicio.impl.EncuestaPreguntaServiceImpl;

@RestController
@RequestMapping("/encuestapregunta")
public class EncuestaPreguntaResource {

	
	@Autowired
	private EncuestaPreguntaServiceImpl objService;
	
	
	@GetMapping("/listar")
    public List<EncuestaPregunta> listar() {
        return objService.getAll();
    }
	
	/*@PostMapping("/crear")
	public EncuestaPregunta crearencuesta(@RequestBody EncuestaPregunta encuesta) throws DataException {
		
		
		objService.save(encuesta);
		return null;*/
		
	}
	
 

