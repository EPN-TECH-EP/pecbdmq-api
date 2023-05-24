package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.TipoBaja;
import epntech.cbdmq.pe.dto.Tipo_BajaDTO;
import epntech.cbdmq.pe.servicio.impl.TipoBajaServiceImpl;
import epntech.cbdmq.pe.servicio.impl.Tipo_bajaServiceImpl;

@RestController
@RequestMapping("/tipobaja")

public class Tipo_BajaResource {

	@Autowired
    private Tipo_bajaServiceImpl objServices;

	public Tipo_BajaResource(Tipo_bajaServiceImpl objServices) {
		super();
		this.objServices = objServices;
	}
	
	@GetMapping("/listar")
public  List<Tipo_BajaDTO> listar(@RequestParam String estado){
	return this.objServices.findByBaja(estado);
	
}
		
		
	
	
	
	



	
	
	
}
