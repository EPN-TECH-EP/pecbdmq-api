package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_periodo_estudiante")
@Table(name = "pro_periodo_estudiante")
@SQLDelete(sql = "UPDATE {h-schema}pro_periodo_estudiante SET estado = 'ELIMINADO' WHERE cod_periodo_estudiante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProPeriodoEstudiante extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_periodo_estudiante")
    private Integer codPeriodoEstudiante;

    @Column(name = "cod_periodo")
    private Integer codPeriodo;

    @Column(name = "cod_datos_personales")
    private Integer codDatosPersonales;

    @Column(name = "estado")
    private String estado;
}
