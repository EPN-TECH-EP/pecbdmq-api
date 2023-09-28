package epntech.cbdmq.pe.dominio.evaluaciones;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "tipos_evaluaciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE {h-schema}tipos_evaluaciones SET estado = 'ELIMINADO' WHERE cod_tipo_evaluacion = ?", check = ResultCheckStyle.COUNT)
public class TipoEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_tipo_evaluacion")
    private Long codTipoEvaluacion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "estado")
    private String estado;

}
