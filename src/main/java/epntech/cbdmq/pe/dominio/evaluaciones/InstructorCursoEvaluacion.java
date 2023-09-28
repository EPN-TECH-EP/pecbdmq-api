package epntech.cbdmq.pe.dominio.evaluaciones;

import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.InstructoresCurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "instructor_curso_evaluacion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorCursoEvaluacion implements Serializable {

    @EmbeddedId
    private InstructorCursoEvaluacionId id;

    @Column(name = "estado")
    private String estado = "ACTIVO";
}
