package epntech.cbdmq.pe.resource.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.Evaluacion;
import epntech.cbdmq.pe.dominio.evaluaciones.InstructorCursoEvaluacion;
import epntech.cbdmq.pe.repositorio.evaluaciones.InstructorCursoEvaluacionRepository;
import epntech.cbdmq.pe.servicio.impl.evaluaciones.EvaluacionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/intructorCursoEvaluacion")
public class InstructorCursoEvaluacionResource {

    InstructorCursoEvaluacionRepository instructorCursoEvaluacionRepository;
    EvaluacionServiceImpl evaluacionServiceImpl;

    public InstructorCursoEvaluacionResource(
            InstructorCursoEvaluacionRepository instructorCursoEvaluacionRepository,
            EvaluacionServiceImpl evaluacionServiceImpl
    ) {
        this.instructorCursoEvaluacionRepository = instructorCursoEvaluacionRepository;
        this.evaluacionServiceImpl = evaluacionServiceImpl;

    }

    @PostMapping
    public InstructorCursoEvaluacion saveInstructorCursoEvaluacion(@RequestBody InstructorCursoEvaluacion instructorCursoEvaluacion) {
        return this.instructorCursoEvaluacionRepository.save(instructorCursoEvaluacion);
    }

    @GetMapping("/{id}")
    public Evaluacion existeInstructorCursoEvaluacion(@PathVariable("id") Long codCursoInstructor) {
        InstructorCursoEvaluacion instructorCursoEvaluacion = this.instructorCursoEvaluacionRepository.findByCodCursoInstructor(codCursoInstructor);
        if (instructorCursoEvaluacion != null) {
            return this.evaluacionServiceImpl.getById(instructorCursoEvaluacion.getId().getCodEvaluacion());
        }
        return null;
    }

}
