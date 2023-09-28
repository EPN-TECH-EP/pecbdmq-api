package epntech.cbdmq.pe.dominio.evaluaciones;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "preguntas_tipo_evaluacion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE {h-schema}preguntas_tipo_evaluacion SET estado = 'ELIMINADO' WHERE cod_pregunta_tipo_evaluacion = ?", check = ResultCheckStyle.COUNT)

public class PreguntaTipoEvaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_pregunta_tipo_evaluacion")
    private Long codPregunta;

    @Column(name = "cod_tipo_evaluacion")
    private Long codTipoEvaluacion;

    @Column(name = "pregunta")
    private String pregunta;

    @Column(name = "estado")
    private String estado;

}
