package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_REGISTRADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.MateriaEstudiante;
import epntech.cbdmq.pe.dominio.util.EstudianteDto;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EstudianteServiceImpl;
import epntech.cbdmq.pe.servicio.impl.MateriaEstudianteServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteResource {

	@Autowired
	private EstudianteServiceImpl objService;
	
	@Autowired
	private MateriaEstudianteServiceImpl materiaEstudianteServiceImpl;
	
	@GetMapping("/listar")
	public List<Estudiante> listar() {
		return objService.getAll();
	}

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody Estudiante obj) throws DataException {
        return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPorId(@PathVariable("id") int codigo) {
        return objService.getById(codigo).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
        objService.delete(codigo);
        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }
	
	/*@GetMapping("/paginado")
	public ResponseEntity<?> listarDatos(Pageable pageable) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objService.getAllEstudiante(pageable));
		} catch (Exception e) {
			return response(HttpStatus.NOT_FOUND, "Error. Por favor intente más tarde.");
		}
	}*/
	
	/*@GetMapping("/listardatos")
	public List<EstudianteDatos> listarAll() {
		return objService.findAllEstudiante();
	}*/

	// crear estudiantes de formación (aprobados de pruebas)
	@PostMapping("/crearEstudiantes")
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardarAll() throws DataException {
		objService.saveEstudiantes();
		
		return response(HttpStatus.OK, DATOS_REGISTRADOS);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);

    }
	
    @GetMapping("/ByUser")
    public Estudiante listarEstudianteByUsuario(@RequestParam("codUsuario") String codUsuario) throws DataException {
        return objService.getEstudianteByUsuario(codUsuario);
    }
    
    @PostMapping("/asignarMateriaEstudiante")
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardarMateriaEstudiante(@RequestBody MateriaEstudiante obj) throws DataException {
		return new ResponseEntity<>(materiaEstudianteServiceImpl.save(obj), HttpStatus.OK);
	}
    
    @DeleteMapping("/eliminarMateriaEstudiante/{id}")
	public ResponseEntity<HttpResponse> eliminarMateriaEstudiante(@PathVariable("id") Long codigo) throws DataException {
    	materiaEstudianteServiceImpl.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
    
	@GetMapping("/materiaEstudiante/{id}")
	public List<MateriaEstudiante> obtenerPorIdMateriaEstudiante(@PathVariable("id") Long codigo) {
		return materiaEstudianteServiceImpl.getByCodEstudiante(codigo);
	}
    @GetMapping("/listarPA")
	public List<EstudianteDto> obtenerEstudiantesPA() {
		return objService.getEstudiantesSinAsignarPA();
	}
	@GetMapping("/listarBajaPA")
	public List<EstudianteDto> obtenerEstudiantesPAbAJA() {
		return objService.getEstudiantesBaja();
	}
    
}


