package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.NO_ENCUENTRA;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_REGISTRADOS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EstudianteServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteResource {

    @Autowired
    private EstudianteServiceImpl objService;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody Estudiante obj) throws DataException {
        return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public List<Estudiante> listar() {
        return objService.getAll();
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

    @PostMapping("/ByUser")
    public Estudiante listarEstudianteByUsuario(@RequestParam("codUsuario") String codUsuario) throws DataException {
        Estudiante estudiante = objService.getEstudianteByUsuario(codUsuario);
        return estudiante;
    }
}


