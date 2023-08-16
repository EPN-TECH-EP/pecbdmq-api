package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProEstudianteSemestreMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProEstudianteSemestreMateriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

@RestController
@RequestMapping("/proEstudianteSemestreMateria")
public class ProEstudianteSemestreMateriaResource {

    @Autowired
    private ProEstudianteSemestreMateriaImpl objService;

    /*
Metodo para listar Instructor
*/
    @GetMapping("/listar")
    public List<ProEstudianteSemestreMateria> listar() {
        return objService.getAll();
    }

    /*
Metodo para obtener por id el instructor
*/
    @GetMapping("/{id}")
    public ResponseEntity<ProEstudianteSemestreMateria> obtenerPorId(@PathVariable("id") Integer codigo) {
        return objService.getById(codigo).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
Metodo para crear instructor
*/
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody ProEstudianteSemestreMateria obj) throws DataException {
        return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
    }

    /*
Metodo para actualizar instructor
 */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProEstudianteSemestreMateria obj) throws DataException{
        return (ResponseEntity<ProEstudianteSemestreMateria>) objService.getById(codigo).map(datosGuardados -> {
            datosGuardados.setCodPeriodo(obj.getCodPeriodo());
            datosGuardados.setCodPeriodoEstudianteSemestre(obj.getCodPeriodoEstudianteSemestre());
            datosGuardados.setCodMateria(obj.getCodMateria());
            datosGuardados.setEstado(obj.getEstado());
            ProEstudianteSemestreMateria datosActualizados = null;
            try {
                datosActualizados = objService.update(datosGuardados);
            } catch (DataException e) {
                return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
            }
            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
Metodo para eliminar Instructor
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) {
        objService.delete(codigo);
        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
