package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.EspCurso;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.servicio.impl.EspCursoServiceImpl;
import epntech.cbdmq.pe.servicio.impl.MateriaPeriodoServiceImpl;


@RestController
@RequestMapping("/materiaperiodo")
public class MateriaPeriodoResource {

	
	@Autowired
	private MateriaPeriodoServiceImpl objService;
	
	
	@GetMapping("/listar")
	public List<MateriaPeriodo> listar() {
		return objService.getAll();
	}
}
	

