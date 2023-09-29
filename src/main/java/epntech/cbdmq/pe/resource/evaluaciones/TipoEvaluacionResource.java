package epntech.cbdmq.pe.resource.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.TipoEvaluacion;
import epntech.cbdmq.pe.servicio.impl.evaluaciones.TipoEvaluacionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiposEvaluacion")
public class TipoEvaluacionResource {

    TipoEvaluacionServiceImpl tipoEvaluacionServiceImpl;

    public TipoEvaluacionResource(TipoEvaluacionServiceImpl tipoEvaluacionServiceImpl) {
        this.tipoEvaluacionServiceImpl = tipoEvaluacionServiceImpl;
    }

    @GetMapping
    public List<TipoEvaluacion> getTiposEvaluacion() {
        return tipoEvaluacionServiceImpl.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoEvaluacion saveTipoEvaluacion(@RequestBody TipoEvaluacion tipoEvaluacion) {
        return tipoEvaluacionServiceImpl.save(tipoEvaluacion);
    }

    @PostMapping("/{id}")
    public TipoEvaluacion updateTipoEvaluacion(@PathVariable("id") Long codTipoEvaluacion, @RequestBody TipoEvaluacion tipoEvaluacion) {
        return tipoEvaluacionServiceImpl.update(codTipoEvaluacion, tipoEvaluacion);
    }

    @DeleteMapping("/{id}")
    public void deleteTipoEvaluacion(@PathVariable("id") Long codTipoEvaluacion) {
        tipoEvaluacionServiceImpl.delete(codTipoEvaluacion);
    }

}
