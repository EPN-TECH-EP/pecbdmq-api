package epntech.cbdmq.pe.dominio.evaluaciones;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "preguntas_evaluaciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaEvaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_pregunta")
    private Long codPregunta;

    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;

    @Column(name = "pregunta")
    private String pregunta;

    @Column(name = "estado")
    private String estado;

}
