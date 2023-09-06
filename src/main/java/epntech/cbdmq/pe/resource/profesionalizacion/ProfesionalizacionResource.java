package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProfesionalizacionEntity;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProfesionalizacionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

public class ProfesionalizacionResource<T extends ProfesionalizacionEntity, U, V extends ProfesionalizacionRepository<T,U>, W extends ProfesionalizacionServiceImpl<T, U, V>> {
    protected W service;

    public ProfesionalizacionResource(W service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public List<T> listar() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> obtenerPorId(@PathVariable("id") U codigo) {
        return service.findById(codigo).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody T obj) throws DataException {
        return new ResponseEntity<>(service.save(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") U codigo) throws DataException {
        service.delete(codigo);
        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }

    public ResponseEntity<?> actualizarDatos(T entity){
        T datosActualizados;
        try {
            datosActualizados = service.update(entity);
        } catch (DataException e) {
            return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
        }
        return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
    }

    public ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
