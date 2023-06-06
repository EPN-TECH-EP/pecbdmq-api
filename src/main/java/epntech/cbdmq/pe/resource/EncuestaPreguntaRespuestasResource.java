package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.EncuestaPregunta;
import epntech.cbdmq.pe.dominio.admin.EncuestaPreguntaRespuesta;
import epntech.cbdmq.pe.servicio.impl.EncuestaPreguntaRespuestaServiceImpl;
import epntech.cbdmq.pe.servicio.impl.EncuestaPreguntaServiceImpl;


@RestController
@RequestMapping("/encuestapreguntarespuesta")
public class EncuestaPreguntaRespuestasResource {

	
	@Autowired
	private EncuestaPreguntaRespuestaServiceImpl objService;
	
	@GetMapping("/listar")
    public List<EncuestaPreguntaRespuesta> listar() {
        return objService.getAll();
    }
}
