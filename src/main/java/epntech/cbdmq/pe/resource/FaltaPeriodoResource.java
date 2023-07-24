package epntech.cbdmq.pe.resource;

import java.util.List;

import epntech.cbdmq.pe.dominio.util.TipoFaltaPeriodoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.FaltaPeriodo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.FaltaPeriodoServiceImpl;


@RestController
@RequestMapping("/faltaperiodo")
public class FaltaPeriodoResource {

	@Autowired
	private FaltaPeriodoServiceImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody FaltaPeriodo obj) throws DataException{
		return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<FaltaPeriodo> listar() {
		return objService.getAll();
	}
	@GetMapping("/listarPA")
	public List<TipoFaltaPeriodoUtil> getFaltasPeriodo(){
		return objService.getFaltasPeriodo();
	}


}
