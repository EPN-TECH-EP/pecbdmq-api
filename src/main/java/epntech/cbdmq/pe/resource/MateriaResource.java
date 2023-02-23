package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.MateriaServiceImpl;

@RestController
@RequestMapping("/materia")
//@CrossOrigin(origins = "${cors.urls}")
public class MateriaResource {
	
	@Autowired
	private MateriaServiceImpl objService;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Materia obj) throws DataException{
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.save(obj));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/listar")
	public List<Materia> listar() {
		return objService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Materia> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getById(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Materia> actualizarDatos(@PathVariable("id") int codigo, @RequestBody Materia obj) throws DataException{
		return objService.getById(codigo).map(datosGuardados -> {
			datosGuardados.setNombreMateria(obj.getNombreMateria());
			datosGuardados.setNumHoras(obj.getNumHoras());
			datosGuardados.setTipoMateria(obj.getTipoMateria());
			datosGuardados.setObservacionMateria(obj.getObservacionMateria());
			datosGuardados.setPesoMateria(obj.getPesoMateria());
			datosGuardados.setNotaMinima(obj.getNotaMinima());

			Materia datosActualizados = null;
			
			try {
				datosActualizados = objService.update(datosGuardados);
				} catch (DataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
			
			
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarDatos(@PathVariable("id") int codigo) {
		objService.delete(codigo);
		return new ResponseEntity<String>("Registro eliminado exitosamente",HttpStatus.OK);
	}
}
