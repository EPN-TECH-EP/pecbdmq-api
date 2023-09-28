package epntech.cbdmq.pe.resource.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.PreguntaTipoEvaluacion;
import epntech.cbdmq.pe.servicio.impl.evaluaciones.PreguntaEvaluacionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preguntasEvaluaciones")
public class PreguntaEvaluacionResource {

    PreguntaEvaluacionServiceImpl preguntaEvaluacionServiceImpl;

    public PreguntaEvaluacionResource(PreguntaEvaluacionServiceImpl preguntaEvaluacionServiceImpl) {
        this.preguntaEvaluacionServiceImpl = preguntaEvaluacionServiceImpl;
    }

    @GetMapping
    public List<PreguntaTipoEvaluacion> getPreguntasEvaluaciones() {
        return preguntaEvaluacionServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public List<PreguntaTipoEvaluacion> getPreguntaEvaluacionByCodTipoEvaluacion(@PathVariable("id") Long codTipoEvaluacion) {
        return preguntaEvaluacionServiceImpl.getByCodTipoEvaluacion(codTipoEvaluacion);
    }

    @PostMapping
    public PreguntaTipoEvaluacion savePreguntaEvaluacion(@RequestBody PreguntaTipoEvaluacion preguntaTipoEvaluacion) {
        return preguntaEvaluacionServiceImpl.save(preguntaTipoEvaluacion);
    }

    @PostMapping("/{id}")
    public PreguntaTipoEvaluacion updatePreguntaEvaluacion(@PathVariable("id") Long codPreguntaEvaluacion, @RequestBody PreguntaTipoEvaluacion preguntaTipoEvaluacion) {
        return preguntaEvaluacionServiceImpl.update(codPreguntaEvaluacion, preguntaTipoEvaluacion);
    }

    @DeleteMapping("/{id}")
    public void deletePreguntaEvaluacion(@PathVariable("id") Long codPreguntaEvaluacion) {
        preguntaEvaluacionServiceImpl.delete(codPreguntaEvaluacion);
    }
}
