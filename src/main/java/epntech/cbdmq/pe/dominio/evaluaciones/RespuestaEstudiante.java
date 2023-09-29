package epntech.cbdmq.pe.dominio.evaluaciones;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "respuestas_estudiantes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaEstudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_respuesta")
    private Long codRespuesta;

    @Column(name = "cod_estudiante")
    private Long codEstudiante;

    @Column(name = "cod_pregunta")
    private Long codPreguntaTipoEvaluacion;

    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;

    @Column(name = "respuesta")
    private Boolean respuesta;

    @Column(name = "fecha_respuesta")
    private LocalDate fechaRespuesta;

    @Column(name = "estado")
    private String estado;

}
