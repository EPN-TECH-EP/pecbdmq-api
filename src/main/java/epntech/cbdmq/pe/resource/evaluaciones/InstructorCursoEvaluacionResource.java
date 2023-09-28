package epntech.cbdmq.pe.resource.evaluaciones;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.evaluaciones.InstructorCursoEvaluacion;
import epntech.cbdmq.pe.repositorio.evaluaciones.InstructorCursoEvaluacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/intructorCursoEvaluacion")
public class InstructorCursoEvaluacionResource {

    InstructorCursoEvaluacionRepository instructorCursoEvaluacionRepository;

    public InstructorCursoEvaluacionResource(
            InstructorCursoEvaluacionRepository instructorCursoEvaluacionRepository
    ) {
        this.instructorCursoEvaluacionRepository = instructorCursoEvaluacionRepository;
    }

    @PostMapping
    public InstructorCursoEvaluacion saveInstructorCursoEvaluacion(@RequestBody InstructorCursoEvaluacion instructorCursoEvaluacion) {
        Optional<InstructorCursoEvaluacion> instructorCursoEvaluacionOptional = instructorCursoEvaluacionRepository.findById(instructorCursoEvaluacion.getId());
        return instructorCursoEvaluacionOptional.orElseGet(() -> instructorCursoEvaluacionRepository.save(instructorCursoEvaluacion));
    }

}
