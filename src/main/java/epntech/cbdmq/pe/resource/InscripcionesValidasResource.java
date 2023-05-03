package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.dominio.admin.InscripcionesValidas;
import epntech.cbdmq.pe.dominio.util.InscripcionesValidasUtil;
import epntech.cbdmq.pe.servicio.impl.ConvocatoriaServicieImpl;
import epntech.cbdmq.pe.servicio.impl.InscripcionesValidasServiceImpl;

public class InscripcionesValidasResource {

	@Autowired
	private InscripcionesValidasServiceImpl objService;
	
	@GetMapping("/listar")
	public List<InscripcionesValidasUtil> listar() {
		return objService.getinscripcioneslistar();
	}
}
