package epntech.cbdmq.pe.resource.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.Evaluacion;
import epntech.cbdmq.pe.servicio.impl.evaluaciones.EvaluacionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluaciones")
public class EvaluacionResource {

    EvaluacionServiceImpl evaluacionServiceImpl;

    public EvaluacionResource(EvaluacionServiceImpl evaluacionServiceImpl) {
        this.evaluacionServiceImpl = evaluacionServiceImpl;
    }

    @GetMapping
    public List<Evaluacion> getEvaluaciones() {
        return evaluacionServiceImpl.getAll();
    }

    @PostMapping
    public Evaluacion saveEvaluacion(@RequestBody Evaluacion evaluacion) {
        return evaluacionServiceImpl.save(evaluacion);
    }

    @PostMapping("/{id}")
    public Evaluacion updateEvaluacion(@PathVariable("id") Long codEvaluacion, @RequestBody Evaluacion evaluacion) {
        return evaluacionServiceImpl.update(codEvaluacion, evaluacion);
    }

    @DeleteMapping("/{id}")
    public void deleteEvaluacion(@PathVariable("id") Long codEvaluacion) {
        evaluacionServiceImpl.delete(codEvaluacion);
    }


}
