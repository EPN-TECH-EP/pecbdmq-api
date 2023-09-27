package epntech.cbdmq.pe.dominio.evaluaciones;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "evaluaciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;

    @Column(name = "cod_tipo_evaluacion")
    private Long codTipoEvaluacion;

    @Column(name = "autor")
    private String autor;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "estado")
    private String estado;

}
