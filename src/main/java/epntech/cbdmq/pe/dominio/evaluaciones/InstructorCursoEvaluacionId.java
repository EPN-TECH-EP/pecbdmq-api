package epntech.cbdmq.pe.dominio.evaluaciones;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstructorCursoEvaluacionId implements Serializable {

    @Column(name = "cod_curso_instructor")
    private Long codCursoInstructor;

    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;

}