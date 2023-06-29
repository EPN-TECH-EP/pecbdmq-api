package epntech.cbdmq.pe.resource;

import java.util.List;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.admin.EspCurso;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.servicio.impl.EspCursoServiceImpl;
import epntech.cbdmq.pe.servicio.impl.MateriaPeriodoServiceImpl;


@RestController
@RequestMapping("/materiaPeriodo")
public class MateriaPeriodoResource {

	
	@Autowired
	private MateriaPeriodoServiceImpl objService;
	
	
	@GetMapping("/listar")
	public List<MateriaPeriodo> listar() {
		return objService.getAll();
	}
	@PostMapping("/crearActivo")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<MateriaPeriodo> guardarActivo(@RequestBody MateriaPeriodo obj) throws DataException {
		return new ResponseEntity<MateriaPeriodo>(objService.savePeriodoActivo(obj), HttpStatus.OK);
	}
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<MateriaPeriodo> guardar(@RequestBody MateriaPeriodo obj) throws DataException {
		return new ResponseEntity<MateriaPeriodo>(objService.save(obj), HttpStatus.OK);
	}
}
	

